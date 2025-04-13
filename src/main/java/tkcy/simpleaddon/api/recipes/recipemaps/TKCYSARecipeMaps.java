package tkcy.simpleaddon.api.recipes.recipemaps;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.PrimitiveRecipeBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.core.sound.GTSoundEvents;

import crafttweaker.annotations.ZenRegister;
import lombok.experimental.UtilityClass;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenProperty;
import tkcy.simpleaddon.api.recipes.builders.CoilTypeRecipeBuilder;
import tkcy.simpleaddon.api.recipes.builders.NoEnergyRecipeBuilder;
import tkcy.simpleaddon.api.recipes.builders.ToolRecipeBuilder;
import tkcy.simpleaddon.modules.toolmodule.WorkingTool;

@UtilityClass
@ZenExpansion("mods.tkcysa.recipe.RecipeMaps")
@ZenRegister
public final class TKCYSARecipeMaps {

    @ZenProperty
    public static final RecipeMap<NoEnergyRecipeBuilder> PRIMITIVE_ROASTING = new MinimalPrimitiveRecipeMap<>(
            "primitive_roasting",
            1, 1, 0, 1, new NoEnergyRecipeBuilder(), false)
                    .setSound(GTSoundEvents.FURNACE);
    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> ADVANCED_ELECTROLYSIS = new RecipeMap<>("advanced_electrolysis",
            6, 4, 3, 6, new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.ELECTROLYZER);
    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> ADVANCED_ASSEMBLING = new RecipeMap<>("advanced_assembling",
            9, 1, 2, 0, new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.ASSEMBLER);
    @ZenProperty
    public static final RecipeMap<NoEnergyRecipeBuilder> FLUID_PRIMITIVE_BLAST = new MinimalPrimitiveRecipeMap<>(
            "fluid_primitive_blast",
            2, 0, 1, 1, new NoEnergyRecipeBuilder(), false)
                    .setSound(GTSoundEvents.FURNACE);
    @ZenProperty
    public static final RecipeMap<PrimitiveRecipeBuilder> CASTING = new MinimalPrimitiveRecipeMap<>(
            "casting",
            1, 1, 1, 0, new PrimitiveRecipeBuilder(), false);

    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> DRYING = new RecipeMap<>("drying", 1, 1, 1, 1,
            new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.BOILER);
    @ZenProperty
    public static final RecipeMap<NoEnergyRecipeBuilder> GAS_RELEASE = new MinimalPrimitiveRecipeMap<>("gas_release", 0,
            0, 1, 0,
            new NoEnergyRecipeBuilder(), false)
                    .allowEmptyOutput()
                    .setSound(GTSoundEvents.BOILER);
    @ZenProperty
    public static final RecipeMap<NoEnergyRecipeBuilder> ALLOYING = new RecipeMap<>("alloying", 2,
            0, 9, 2,
            new NoEnergyRecipeBuilder(), false)
                    .setSound(GTSoundEvents.BOILER);
    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> PRIMITIVE_DUST_MIXING = new RecipeMap<>("primitive_dust_mixing",
            3,
            1, 0, 0,
            new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.TURBINE);
    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> PRIMITIVE_MELTING = new RecipeMap<>("primitive_melting", 1,
            0, 0, 1,
            new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.BOILER);
    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> CLUSTER_MILL_RECIPES = new RecipeMap<>("cluster_mill", 2, 1, 0,
            0, new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.MOTOR);
    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> COMPONENT_ASSEMBING = new RecipeMap<>("component_assembler", 9,
            1, 1,
            0, new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.ASSEMBLER);
    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> HYDROGENATION = new RecipeMap<>("hydrogenation", 1,
            1, 2,
            2, new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.COOLING);
    @ZenProperty
    public static final RecipeMap<CoilTypeRecipeBuilder> CRACKING = new RecipeMap<>("cracking", 2,
            0, 2,
            1, new CoilTypeRecipeBuilder(), false)
                    .setSound(GTSoundEvents.BOILER);

    @ZenProperty
    public static final RecipeMap<SimpleRecipeBuilder> ROLLING_RECIPES = new RecipeMap<>(
            "rolling", 2, 1, 0, 0, new SimpleRecipeBuilder(), false)
                    .setSound(GTSoundEvents.MOTOR);

    @WorkingTool
    @ZenProperty
    public static final RecipeMap<ToolRecipeBuilder> ANVIL_RECIPES = new ToolRecipeMap<>(
            "anvil_recipes", 2, 2, 0, 0, new ToolRecipeBuilder(), false);

    @WorkingTool
    @ZenProperty
    public static final RecipeMap<ToolRecipeBuilder> BASIC_ELECTRONIC_RECIPES = new ToolRecipeMap<>(
            "basic_electronic_recipes", 10, 1, 1, 0, new ToolRecipeBuilder(), false);
}
