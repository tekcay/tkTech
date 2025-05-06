package tkcy.tktech.api.recipes.helpers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gregtech.api.recipes.Recipe;

import tkcy.tktech.api.recipes.properties.IRecipePropertyHelper;
import tkcy.tktech.api.recipes.properties.ToolProperty;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.modules.toolmodule.ToolsModule;

class RecipeSearchHelpersTest {

    @Test
    void findFirstRecipeWithProperties() {
        Map<IRecipePropertyHelper<?>, Object> parameters = new HashMap<>();
        System.out.println("here");
        parameters.put(ToolProperty.getInstance(), ToolsModule.GtTool.AXE);
        Recipe recipe = RecipeSearchHelpers.findFirstRecipeWithProperties(TkTechRecipeMaps.WOOD_WORKSHOP_RECIPES,
                parameters);
        System.out.println(recipe);
        assertNotNull(recipe);
    }
}
