package tkcy.tktech.api.unification.properties;

import java.util.HashSet;
import java.util.Set;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.MaterialProperties;
import gregtech.api.unification.material.properties.PropertyKey;

import crafttweaker.annotations.ZenRegister;
import lombok.AllArgsConstructor;
import lombok.Getter;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tkcy.tktech.api.utils.BooleanHelper;
import tkcy.tktech.api.utils.MaterialPropertiesHelper;

@ZenClass("mods.tktech.api.unification.properties.ChemicalStructureProperty")
@ZenRegister
@AllArgsConstructor
@Getter
public class ChemicalStructureProperty extends MaterialPropertiesHelper<ChemicalStructureProperty> {

    private int textureHeight, textureWidth;

    private ChemicalStructureProperty() {}

    public static final Set<Material> MATERIALS_WITH_CHEMICAL_STRUCTURE = new HashSet<>();
    public static ChemicalStructureProperty INSTANCE = new ChemicalStructureProperty();

    @Override
    public void verifyProperty(MaterialProperties properties) {
        if (!BooleanHelper.doesAnyMatch(properties::hasProperty, PropertyKey.DUST, PropertyKey.FLUID)) {
            throw new IllegalStateException(
                    "ChemicalStructureProperty requires atleast DUST property or FLUID property!");
        }
    }

    @Override
    public PropertyKey<ChemicalStructureProperty> getPropertyKey() {
        return TkTechMaterialPropertyKeys.CHEMICAL_STRUCTURE;
    }

    @ZenMethod
    public static void addChemicalStructureProperty(Material material, int textureHeight, int textureWidth) {
        material.getProperties().setProperty(
                TkTechMaterialPropertyKeys.CHEMICAL_STRUCTURE,
                new ChemicalStructureProperty(textureHeight, textureWidth));
        ChemicalStructureProperty.MATERIALS_WITH_CHEMICAL_STRUCTURE.add(material);
    }
}
