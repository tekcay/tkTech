package tkcy.tktech.integration.jei;

import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import gregtech.api.modules.GregTechModule;
import gregtech.api.util.Mods;
import gregtech.integration.IntegrationSubmodule;

import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import tkcy.tktech.TkTech;
import tkcy.tktech.api.unification.properties.ChemicalStructureProperty;
import tkcy.tktech.modules.TkTechModules;

@JEIPlugin
@GregTechModule(moduleID = TkTechModules.MODULE_JEI,
                containerID = TkTech.MODID,
                modDependencies = Mods.Names.JUST_ENOUGH_ITEMS,
                name = "TkTech JEI Integration",
                description = "TkTech JustEnoughItems Integration Module")
public class JEIModule extends IntegrationSubmodule implements IModPlugin {

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new ChemicalStructureCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        List<ChemicalStructureInfo> chemicalStructureInfos = ChemicalStructureProperty.MATERIALS_WITH_CHEMICAL_STRUCTURE
                .stream()
                .map(ChemicalStructureInfo::new)
                .collect(Collectors.toList());
        String chemicalStructureID = TkTech.MODID + ":" + "chemical_structure_location";
        registry.addRecipes(chemicalStructureInfos, chemicalStructureID);
    }
}
