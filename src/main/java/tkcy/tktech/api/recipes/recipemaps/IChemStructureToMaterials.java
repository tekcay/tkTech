package tkcy.tktech.api.recipes.recipemaps;

import java.util.List;

import gregtech.api.unification.material.Material;

import tkcy.tktech.api.unification.properties.ChemicalStructureProperty;

public interface IChemStructureToMaterials {

    /**
     * @return recipe inputs {@link Material} that have {@link ChemicalStructureProperty} and will be drawn in JEI.
     */
    List<Material> getInputMaterialsChemStructure();

    /**
     * @return recipe outputs {@link Material} that have {@link ChemicalStructureProperty} and will be drawn in JEI.
     */
    List<Material> getOutputMaterialsChemStructure();
}
