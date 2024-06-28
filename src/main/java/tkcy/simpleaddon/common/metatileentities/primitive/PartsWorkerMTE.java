package tkcy.simpleaddon.common.metatileentities.primitive;

import static tkcy.simpleaddon.api.utils.TKCYSALog.stackToString;
import static tkcy.simpleaddon.modules.NBTLabel.TOOL_USAGES;

import java.util.*;
import java.util.stream.Collectors;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.items.toolitem.ToolHelper;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.simpleaddon.api.items.toolitem.ToolAction;
import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.recipes.builders.ToolRecipeBuilder;
import tkcy.simpleaddon.api.recipes.properties.RecipePropertyHelper;
import tkcy.simpleaddon.api.recipes.properties.ToolProperty;
import tkcy.simpleaddon.api.recipes.properties.ToolUsesProperty;
import tkcy.simpleaddon.api.utils.RecipeHelper;
import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.modules.ToolsModule;

public class PartsWorkerMTE extends MetaTileEntity implements ToolAction {

    private int toolUsage = 0;
    private final RecipeMap<ToolRecipeBuilder> recipeMap;
    private Recipe currentRecipe;
    private List<ItemStack> currentRecipeOutputStack;
    private String toolClass;
    private int recipeToolUsages;
    private final RecipePropertyHelper<Integer> toolUsesProperty = ToolUsesProperty.getInstance();
    private final RecipeProperty<ToolsModule.GtTool> toolProperty = ToolProperty.getInstance();
    private final List<Recipe> recipes = TKCYSARecipeMaps.PARTS_WORKING.getRecipeList().stream()
            .collect(Collectors.toList());

    public PartsWorkerMTE(ResourceLocation metaTileEntityId, RecipeMap<ToolRecipeBuilder> recipeMap) {
        super(metaTileEntityId);
        this.recipeMap = recipeMap;
        this.createExportItemHandler();
        this.currentRecipeOutputStack = new ArrayList<>();
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new GTItemStackHandler(this, 2);
    }

    @Nullable
    private Recipe getRecipe(@NotNull ItemStack itemStack) {
        for (Recipe recipe : recipes) {
            for (GTRecipeInput gtRecipeInput : recipe.getInputs()) {
                if (gtRecipeInput.acceptsStack(itemStack)) {
                    TKCYSALog.logger.info("does accept stack");
                    return recipe;
                }
            }
        }
        TKCYSALog.logger.info("does NOT accept stack");
        return null;
    }

    @Nullable
    private Recipe getRecipeAdvanced(@NotNull ItemStack itemStack) {
        for (Recipe recipe : recipes) {
            for (GTRecipeInput gtRecipeInput : recipe.getInputs()) {
                if (gtRecipeInput.acceptsStack(itemStack)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 1, this, false);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, new FluidTank(2000));
    }

    private boolean verifyToolStack(ItemStack itemStack, EntityPlayer playerIn) {
        Set<String> toolClasses = itemStack.getItem().getToolClasses(itemStack);
        if (toolClasses.isEmpty()) this.toolClass = null;

        this.toolClass = ToolsModule.getToolClass(toolClasses);
        return toolClasses != null;
    }

    private int getRecipeProgress() {
        return (int) (100.0F * this.toolUsage / (100.0F * this.recipeToolUsages));
    }

    private void reinit() {
        this.toolUsage = 0;
    }

    public void onToolSneakRightClick(EntityPlayer player) {
        this.toolUsage++;
        int progress = this.getRecipeProgress();
        player.sendMessage(new TextComponentString(String.format("Recipe progress: %d/%%", this.toolUsage)) {});
        if (progress >= 100) this.reinit();
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

    private boolean didFailDamageTool(ItemStack toolStack, EntityPlayer playerIn) {
        if (this.toolClass == null) return true;
        ToolHelper.damageItem(toolStack, playerIn);
        return false;
    }

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
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.2"));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new PartsWorkerMTE(metaTileEntityId, this.recipeMap);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger(TOOL_USAGES.name(), toolUsage);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.toolUsage = data.getInteger(TOOL_USAGES.name());
    }

    @Override
    public void writeInitialSyncData(@NotNull PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.toolUsage);
    }

    @Override
    public void receiveInitialSyncData(@NotNull PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.toolUsage = buf.readInt();
    }

    @Override
    @Nullable
    public Recipe getCurrentRecipe(List<ItemStack> inputStacks) {
        return TKCYSARecipeMaps.PARTS_WORKING.findRecipe(1, inputStacks, RecipeHelper.emptyFluidStack);
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

    private void fillExportItemHandler(@NotNull List<ItemStack> recipeOutputs) {
        int slotIndex = 0;
        for (ItemStack itemStack : recipeOutputs) {
            TKCYSALog.logger.info("output is " + stackToString(itemStack));
            this.exportItems.insertItem(slotIndex, itemStack, false);
            slotIndex++;
        }
    }

    private boolean fillPlayerInventory(EntityPlayer playerIn) {
        boolean didItFill = false;
        for (int slot = 0; slot < this.exportItems.getSlots(); slot++) {
            // TKCYSALog.logger.info("current slotIndex loop : " + slot);
            TKCYSALog.logger
                    .info("to remove from exportItems : " + stackToString(this.exportItems.getStackInSlot(slot)));
            ItemStack exportStack = this.exportItems.extractItem(slot, Integer.MAX_VALUE, false);
            TKCYSALog.logger.info("removed from exportItems : " + stackToString(exportStack));
            if (exportStack.isEmpty()) continue;
            didItFill = playerIn.addItemStackToInventory(exportStack);
        }
        return didItFill;
    }

    @Override
    public boolean onHardHammerClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                     CuboidRayTraceResult hitResult) {
        TKCYSALog.logger.info("Click one");
        ItemStack importInventory = this.importItems.getStackInSlot(0);
        TKCYSALog.logger.info("is importInventory empty : " + importInventory.isEmpty());

        if (importInventory.isEmpty()) {
            this.currentRecipe = null;
            this.currentRecipeOutputStack.clear();
            TKCYSALog.logger.info("current recipe is null");
            TKCYSALog.logger.info("############");
            TKCYSALog.logger.info("############");
            return false;
        }

        TKCYSALog.logger.info("importInventory is " + stackToString(importInventory));

        // this.currentRecipe = this.getRecipe(importInventory);

        if (this.currentRecipe == null) {
            this.currentRecipe = this.getRecipe(importInventory);
            TKCYSALog.logger.info("current recipe is null : " + this.currentRecipe == null);

            this.currentRecipeOutputStack = this.currentRecipe.getOutputs();
            this.markDirty();

            if (this.currentRecipeOutputStack.isEmpty() || this.currentRecipe == null) {
                this.currentRecipeOutputStack.clear();
                TKCYSALog.logger.info("CLEAR");
                TKCYSALog.logger.info("############");
                TKCYSALog.logger.info("############");
                return false;
            }
        }

        for (ItemStack itemStack : this.currentRecipeOutputStack) {
            TKCYSALog.logger.info("recipeOutputStack contains " + stackToString(itemStack));
        }

        this.fillExportItemHandler(this.currentRecipeOutputStack);
        boolean didItFill = this.fillPlayerInventory(playerIn);
        TKCYSALog.logger.info("did it fill player inventory ? : " + didItFill);
        if (didItFill) {
            this.importItems.extractItem(0, 2, false);
            TKCYSALog.logger.info("DID FILL");
            TKCYSALog.logger.info("############");
            TKCYSALog.logger.info("############");
            return true;
        }

        TKCYSALog.logger.info("TRUE END");
        TKCYSALog.logger.info("############");
        TKCYSALog.logger.info("############");
        return false;
    }
}
