package tkcy.simpleaddon.common.metatileentities.electric;

import static gregtech.api.GTValues.HV;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.recipes.builders.CoilTypeRecipeBuilder;
import tkcy.simpleaddon.api.recipes.properties.CoilTypeProperty;

public class CrackingUnitMte extends RecipeMapMultiblockController {

    private BlockWireCoil.CoilType coil;

    public CrackingUnitMte(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYSARecipeMaps.CRACKING);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new CrackingUnitMte(metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgressPercent());
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        if (type instanceof BlockWireCoil.CoilType) {
            this.coil = (BlockWireCoil.CoilType) type;
        } else {
            this.coil = CoilTypeRecipeBuilder.getDefaultValue();
        }
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
        return this.coil
                .equals(recipe.getProperty(CoilTypeProperty.getInstance(), CoilTypeRecipeBuilder.getDefaultValue()));
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.FRONT, RelativeDirection.UP)
                .aisle("ASA", "AAA", "AAA")
                .aisle("CCC", "C#C", "CCC")
                .aisle("CCC", "C#C", "CCC")
                .aisle("FXF", "X#X", "FXF").setRepeatable(9, 9)
                .aisle("FXF", "XMX", "FXF")
                .where('S', selfPredicate())
                .where('F', frames(Materials.Steel))
                .where('X', states(this.getCasingState()))
                .where('A', states(this.getCasingState())
                        .or(autoAbilities(true, true, true, false, true, true, false)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('C', heatingCoils())
                .where('#', air())
                .build();
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.HV);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.VOLTAGE_CASINGS[HV];
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.cracker.tooltip.1"));
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.BLAST_FURNACE_OVERLAY;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }
}
