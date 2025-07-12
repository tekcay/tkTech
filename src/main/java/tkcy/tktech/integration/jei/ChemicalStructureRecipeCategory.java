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
import tkcy.tktech.api.render.ChemicalReactionRenderUtils;
import tkcy.tktech.api.render.IChemicalStructureCategory;

public class ChemicalStructureRecipeCategory extends RecipeMapCategory
                                             implements IChemicalStructureCategory, IChemStructureToMaterials {

    private final IGuiHelper guiHelper;
    private ChemicalStructuresRecipeProperty.Container chemicalStructureContainer;
    private final IDrawable plusSign;
    private final IDrawable reactionArrow;
    private final RecipeMapUI<?> recipeMapUI;
    private List<IDrawable> chemicalStructuresInputs;
    private List<IDrawable> chemicalStructuresOutputs;
    private int backgroundHeight;
    private int chemReactionWidth;

    private static final double scale = 0.25D;

    public ChemicalStructureRecipeCategory(@NotNull RecipeMap<?> recipeMap, @NotNull GTRecipeCategory category,
                                           IGuiHelper guiHelper) {
        super(recipeMap, category, guiHelper);
        this.recipeMapUI = recipeMap.getRecipeMapUI();
        this.guiHelper = guiHelper;
        this.plusSign = ChemicalReactionRenderUtils.getPlusSign(guiHelper, scale);
        this.reactionArrow = ChemicalReactionRenderUtils.getReactionArrow(guiHelper, scale);
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

    @Override
    public int getBackgroundHeight() {
        return this.backgroundHeight;
    }

    @Override
    public int getBackgroundWidth() {
        return Math.max(chemReactionWidth + xMargin() * 2, getRecipeMapUI().getBackgroundWidth());
    }

    @Override
    public void drawExtras(@NotNull Minecraft minecraft) {
        super.drawExtras(minecraft);

        if (chemicalStructureContainer == null || !chemicalStructureContainer.isValid()) return;

        chemicalStructuresInputs = buildChemicalStructures(guiHelper, getInputMaterialsChemStructure(), scale);
        chemicalStructuresOutputs = buildChemicalStructures(guiHelper, getOutputMaterialsChemStructure(), scale);
        chemReactionWidth = ChemicalReactionRenderUtils.getReactionWith(chemicalStructuresInputs,
                chemicalStructuresOutputs, plusSign, reactionArrow, xSpacing());

        int xOffset = ChemicalReactionRenderUtils.getReactionXOffset(chemReactionWidth + 2 * xMargin(),
                getRecipeMapUI().getBackgroundWidth());
        int yOffset = getRecipeMapUI().getBackgroundHeight() + yMargin();

        int maxHeight = getTallestChemStructureHeight(
                Stream.concat(chemicalStructuresInputs.stream(), chemicalStructuresOutputs.stream()));

        backgroundHeight = yMargin() * 2 + maxHeight;

        drawBackground(xOffset,
                getRecipeMapUI().getBackgroundHeight(),
                getBackgroundWidth(),
                backgroundHeight);

        xOffset += xMargin();
        xOffset = drawChemStructures(chemicalStructuresInputs, minecraft, xOffset, yOffset, maxHeight);

        reactionArrow.draw(minecraft, xOffset, getCenteredYOffset(yOffset, maxHeight, reactionArrow.getHeight()));

        xOffset += reactionArrow.getWidth();
        xOffset += xSpacing();

        xOffset = drawChemStructures(chemicalStructuresOutputs, minecraft, xOffset, yOffset, maxHeight);
        xOffset += xMargin();
    }

    private int drawChemStructures(List<IDrawable> chemStructures, Minecraft minecraft, int baseXOffset,
                                   int baseYOffset,
                                   int maxHeight) {
        for (IDrawable chemStructure : chemStructures) {
            int height = chemStructure.getHeight();
            int yOffset = getCenteredYOffset(baseYOffset, maxHeight, height);
            chemStructure.draw(minecraft, baseXOffset, yOffset);
            baseXOffset += chemStructure.getWidth() + xSpacing();

            if (chemStructures.indexOf(chemStructure) < chemStructures.size() - 1) {
                plusSign.draw(minecraft, baseXOffset, getCenteredYOffset(baseYOffset, maxHeight, plusSign.getHeight()));
                baseXOffset += plusSign.getWidth() + xSpacing();
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
