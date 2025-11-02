package tkcy.tktech.common.metatileentities.multiprimitive;

import static gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType.STEEL_PIPE;
import static gregtech.common.blocks.MetaBlocks.BOILER_CASING;

import java.util.List;
import java.util.function.Function;

import gregtech.api.recipes.ingredients.GTRecipeFluidInput;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.util.TextComponentUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;

import tkcy.tktech.api.machines.NoEnergyMultiController;
import tkcy.tktech.api.metatileentities.RepetitiveSide;
import tkcy.tktech.api.recipes.logic.NoEnergyParallelLogic;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;

public class GasRelease extends NoEnergyMultiController implements RepetitiveSide {

    private int height = 1;

    public GasRelease(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TkTechRecipeMaps.GAS_RELEASE);
        this.recipeMapWorkable = new NoEnergyParallelLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new GasRelease(metaTileEntityId);
    }

    @Override
    public void update() {
        super.update();
        if ((getOffsetTimer() % 20 == 0) && isActive()) {
            tryHurtPlayer();
        }
    }

    @Nullable
    protected FluidStack getReleasedGas() {
        if (!isActive()) return null;
        try {
            return getRecipeLogic()
                    .getPreviousRecipe()
                    .getFluidInputs()
                    .get(0)
                    .getInputFluidStack();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Nullable
    protected Entity findEntity() {
        int maxRange = 5;
        BlockPos blockPos = this.getPos().up(getHeight() + 2);
        for (int i = 0; i < maxRange; i++) {
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos);
            try {
                Entity entity = getWorld().getEntitiesWithinAABB(Entity.class, axisAlignedBB).get(0);
                if (entity != null) {
                    return entity;
                }
            } catch (Exception e) {
                continue;
            }
            blockPos.up();
        }
        return null;
    }

    protected void tryHurtPlayer() {
        FluidStack releasedGas = getReleasedGas();
        if (releasedGas == null) return;
        int gasTemperature = releasedGas.getFluid().getTemperature();
        if (gasTemperature > 398) {
            float damage = (float) (gasTemperature - 298) / 100;
            Entity entity = findEntity();
            if (entity == null) return;
            if (!entity.isImmuneToFire) {
                entity.attackEntityFrom(DamageSource.IN_FIRE, damage);
            }
        }
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.FRONT, RelativeDirection.UP)
                .aisle("I")
                .aisle("Y")
                .aisle("P").setRepeatable(getMinSideLength(), getMaxSideLength())
                .aisle("M")
                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('P', states(getSideBlockBlockState()))
                .where('Y', selfPredicate())
                .build();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.height = getHeight();
        this.recipeMapWorkable.setParallelLimit(this.getParallelNumber());
        this.recipeMapWorkable.applyParallelBonus(TkTechRecipeMaps.GAS_RELEASE.recipeBuilder());
        initializeAbilities();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tktech.machine.gas_release.1"));
        tooltip.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, I18n.format("tktech.machine.gas_release.2")).getFormattedText());
        addParallelTooltip(tooltip);
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.COKE_OVEN_OVERLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger(RepetitiveSide.getHeightMarker(), this.height);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.height = data.getInteger(RepetitiveSide.getHeightMarker());
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.height);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.height = buf.readInt();
    }

    @Override
    public int getMinSideLength() {
        return 3;
    }

    @Override
    public int getMaxSideLength() {
        return 15;
    }

    @Override
    public IBlockState getSideBlockBlockState() {
        return BOILER_CASING.getState(STEEL_PIPE);
    }

    @Override
    public MetaTileEntity getMetatileEntity() {
        return this;
    }

    @Override
    public int getLayersPerParallel() {
        return 3;
    }

    @Override
    public Function<Integer, BlockPos> getRepetitiveDirection() {
        return pos -> this.getPos().up(pos);
    }
}
