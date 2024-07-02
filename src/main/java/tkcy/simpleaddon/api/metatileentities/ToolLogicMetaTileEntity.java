package tkcy.simpleaddon.api.metatileentities;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;

import tkcy.simpleaddon.api.recipes.builders.ToolRecipeBuilder;
import tkcy.simpleaddon.api.recipes.logic.ToolRecipeLogic;

public abstract class PartsWorkerMTE extends MetaTileEntity {

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
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, this.recipeMap.getMaxInputs(), this, false);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, new FluidTank(2000));
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
                .progressBar(this.logic::getProgressPercent, 100, 30, 18, 18, GuiTextures.PROGRESS_BAR_BENDING,
                        ProgressWidget.MoveType.HORIZONTAL, this.recipeMap)
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }
}
