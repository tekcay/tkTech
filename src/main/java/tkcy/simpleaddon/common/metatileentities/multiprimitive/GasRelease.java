package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import static gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType.STEEL_PIPE;
import static gregtech.common.blocks.MetaBlocks.BOILER_CASING;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

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

import tkcy.simpleaddon.api.machines.NoEnergyMultiController;
import tkcy.simpleaddon.api.metatileentities.HeightMetaTileEntity;
import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class GasRelease extends NoEnergyMultiController implements HeightMetaTileEntity {

    private int height = 1;
    private final String heightMarker = "height";

    public GasRelease(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYSARecipeMaps.PRIMITIVE_ROASTING);
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
                .aisle("P").setRepeatable(getMinHeight(), getMaxHeight())
                .aisle("M")
                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('P', states(getBlockStateToTest()))
                .where('Y', selfPredicate())
                .build();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.height = getHeight();
        setParallelNumber();
        initializeAbilities();
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
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.height = data.getInteger("height");
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
    public int getMinHeight() {
        return 3;
    }

    @Override
    public int getMaxHeight() {
        return 15;
    }

    @Override
    public IBlockState getBlockStateToTest() {
        return BOILER_CASING.getState(STEEL_PIPE);
    }

    @Override
    public MetaTileEntity getMetatileEntity() {
        return this;
    }

    @Override
    public void setParallelNumber() {
        this.recipeMapWorkable.setParallelLimit(this.height / 2);
    }
}
