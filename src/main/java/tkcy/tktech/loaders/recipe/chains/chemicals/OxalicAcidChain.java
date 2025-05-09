package tkcy.tktech.loaders.recipe.chains.chemicals;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tkcy.tktech.api.unification.materials.TkTechMaterials.*;

import gregtech.api.recipes.RecipeMaps;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.api.utils.TimeUtil;

@UtilityClass
public class OxalicAcidChain {

    public static void init() {
        // CO + O2 + MeOH -> MeOOCCOOMe + H2O (crude)
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(CarbonMonoxide.getFluid(4000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidInputs(Methanol.getFluid(4000))
                .fluidOutputs(CrudeDimethylOxalate.getFluid(2000))
                .EUt(60)
                .duration(TimeUtil.seconds(10))
                .buildAndRegister();

        // CO + O2 + EtOH -> EtOOCCOOEt + H2O (crude)
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(CarbonMonoxide.getFluid(4000))
                .fluidInputs(Oxygen.getFluid(1000))
                .fluidInputs(Ethanol.getFluid(4000))
                .fluidOutputs(CrudeDiethylOxalate.getFluid(2000))
                .EUt(60)
                .duration(TimeUtil.seconds(13))
                .buildAndRegister();

        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(CrudeDiethylOxalate.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(DiethylOxalate.getFluid(1000))
                .EUt(30)
                .duration(TimeUtil.seconds(60))
                .buildAndRegister();

        RecipeMaps.DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(CrudeDimethylOxalate.getFluid(1000))
                .fluidOutputs(Water.getFluid(1000))
                .fluidOutputs(DimethylOxalate.getFluid(1000))
                .EUt(30)
                .duration(TimeUtil.seconds(60))
                .buildAndRegister();

        // MeOOCCOOMe + 2 LiOH -> HOOCCOOH + H2O
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(DimethylOxalate.getFluid(1000))
                .input(dust, LithiumHydroxide, 2)
                .output(dust, CrudeOxalicAcid)
                .EUt(30)
                .duration(TimeUtil.seconds(60))
                .buildAndRegister();

        // EtOOCCOOEt + 2 LiOH -> HOOCCOOH + H2O
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(DiethylOxalate.getFluid(1000))
                .input(dust, LithiumHydroxide, 2)
                .output(dust, CrudeOxalicAcid)
                .EUt(30)
                .duration(TimeUtil.seconds(60))
                .buildAndRegister();

        TkTechRecipeMaps.DRYING.recipeBuilder()
                .input(dust, CrudeOxalicAcid)
                .output(dust, OxalicAcid)
                .EUt(30)
                .duration(TimeUtil.seconds(60))
                .buildAndRegister();

        TkTechRecipeMaps.DRYING.recipeBuilder()
                .input(dust, OxalicAcid)
                .output(dust, DryOxalicAcid)
                .fluidOutputs(Steam.getFluid(2000))
                .EUt(160)
                .duration(TimeUtil.seconds(100))
                .buildAndRegister();
    }
}
