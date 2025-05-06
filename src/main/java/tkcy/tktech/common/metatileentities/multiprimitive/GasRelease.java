package tkcy.tktech.common.metatileentities.multiprimitive;

import static gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType.STEEL_PIPE;
import static gregtech.common.blocks.MetaBlocks.BOILER_CASING;

import java.util.List;
import java.util.function.Function;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
        tooltip.add(I18n.format("tkcysa.machine.gas_release.1"));
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
