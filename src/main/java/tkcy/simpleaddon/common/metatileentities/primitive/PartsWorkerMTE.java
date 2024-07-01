package tkcy.simpleaddon.common.metatileentities.primitive;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.simpleaddon.api.recipes.builders.ToolRecipeBuilder;
import tkcy.simpleaddon.api.recipes.logic.ToolRecipeLogic;
import tkcy.simpleaddon.modules.ToolsModule;

public class PartsWorkerMTE extends MetaTileEntity {

    protected final ToolRecipeLogic logic;
    protected final RecipeMap<ToolRecipeBuilder> recipeMap;

    public PartsWorkerMTE(ResourceLocation metaTileEntityId, RecipeMap<ToolRecipeBuilder> recipeMap) {
        super(metaTileEntityId);
        this.recipeMap = recipeMap;
        this.logic = new ToolRecipeLogic(this, recipeMap);
    }

    /**
     * This is used for recipe trimming during
     * {@link ToolRecipeLogic#prepareRecipe(Recipe, IItemHandlerModifiable, IMultipleTankHandler)}.
     * </br>
     * As we want to spawn items instead of transferring to output inventory, we use -1 (see
     * {@link Recipe#trimRecipeOutputs(Recipe, RecipeMap, int, int)})
     */
    @Override
    public int getItemOutputLimit() {
        return -1;
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new GTItemStackHandler(this, 2);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 1, this, false);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, new FluidTank(2000));
    }

    /*
     * @Override
     * public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
     * CuboidRayTraceResult hitResult) {
     * if (facing.equals(EnumFacing.UP))
     * return super.onRightClick(playerIn, hand, facing, hitResult);
     *
     * if (!super.onRightClick(playerIn, hand, facing, hitResult)) {
     *
     * ItemStack toolItemStack = playerIn.getHeldItem(hand);
     * if (toolItemStack.isEmpty()) return true;
     *
     * if (!this.verifyToolStack(toolItemStack, playerIn)) return true;
     * if (this.didFailDamageTool(toolItemStack, playerIn)) return true;
     *
     * if (this.currentRecipe == null) {
     *
     * this.inputStacks.add(toolItemStack);
     * this.inputStacks.add(this.itemInventory.getStackInSlot(0));
     *
     * this.currentRecipe = getCurrentRecipe(this.inputStacks);
     * if (currentRecipe == null) return true;
     *
     * this.toolUsage = this.toolUsesProperty.getValueFromRecipe(currentRecipe);
     * this.recipeToolUsages = toolUsage;
     *
     * } else this.onToolSneakRightClick(playerIn);
     *
     * }
     * return true;
     * }
     *
     */

    @SideOnly(Side.CLIENT)
    protected SimpleOverlayRenderer getBaseRenderer() {
        return Textures.COKE_BRICKS;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline,
                new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        ColourMultiplier multiplier = new ColourMultiplier(
                GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        colouredPipeline = ArrayUtils.add(pipeline, multiplier);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.SOUTH);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.NORTH);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.EAST);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.WEST);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new PartsWorkerMTE(this.metaTileEntityId, this.recipeMap);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.2"));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player).build(getHolder(), player);
    }

    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.PRIMITIVE_BACKGROUND, 176, 166)
                .shouldColor(false)
                .widget(new LabelWidget(5, 5, getMetaFullName()))
                .slot(this.importItems, 0, 60, 30, GuiTextures.PRIMITIVE_SLOT)
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @Override
    public boolean onHardHammerClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                     CuboidRayTraceResult hitResult) {
        this.logic.startWorking(ToolsModule.GtTool.HARD_HAMMER);
        return true;

        /*
         * 
         * TKCYSALog.logger.info("Click one");
         * ItemStack importInventory = this.importItems.getStackInSlot(0);
         * TKCYSALog.logger.info("is importInventory empty : " + importInventory.isEmpty());
         * 
         * if (importInventory.isEmpty()) {
         * this.currentRecipe = null;
         * this.currentRecipeOutputStack.clear();
         * TKCYSALog.logger.info("current recipe is null");
         * TKCYSALog.logger.info("############");
         * TKCYSALog.logger.info("############");
         * return false;
         * }
         * 
         * TKCYSALog.logger.info("importInventory is " + stackToString(importInventory));
         * 
         * // this.currentRecipe = this.getRecipe(importInventory);
         * 
         * if (this.currentRecipe == null) {
         * this.currentRecipe = this.getRecipe(importInventory);
         * TKCYSALog.logger.info("current recipe is null : " + this.currentRecipe == null);
         * 
         * this.currentRecipeOutputStack = this.currentRecipe.getOutputs();
         * this.markDirty();
         * 
         * if (this.currentRecipeOutputStack.isEmpty() || this.currentRecipe == null) {
         * this.currentRecipeOutputStack.clear();
         * TKCYSALog.logger.info("CLEAR");
         * TKCYSALog.logger.info("############");
         * TKCYSALog.logger.info("############");
         * return false;
         * }
         * }
         * 
         * for (ItemStack itemStack : this.currentRecipeOutputStack) {
         * TKCYSALog.logger.info("recipeOutputStack contains " + stackToString(itemStack));
         * }
         * 
         * this.fillExportItemHandler(this.currentRecipeOutputStack);
         * boolean didItFill = this.fillPlayerInventory(playerIn);
         * TKCYSALog.logger.info("did it fill player inventory ? : " + didItFill);
         * if (didItFill) {
         * this.importItems.extractItem(0, 2, false);
         * TKCYSALog.logger.info("DID FILL");
         * TKCYSALog.logger.info("############");
         * TKCYSALog.logger.info("############");
         * return true;
         * }
         * 
         * TKCYSALog.logger.info("TRUE END");
         * TKCYSALog.logger.info("############");
         * TKCYSALog.logger.info("############");
         * return false;
         * 
         */
    }
}
