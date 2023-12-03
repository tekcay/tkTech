package tkcy.simpleaddon.api.recipes;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.PrimitiveRecipeBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.core.sound.GTSoundEvents;

public final class TKCYSARecipeMaps {

    public static final RecipeMap<PrimitiveRecipeBuilder> PRIMITIVE_ROASTING = new RecipeMap<>("primitive_roasting",
            1, 1, 0, 0, new PrimitiveRecipeBuilder(), false)
                    .setSound(GTSoundEvents.FURNACE);
    public static final RecipeMap<SimpleRecipeBuilder> ADVANCED_ELECTROLYSIS = new RecipeMap<>("advanced_electrolysis",
            9, 4, 4, 6, new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.ELECTROLYZER);

    private TKCYSARecipeMaps() {}
}
