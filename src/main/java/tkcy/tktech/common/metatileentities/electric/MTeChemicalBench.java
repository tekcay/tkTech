package tkcy.tktech.common.metatileentities.electric;

import static tkcy.tktech.api.utils.GuiJeiUtils.FONT_HEIGHT;

import java.util.function.Supplier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.*;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleSidedCubeRenderer;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.tktech.api.recipes.logic.OnBlockRecipeLogic;
import tkcy.tktech.api.recipes.logic.containers.IRecipeLogicContainer;
import tkcy.tktech.api.recipes.logic.containers.RandomDurationRecipeLogic;
import tkcy.tktech.api.recipes.logic.containers.RecipeLogicsContainer;
import tkcy.tktech.api.recipes.logic.markers.IHideRecipeProgress;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.api.utils.handlers.FluidTankListHelper;

public class MTeChemicalBench extends MetaTileEntity implements EnergyContainerHandler.IEnergyChangeListener {

    protected IEnergyContainer energyContainer;
    protected OnBlockRecipeLogic workable;

    public MTeChemicalBench(@NotNull ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void initializeInventory() {
        this.energyContainer = EnergyContainerHandler.receiverContainer(this, 10000, GTValues.V[GTValues.EV], 2);
        this.workable = new Logic(this, () -> energyContainer, TkTechRecipeMaps.CHEMICAL_BENCH_RECIPES);
        super.initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTeChemicalBench(metaTileEntityId);
    }

    @Override
    public void onEnergyChanged(IEnergyContainer container, boolean isInitialChange) {}

    @SideOnly(Side.CLIENT)
    protected SimpleSidedCubeRenderer getBaseRenderer() {
        return Textures.VOLTAGE_CASINGS[0];
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline,
                new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 9, this, false);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new NotifiableItemStackHandler(this, workable.getRecipeMap().getMaxOutputs(), this, true);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return FluidTankListHelper.createNotifiableFluidHandler(workable.getRecipeMap().getMaxFluidInputs(), 2000, this,
                false);
    }

    @Override
    protected FluidTankList createExportFluidHandler() {
        return FluidTankListHelper.createNotifiableFluidHandler(workable.getRecipeMap().getMaxFluidInputs(), 2000, this,
                true);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createGuiTemplate(entityPlayer).build(getHolder(), entityPlayer);
    }

    protected ModularUI.Builder createGuiTemplate(EntityPlayer player) {
        RecipeMap<?> workableRecipeMap = workable.getRecipeMap();
        int yOffset = 0;
        if (workableRecipeMap.getMaxInputs() >= 6 || workableRecipeMap.getMaxFluidInputs() >= 6 ||
                workableRecipeMap.getMaxOutputs() >= 6 || workableRecipeMap.getMaxFluidOutputs() >= 6) {
            yOffset = FONT_HEIGHT;
        }

        ModularUI.Builder builder = workableRecipeMap.getRecipeMapUI()
                .createUITemplate(workable::getProgressPercent, importItems, exportItems, importFluids, exportFluids,
                        yOffset)
                .widget(new LabelWidget(5, 5, getMetaFullName()))
                .widget(new ImageWidget(79, 42 + yOffset, 18, 18, GuiTextures.INDICATOR_NO_ENERGY).setIgnoreColor(true)
                        .setPredicate(workable::isHasNotEnoughEnergy))
                .bindPlayerInventory(player.inventory, GuiTextures.SLOT, yOffset);

        return builder;
    }

    private static class Logic extends OnBlockRecipeLogic implements IHideRecipeProgress {

        public Logic(MetaTileEntity tileEntity, Supplier<IEnergyContainer> energyContainer,
                     RecipeMap<?>... recipeMaps) {
            super(tileEntity, energyContainer, false, recipeMaps);
        }

        @Override
        public @NotNull IRecipeLogicContainer setRecipeLogicContainer() {
            return new RecipeLogicsContainer(this, new RandomDurationRecipeLogic(this));
        }
    }
}
