package tkcy.tktech.integration.jei;

import static tkcy.tktech.api.render.ChemicalStructureRenderUtils.buildChemicalStructures;
import static tkcy.tktech.api.render.ChemicalStructureRenderUtils.getTallestChemStructureHeight;

import java.util.Set;
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

public class ChemicalStructureRecipeCategory extends RecipeMapCategory
                                             implements IChemStructureToMaterials {

    private final IGuiHelper guiHelper;
    private ChemicalStructuresRecipeProperty.Container chemicalStructureContainer;
    private IDrawable plusSign;
    private final RecipeMapUI<?> recipeMapUI;

    public ChemicalStructureRecipeCategory(@NotNull RecipeMap<?> recipeMap, @NotNull GTRecipeCategory category,
                                           IGuiHelper guiHelper) {
        super(recipeMap, category, guiHelper);
        this.recipeMapUI = recipeMap.getRecipeMapUI();
        this.guiHelper = guiHelper;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @NotNull GTRecipeWrapper recipeWrapper,
                          @NotNull IIngredients ingredients) {
        super.setRecipe(recipeLayout, recipeWrapper, ingredients);

        chemicalStructureContainer = ChemicalStructuresRecipeProperty.getInstance()
                .getValueFromRecipe(recipeWrapper.getRecipe(), true);
    }

    @Override
    public void drawExtras(@NotNull Minecraft minecraft) {
        super.drawExtras(minecraft);

        if (chemicalStructureContainer == null || !chemicalStructureContainer.isValid()) return;

        Set<IDrawable> chemicalStructuresInputs = buildChemicalStructures(guiHelper,
                getInputMaterialsChemStructure());
        Set<IDrawable> chemicalStructuresOutputs = buildChemicalStructures(guiHelper,
                getOutputMaterialsChemStructure());

        int xOffset = 20;
        int yOffset = 200;
        int maxHeight = getTallestChemStructureHeight(
                Stream.concat(chemicalStructuresInputs.stream(), chemicalStructuresOutputs.stream()));
        int xSpacing = 10;

        xOffset = drawChemStructures(chemicalStructuresInputs, minecraft, xOffset, yOffset, maxHeight, xSpacing);

        int progressBarHeight = 20;
        int progressBarWidth = 15;
        // recipeMapUI.progressBarTexture().draw(xOffset, getCenteredYOffset(yOffset, maxHeight, progressBarHeight),
        // progressBarWidth, progressBarHeight);
        xOffset += progressBarWidth;
        xOffset += xSpacing;

        xOffset = drawChemStructures(chemicalStructuresOutputs, minecraft, xOffset, yOffset, maxHeight, xSpacing);
    }

    private int drawChemStructures(Set<IDrawable> chemStructures, Minecraft minecraft, int baseXOffset, int baseYOffset,
                                   int maxHeight, int xSpacing) {
        for (IDrawable chemStructure : chemStructures) {
            int height = chemStructure.getHeight();
            int yOffset = getCenteredYOffset(baseYOffset, maxHeight, height);
            chemStructure.draw(minecraft, baseXOffset, yOffset);
            baseXOffset += chemStructure.getWidth() + xSpacing;
        }
        return baseXOffset;
    }

    private int getCenteredYOffset(int baseYOffset, int maxHeight, int imageHeight) {
        return (int) (baseYOffset + ((double) (maxHeight - imageHeight) / 2));
    }

    @Override
    public Set<Material> getInputMaterialsChemStructure() {
        return this.chemicalStructureContainer.getInputMaterialsChemStructure();
    }

    @Override
    public Set<Material> getOutputMaterialsChemStructure() {
        return this.chemicalStructureContainer.getOutputMaterialsChemStructure();
    }
}
