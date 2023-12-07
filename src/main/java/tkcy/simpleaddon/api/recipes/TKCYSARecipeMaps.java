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
            6, 4, 2, 6, new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.ELECTROLYZER);
    public static final RecipeMap<SimpleRecipeBuilder> ADVANCED_ASSEMBLING = new RecipeMap<>("advanced_assembling",
            9, 1, 2, 0, new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.ASSEMBLER);
    public static final RecipeMap<PrimitiveRecipeBuilder> FLUID_PRIMITIVE_BLAST = new RecipeMap<>(
            "fluid_primitive_blast",
            2, 0, 1, 1, new PrimitiveRecipeBuilder(), false)
                    .setSound(GTSoundEvents.FURNACE);
    public static final RecipeMap<PrimitiveRecipeBuilder> CASTING = new MinimalPrimitiveRecipeMap<>(
            "casting",
            1, 1, 1, 0, new PrimitiveRecipeBuilder(), false)
                    .setSound(GTSoundEvents.COOLING);

    private TKCYSARecipeMaps() {}
}
