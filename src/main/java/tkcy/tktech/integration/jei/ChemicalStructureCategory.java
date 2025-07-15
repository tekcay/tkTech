package tkcy.tktech.integration.jei;

import static tkcy.tktech.api.utils.GuiJeiUtils.SLOT_DIM;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.integration.jei.basic.BasicRecipeCategory;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import tkcy.tktech.TkTech;
import tkcy.tktech.api.render.ChemicalReactionRenderUtils;
import tkcy.tktech.api.render.ChemicalStructureRenderUtils;
import tkcy.tktech.api.render.IChemicalStructureCategory;
import tkcy.tktech.api.utils.GuiJeiUtils;

public class ChemicalStructureCategory extends BasicRecipeCategory<ChemicalStructureInfo, ChemicalStructureInfo>
                                       implements IChemicalStructureCategory {

    private final IGuiHelper guiHelper;

    protected final IDrawable slot;
    protected final IDrawable icon;
    protected IDrawable chemicalStructure;

    private ChemicalStructureInfo info;

    public ChemicalStructureCategory(IGuiHelper guiHelper) {
        super("chemical_structure_location",
                "chemical_structure_location.name",
                GuiJeiUtils.drawConfigHeightDependentJEIGui(guiHelper, 0.8D),
                guiHelper);

        this.guiHelper = guiHelper;
        this.icon = guiHelper.createDrawableIngredient(OreDictUnifier.get(OrePrefix.ingot, Materials.Copper));
        this.slot = GuiJeiUtils.standardSlot(guiHelper);
    }

    @NotNull
    @Override
    public IRecipeWrapper getRecipeWrapper(@NotNull ChemicalStructureInfo recipeWrapper) {
        return recipeWrapper;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayout recipeLayout, @NotNull ChemicalStructureInfo recipeWrapper,
                          @NotNull IIngredients ingredients) {
        this.info = recipeWrapper;
        int slotIndex = 0;

        if (recipeWrapper.isHasDust()) {
            IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
            itemStackGroup.init(slotIndex, true, slotXOffset(slotIndex), slotYOffset());
            itemStackGroup.set(slotIndex, recipeWrapper.getDusts());
            slotIndex++;
        }

        if (recipeWrapper.isHasFluid()) {
            IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
            fluidStackGroup.init(slotIndex, true, slotXOffset(slotIndex), slotYOffset());
            fluidStackGroup.set(slotIndex, recipeWrapper.getFluidStack());
        }
    }

    /**
     * Sum of all the ingredients slots width plus the required {@link #xSpacing()}.
     */
    private int ingredientsWidth() {
        int slots = 0;
        if (info.isHasDust()) slots++;
        if (info.isHasFluid()) slots++;
        return slots * SLOT_DIM + (slots - 1) * xSpacing();
    }

    private int slotYOffset() {
        return yMargin() + materialNameHeight() + ySpacing();
    }

    /**
     * Returns an xOffset for the slot to be centered in the GUI, provided its width is
     * {@link GuiJeiUtils#STANDARD_JEI_UI_WIDTH}.
     */
    private int slotXOffset(int slotIndex) {
        int firstSlotXOffset = getCenterXOffset(GuiJeiUtils.STANDARD_JEI_UI_WIDTH, ingredientsWidth());
        return firstSlotXOffset + slotIndex * (SLOT_DIM + 1 + xSpacing());
    }

    /**
     * Drawn in the recipeWrapper, see {@link ChemicalStructureInfo#drawInfo(Minecraft, int, int, int, int)}.
     */
    private int materialNameHeight() {
        return GuiJeiUtils.FONT_HEIGHT;
    }

    @Override
    public void drawExtras(@NotNull Minecraft minecraft) {
        if (info == null) return;

        chemicalStructure = ChemicalStructureRenderUtils.getChemStructureIDrawable(guiHelper, info.getMaterial(), 0.5D);

        int xOffset = getChemicalBackgroundXOffset();

        drawReactionBackground();

        info.setGuiWidth(background.getWidth());
        info.setYMargin(yMargin());

        int slotIndex = 0;

        if (info.isHasDust()) {
            slot.draw(minecraft, slotXOffset(slotIndex), slotYOffset());
            slotIndex++;
        }

        if (info.isHasFluid()) {
            slot.draw(minecraft, slotXOffset(slotIndex), slotYOffset());
        }

        xOffset += xMargin();
        chemicalStructure.draw(
                minecraft,
                xOffset,
                getChemicalBackgroundYOffset() + ySpacing());
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @NotNull
    @Override
    public String getTitle() {
        return I18n.format("tktech.category.chemstructure.title");
    }

    @NotNull
    @Override
    public String getModName() {
        return TkTech.MODID;
    }

    @Override
    public int getChemicalBackgroundHeight() {
        return ySpacing() + chemicalStructure.getHeight() + ySpacing();
    }

    @Override
    public int getChemicalBackgroundWidth() {
        return xMargin() + chemicalStructure.getWidth() + xMargin();
    }

    @Override
    public int getChemicalBackgroundXOffset() {
        return ChemicalReactionRenderUtils.getReactionXOffset(getChemicalBackgroundWidth(), background.getWidth());
    }

    @Override
    public int getChemicalBackgroundYOffset() {
        return slotYOffset() + SLOT_DIM + ySpacing();
    }
}
