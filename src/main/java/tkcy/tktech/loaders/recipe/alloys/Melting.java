package tkcy.tktech.loaders.recipe.alloys;

import static gregtech.api.unification.ore.OrePrefix.plate;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.common.metatileentities.TkTechMetaTileEntities;
import tkcy.tktech.modules.alloyingmodule.Alloying;

@Alloying
@UtilityClass
public class Melting {

    public static void init() {
        ModHandler.addShapedRecipe("tkcy_steam_melter", TkTechMetaTileEntities.STEAM_MELTER.getStackForm(),
                "BBB", "BHB", "BBB",
                'B', new UnificationEntry(plate, Materials.Steel),
                'H', MetaBlocks.STEAM_CASING.getItemVariant(BlockSteamCasing.SteamCasingType.STEEL_BRICKS_HULL));

        List<Material> primitiveMaterials = new ArrayList<>();
        primitiveMaterials.add(Materials.Bronze);
        primitiveMaterials.add(Materials.SolderingAlloy);
        primitiveMaterials.add(Materials.Steel);
        primitiveMaterials.add(Materials.Copper);
        primitiveMaterials.add(Materials.Tin);
        primitiveMaterials.add(Materials.RedAlloy);
        primitiveMaterials.add(Materials.Redstone);
        primitiveMaterials.add(Materials.Iron);
        primitiveMaterials.add(Materials.TinAlloy);

        for (Material material : primitiveMaterials) {
            TkTechRecipeMaps.PRIMITIVE_MELTING.recipeBuilder()
                    .input(OrePrefix.dust, material)
                    .fluidOutputs(material.getFluid(GTValues.L))
                    .duration((int) material.getMass() + material.getFluid().getTemperature())
                    .EUt(32)
                    .buildAndRegister();
        }
    }
}
