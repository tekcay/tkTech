package tkcy.tktech.api.unification.properties;

import java.util.HashSet;
import java.util.Set;

import com.github.bsideup.jabel.Desugar;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IMaterialProperty;
import gregtech.api.unification.material.properties.MaterialProperties;
import gregtech.api.unification.material.properties.PropertyKey;

@Desugar
public record ChemicalStructureProperty(int textureHeight, int textureWidth) implements IMaterialProperty {

    public static final Set<Material> MATERIALS_WITH_CHEMICAL_STRUCTURE = new HashSet<>();

    @Override
    public void verifyProperty(MaterialProperties properties) {
        properties.ensureSet(PropertyKey.CHEMICAL_STRUCTURE, true);
    }
}
