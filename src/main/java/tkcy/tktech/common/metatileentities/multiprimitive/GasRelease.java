package tkcy.tktech.common.metatileentities.multiprimitive;

import java.util.List;
import java.util.function.Function;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.PotionEffect;
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

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Material;
import gregtech.api.util.EntityDamageUtil;
import gregtech.api.util.RelativeDirection;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;

import lombok.Getter;
import tkcy.tktech.api.capabilities.TkTechMultiblockAbilities;
import tkcy.tktech.api.machines.NoEnergyMultiController;
import tkcy.tktech.api.metatileentities.IIgnitable;
import tkcy.tktech.api.metatileentities.RepetitiveSide;
import tkcy.tktech.api.recipes.logic.NoEnergyParallelLogic;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.api.unification.properties.CorrosiveMaterialProperty;
import tkcy.tktech.api.unification.properties.TkTechMaterialPropertyKeys;
import tkcy.tktech.api.unification.properties.ToxicMaterialProperty;
import tkcy.tktech.api.utils.BooleanHelper;
import tkcy.tktech.api.utils.MaterialHelper;
import tkcy.tktech.common.TkTechConfigHolder;
import tkcy.tktech.common.item.potions.TkTechPotion;

public class GasRelease extends NoEnergyMultiController implements RepetitiveSide, IIgnitable {

    private int height = 1;
    @Getter
    private boolean isIgnited;
    private final boolean isBrick;
    private final IBlockState repetitiveBlock;
    private final ICubeRenderer baseTexture;

    public GasRelease(ResourceLocation metaTileEntityId, IBlockState repetitiveBlock, ICubeRenderer baseTexture,
                      boolean isBrick) {
        super(metaTileEntityId, TkTechRecipeMaps.GAS_RELEASE);
        this.isBrick = isBrick;
        this.recipeMapWorkable = new NoEnergyParallelLogic(this);
        this.repetitiveBlock = repetitiveBlock;
        this.baseTexture = baseTexture;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new GasRelease(metaTileEntityId, repetitiveBlock, baseTexture, isBrick);
    }

    @Override
    protected void initializeAbilities() {
        if (!isBrick) {
            super.initializeAbilities();
            return;
        }

        this.inputFluidInventory = new FluidTankList(allowSameFluidFillForOutputs(),
                getAbilities(TkTechMultiblockAbilities.BRICK_HATCH_INPUT));
    }

    @Override
    public void update() {
        super.update();
        if (BooleanHelper.and(
                TkTechConfigHolder.gamePlay.enableGasReleaseDealsDamage,
                (getOffsetTimer() % 20 == 0),
                isActive())) {
            Entity entity = findEntity();
            if (entity == null) return;

            FluidStack releasedGas = getReleasedGas();
            if (releasedGas == null) return;

            tryDamageEntity(entity, releasedGas);
        }
    }

    @SuppressWarnings("ConstantConditions")
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

    /**
     * Applies damage to an entity if the processed released gas is either hot i.e. T > 398 or has
     * {@link ToxicMaterialProperty} or {@link CorrosiveMaterialProperty}.
     */
    protected void tryDamageEntity(@NotNull Entity entity, @NotNull FluidStack releasedGas) {
        int gasTemperature = releasedGas.getFluid().getTemperature();

        if (gasTemperature > 398) {
            EntityDamageUtil.applyTemperatureDamage((EntityLivingBase) entity, gasTemperature, 1.0F, -1);
        }

        Material fluidMaterial = MaterialHelper.getMaterialFromFluid(releasedGas);
        if (fluidMaterial == null) return;

        if (fluidMaterial.hasProperty(TkTechMaterialPropertyKeys.TOXIC)) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 2 * 500, 1));
        }
        if (fluidMaterial.hasProperty(TkTechMaterialPropertyKeys.CORROSIVE)) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(TkTechPotion.CORROSION, 2 * 200, 1));
        }
    }

    private TraceabilityPredicate importFluidPredicate() {
        return isBrick ? abilities(TkTechMultiblockAbilities.BRICK_HATCH_INPUT) :
                abilities(MultiblockAbility.IMPORT_FLUIDS);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.FRONT, RelativeDirection.UP)
                .aisle("I")
                .aisle("Y")
                .aisle("P").setRepeatable(getMinSideLength(), getMaxSideLength())
                .aisle("M")
                .where('I', importFluidPredicate())
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
        tooltip.add(
                TextComponentUtil.translationWithColor(TextFormatting.GOLD, I18n.format("tktech.machine.gas_release.2"))
                        .getFormattedText());
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
        return baseTexture;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger(RepetitiveSide.getHeightMarker(), this.height);
        data.setBoolean(IIgnitable.NBT_LABEL, this.isIgnited);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.height = data.getInteger(RepetitiveSide.getHeightMarker());
        this.isIgnited = data.getBoolean(IIgnitable.NBT_LABEL);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.height);
        buf.writeBoolean(this.isIgnited);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.height = buf.readInt();
        this.isIgnited = buf.readBoolean();
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
        return repetitiveBlock;
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

    @Override
    public void ignite() {
        isIgnited = true;
        markDirty();
    }

    @Override
    public void shutOff() {
        isIgnited = false;
        markDirty();
    }
}
