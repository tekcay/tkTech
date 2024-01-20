package tkcy.simpleaddon.api.recipes;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.PrimitiveRecipeBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.core.sound.GTSoundEvents;

import tkcy.simpleaddon.api.recipes.builders.NoEnergyRecipeBuilder;

public final class TKCYSARecipeMaps {

    public static final RecipeMap<NoEnergyRecipeBuilder> PRIMITIVE_ROASTING = new MinimalPrimitiveRecipeMap<>(
            "primitive_roasting",
            1, 1, 0, 0, new NoEnergyRecipeBuilder(), false)
                    .setSound(GTSoundEvents.FURNACE);
    public static final RecipeMap<SimpleRecipeBuilder> ADVANCED_ELECTROLYSIS = new RecipeMap<>("advanced_electrolysis",
            6, 4, 3, 6, new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.ELECTROLYZER);
    public static final RecipeMap<SimpleRecipeBuilder> ADVANCED_ASSEMBLING = new RecipeMap<>("advanced_assembling",
            9, 1, 2, 0, new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.ASSEMBLER);
    public static final RecipeMap<NoEnergyRecipeBuilder> FLUID_PRIMITIVE_BLAST = new MinimalPrimitiveRecipeMap<>(
            "fluid_primitive_blast",
            2, 0, 1, 1, new NoEnergyRecipeBuilder(), false)
                    .setSound(GTSoundEvents.FURNACE);
    public static final RecipeMap<PrimitiveRecipeBuilder> CASTING = new MinimalPrimitiveRecipeMap<>(
            "casting",
            1, 1, 1, 0, new PrimitiveRecipeBuilder(), false);

    public static final RecipeMap<SimpleRecipeBuilder> DRYING = new RecipeMap<>("drying", 1, 1, 1, 1,
            new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.BOILER);
    public static final RecipeMap<NoEnergyRecipeBuilder> GAS_RELEASE = new MinimalPrimitiveRecipeMap<>("gas_release", 0,
            0, 1, 0,
            new NoEnergyRecipeBuilder(), false)
                    .setSound(GTSoundEvents.BOILER);
    public static final RecipeMap<NoEnergyRecipeBuilder> ALLOYING = new RecipeMap<>("alloying", 2,
            0, 9, 2,
            new NoEnergyRecipeBuilder(), false)
                    .setSound(GTSoundEvents.BOILER);

    private TKCYSARecipeMaps() {}
}
