package tkcy.tktech.api.unification.properties;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.MaterialProperties;
import gregtech.api.unification.material.properties.PropertyKey;
import lombok.Getter;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tkcy.tktech.api.render.ChemicalStructureRenderUtils;
import tkcy.tktech.api.utils.BooleanHelper;
import tkcy.tktech.api.utils.MaterialPropertiesHelper;

import java.util.HashSet;
import java.util.Set;

@ZenClass("mods.tktech.api.unification.properties.ToxicMaterialProperty")
@ZenRegister
@Getter
public class ToxicMaterialProperty extends MaterialPropertiesHelper<ToxicMaterialProperty> {

    private ToxicMaterialProperty() {}
    public static ToxicMaterialProperty INSTANCE = new ToxicMaterialProperty();

    @Override
    public void verifyProperty(MaterialProperties properties) {
        if (!BooleanHelper.doesAnyMatch(properties::hasProperty, PropertyKey.DUST, PropertyKey.FLUID)) {
            throw new IllegalStateException(
                    "ChemicalStructureProperty requires atleast DUST property or FLUID property!");
        }
    }

    @Override
    public PropertyKey<ToxicMaterialProperty> getPropertyKey() {
        return TkTechMaterialPropertyKeys.TOXIC;
    }

    @ZenMethod
    public static void addToxicMaterialProperty(Material material) {
        material.getProperties().setProperty(
                TkTechMaterialPropertyKeys.TOXIC,
                new ToxicMaterialProperty());
    }
}
