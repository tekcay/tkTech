package tkcy.simpleaddon.modules;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.loaders.recipe.CraftingComponent;

public class CraftingComponentsModule {

    public static CraftingComponent.Component GEAR;

    static {
        GEAR = new CraftingComponent.Component(Stream.of(new Object[][] {

                { 0, new UnificationEntry(OrePrefix.gear, Materials.Bronze) },
                { 1, new UnificationEntry(OrePrefix.gear, Materials.CobaltBrass) },
                { 2, new UnificationEntry(OrePrefix.gear, Materials.Copper) },
                { 3, new UnificationEntry(OrePrefix.gear, Materials.Gold) },
                { 4, new UnificationEntry(OrePrefix.gear, Materials.Aluminium) },
                { 5, new UnificationEntry(OrePrefix.gear, Materials.Tungsten) },
                { 6, new UnificationEntry(OrePrefix.gear, Materials.NiobiumTitanium) },
                { 7, new UnificationEntry(OrePrefix.gear, Materials.VanadiumGallium) },
                { 8, new UnificationEntry(OrePrefix.gear, Materials.YttriumBariumCuprate) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));
    }
}
