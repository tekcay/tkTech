package tkcy.tktech.loaders.recipe.chains.metals;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tkcy.tktech.api.unification.materials.TkTechMaterials.*;

import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.utils.TimeUtil;

@UtilityClass
public class PlatinumChain {

    public static void init() {
        removal();
        platinum();
        rhodium();
    }

    private static void platinum() {
        // Froth
        RecipeMaps.CHEMICAL_BATH_RECIPES.recipeBuilder().duration(TimeUtil.seconds(40)).EUt(VA[MV])
                .input(dust, PlatinumGroupSludge, 5)
                .fluidInputs(AquaRegia.getFluid(1000))
                .fluidOutputs(TreatedPlatinumGroupSludge.getFluid(5000))
                .buildAndRegister();

        CENTRIFUGE_RECIPES.recipeBuilder().duration(500).EUt(VA[HV])
                .fluidInputs(TreatedPlatinumGroupSludge.getFluid(5000))
                .output(dust, PlatinumRaw, 3) // PtCl2
                .output(dust, PalladiumRaw, 3) // PdNH3
                .output(dust, InertMetalMixture, 2) // RhRuO4
                .output(dust, RarestMetalMixture) // IrOsO4(H2O)
                .output(dust, PlatinumSludgeResidue, 2)
                .buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder().duration(TimeUtil.seconds(120)).EUt(VA[HV])
                .input(dust, PlatinumRaw, 3)
                .output(dust, Platinum)
                .fluidOutputs(Chlorine.getFluid(800))
                .blastFurnaceTemp(2200)
                .buildAndRegister();
    }

    private static void rhodium() {
        // Froth
        CHEMICAL_RECIPES.recipeBuilder().duration(TimeUtil.seconds(60)).EUt(VA[LV])
                .notConsumable(DistilledWater.getFluid(8000))
                .fluidInputs(RhodiumSulfate.getFluid(1000))
                .input(dust, SodiumHydroxide, 2)
                .fluidOutputs(RhodiumHydroxide.getFluid(1000))
                .buildAndRegister();

        // Rh(OH)2 + HCl -> H3RhCl6
        CHEMICAL_RECIPES.recipeBuilder().duration(TimeUtil.seconds(10)).EUt(VA[LV])
                .fluidInputs(RhodiumHydroxide.getFluid(1000))
                .fluidInputs(HydrochloricAcid.getFluid(6000))
                .fluidOutputs(ChlororhodicAcid.getFluid(1000))
                .buildAndRegister();

        // H3RhCl6 + NH4Cl + NaNO2 -> Rh?
        CHEMICAL_RECIPES.recipeBuilder().duration(TimeUtil.seconds(70)).EUt(VA[LV])
                .fluidInputs(ChlororhodicAcid.getFluid(1000))
                .fluidInputs(DistilledWater.getFluid(2000))
                .notConsumable(DistilledWater.getFluid(6000))
                .input(dust, AmmoniumChloride)
                .input(dust, SodiumNitrite)
                .output(dust, RhodiumPrecipitate)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder().duration(TimeUtil.seconds(20)).EUt(VA[MV])
                .input(dust, RhodiumPrecipitate)
                .fluidInputs(HydrochloricAcid.getFluid(4000))
                .fluidOutputs(TreatedRhodiumPrecipitate.getFluid(1000))
                .buildAndRegister();

        FLUID_HEATER_RECIPES.recipeBuilder().duration(TimeUtil.seconds(200)).EUt(VA[HV])
                .fluidInputs(TreatedRhodiumPrecipitate.getFluid(1000))
                .fluidOutputs(HotRhodium.getFluid(L))
                .buildAndRegister();

        VACUUM_RECIPES.recipeBuilder().duration(TimeUtil.seconds(200)).EUt(VA[HV])
                .fluidInputs(HotRhodium.getFluid(L))
                .output(dust, Rhodium)
                .buildAndRegister();
    }

    private static void removal() {
        List<ItemStack> input = Collections.singletonList(OreDictUnifier.get(dust, PlatinumGroupSludge, 6));
        List<FluidStack> fluidInput = Collections.singletonList(AquaRegia.getFluid(1200));

        Recipe recipeToRemove = CENTRIFUGE_RECIPES.findRecipe(VA[HV], input, fluidInput);
        if (recipeToRemove != null) CENTRIFUGE_RECIPES.removeRecipe(recipeToRemove);
    }
}
