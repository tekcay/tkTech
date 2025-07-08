package tkcy.tktech.integration.jei;

import static tkcy.tktech.api.render.ChemicalStructureRenderUtils.buildChemicalStructures;
import static tkcy.tktech.api.render.ChemicalStructureRenderUtils.getTallestChemStructureHeight;

import java.util.List;
import java.util.stream.Stream;

import net.minecraft.client.Minecraft;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.category.GTRecipeCategory;
import gregtech.api.recipes.ui.RecipeMapUI;
import gregtech.api.unification.material.Material;
import gregtech.integration.jei.recipe.GTRecipeWrapper;
import gregtech.integration.jei.recipe.RecipeMapCategory;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import tkcy.tktech.api.recipes.properties.ChemicalStructuresRecipeProperty;
import tkcy.tktech.api.recipes.recipemaps.IChemStructureToMaterials;
import tkcy.tktech.api.recipes.recipemaps.ICustomRecipeMapUI;
import tkcy.tktech.api.render.TkTechTextures;

public class ChemicalStructureRecipeCategory extends RecipeMapCategory
                                             implements IChemStructureToMaterials {

    private final IGuiHelper guiHelper;
    private ChemicalStructuresRecipeProperty.Container chemicalStructureContainer;
    private IDrawable plusSign;
    private IDrawable reactionArrow;
    private final RecipeMapUI<?> recipeMapUI;
    private List<IDrawable> chemicalStructuresInputs;
    private List<IDrawable> chemicalStructuresOutputs;

    public ChemicalStructureRecipeCategory(@NotNull RecipeMap<?> recipeMap, @NotNull GTRecipeCategory category,
                                           IGuiHelper guiHelper) {
        super(recipeMap, category, guiHelper);
        this.recipeMapUI = recipeMap.getRecipeMapUI();
        this.guiHelper = guiHelper;
        this.plusSign = guiHelper
                .drawableBuilder(TkTechTextures.REACTION_PLUS.imageLocation, 0, 0, 47 / 4, 75 / 4)
                .setTextureSize(47 / 4, 75 / 4)
                .build();
        this.reactionArrow = guiHelper
                .drawableBuilder(TkTechTextures.REACTION_ARROW.imageLocation, 0, 0, 138 / 4, 37 / 4)
                .setTextureSize(138 / 4, 37 / 4)
                .build();
    }

    protected ICustomRecipeMapUI getRecipeMapUI() {
        return (ICustomRecipeMapUI) recipeMapUI;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @NotNull GTRecipeWrapper recipeWrapper,
                          @NotNull IIngredients ingredients) {
        super.setRecipe(recipeLayout, recipeWrapper, ingredients);

        chemicalStructureContainer = ChemicalStructuresRecipeProperty.getInstance()
                .getValueFromRecipe(recipeWrapper.getRecipe(), true);
    }

    private int xMargin() {
        return 20;
    }

    private int yMargin() {
        return 20;
    }

    private int getBackgroundWidth() {
        int chemstructuresWidth = Stream.concat(chemicalStructuresInputs.stream(), chemicalStructuresOutputs.stream())
                .mapToInt(IDrawable::getWidth)
                .sum();

        int reactionArrowWidth = reactionArrow.getWidth() + 2 * getXSpace();
        int plusSignsWidth = (plusSign.getWidth() + getXSpace() * 2);
        plusSignsWidth *= (chemicalStructuresInputs.size() + chemicalStructuresOutputs.size() - 2);

        int xMargins = xMargin() * 2;

        return chemstructuresWidth + reactionArrowWidth + plusSignsWidth + xMargins;
    }

    @Override
    public void drawExtras(@NotNull Minecraft minecraft) {
        super.drawExtras(minecraft);

        if (chemicalStructureContainer == null || !chemicalStructureContainer.isValid()) return;

        chemicalStructuresInputs = buildChemicalStructures(guiHelper, getInputMaterialsChemStructure());
        chemicalStructuresOutputs = buildChemicalStructures(guiHelper, getOutputMaterialsChemStructure());

        int xOffset = xMargin();
        int yOffset = getRecipeMapUI().getBackgroundHeight() + yMargin();

        int maxHeight = getTallestChemStructureHeight(
                Stream.concat(chemicalStructuresInputs.stream(), chemicalStructuresOutputs.stream()));

        TkTechTextures.REACTION_BACKGROUND.draw(0, getRecipeMapUI().getBackgroundHeight(), getBackgroundWidth(),
                yMargin() * 2 + maxHeight);

        xOffset = drawChemStructures(chemicalStructuresInputs, minecraft, xOffset, yOffset, maxHeight);

        reactionArrow.draw(minecraft, xOffset, getCenteredYOffset(yOffset, maxHeight, reactionArrow.getHeight()));

        xOffset += reactionArrow.getWidth();
        xOffset += getXSpace();

        xOffset = drawChemStructures(chemicalStructuresOutputs, minecraft, xOffset, yOffset, maxHeight);
        xOffset += xMargin();
    }

    private int getXSpace() {
        return 10;
    }

    private int drawChemStructures(List<IDrawable> chemStructures, Minecraft minecraft, int baseXOffset,
                                   int baseYOffset,
                                   int maxHeight) {
        for (IDrawable chemStructure : chemStructures) {
            int height = chemStructure.getHeight();
            int yOffset = getCenteredYOffset(baseYOffset, maxHeight, height);
            chemStructure.draw(minecraft, baseXOffset, yOffset);
            baseXOffset += chemStructure.getWidth() + getXSpace();

            if (chemStructures.indexOf(chemStructure) < chemStructures.size() - 1) {
                plusSign.draw(minecraft, baseXOffset, getCenteredYOffset(baseYOffset, maxHeight, plusSign.getHeight()));
                baseXOffset += plusSign.getWidth() + getXSpace();
            }
        }
        return baseXOffset;
    }

    private int getCenteredYOffset(int baseYOffset, int maxHeight, int imageHeight) {
        return (int) (baseYOffset + ((double) (maxHeight - imageHeight) / 2));
    }

    @Override
    public List<Material> getInputMaterialsChemStructure() {
        return this.chemicalStructureContainer.getInputMaterialsChemStructure();
    }

    @Override
    public List<Material> getOutputMaterialsChemStructure() {
        return this.chemicalStructureContainer.getOutputMaterialsChemStructure();
    }
}
