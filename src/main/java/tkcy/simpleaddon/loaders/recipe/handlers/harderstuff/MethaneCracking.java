package tkcy.simpleaddon.loaders.recipe.handlers.harderstuff;

import static gregtech.api.GTValues.HV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.recipes.RecipeMaps.DISTILLATION_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Hydrogen;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;

public class MethaneCracking {

    public static void init() {
        removeGTCEuRecipe();
        addDistillationRecipes();
        addMethaneCrackingRecipes();
    }

    private static void addMethaneCrackingRecipes() {
        // 2 CH4 + H2O -> 2 CO + 3 H2
        RecipeMaps.CRACKING_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(1))
                .fluidInputs(Methane.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .fluidOutputs(LightlySteamCrackedMethane.getFluid(1000))
                .duration(120).EUt(120).buildAndRegister();

        // CH4 + H2O -> CO + 3 H2
        RecipeMaps.CRACKING_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(2))
                .fluidInputs(Methane.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .fluidOutputs(ModeratelySteamCrackedMethane.getFluid(1000))
                .duration(120).EUt(180).buildAndRegister();

        // CH4 + 2 H2O -> CO2 + 4 H2
        RecipeMaps.CRACKING_RECIPES.recipeBuilder()
                .notConsumable(new IntCircuitIngredient(3))
                .fluidInputs(Methane.getFluid(1000))
                .fluidInputs(Steam.getFluid(1000))
                .fluidOutputs(SeverelySteamCrackedMethane.getFluid(1000))
                .duration(120).EUt(240).buildAndRegister();
    }

    private static void addDistillationRecipes() {
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(LightlySteamCrackedMethane.getFluid(1000))
                .fluidOutputs(DistilledWater.getFluid(1000))
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1500))
                .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(ModeratelySteamCrackedMethane.getFluid(1000))
                .fluidOutputs(DistilledWater.getFluid(2000))
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(3000))
                .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(SeverelySteamCrackedMethane.getFluid(1000))
                .fluidOutputs(DistilledWater.getFluid(4000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(4000))
                .duration(120).EUt(120).buildAndRegister();
    }

    private static void removeGTCEuRecipe() {
        List<ItemStack> circuit = Collections.singletonList(IntCircuitIngredient.getIntegratedCircuit(1));

        List<FluidStack> input = new ArrayList<>();
        input.add(Water.getFluid(2000));
        input.add(Methane.getFluid(1000));

        Recipe gtceuRecipe = CHEMICAL_RECIPES.findRecipe(VA[HV], circuit, input);
        if (gtceuRecipe != null) CHEMICAL_RECIPES.removeRecipe(gtceuRecipe);
    }
}
