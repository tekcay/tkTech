package tkcy.tktech.api.render;

import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ChemicalStructureRenderUtils {

    public static TextureArea getMoleculeTexture(Material material) {
        return TextureArea.fullImage(
                String.format("textures/chemicalstructures/%s.png", material.getResourceLocation().getPath()));
    }

    public static ImageWidget getChemicalStructureWidget(Material material, int x, int y, int size) {
        TextureArea tx = getMoleculeTexture(material);
        return new ImageWidget(x, y, size * (int) tx.imageWidth, size * (int) tx.imageWidth, tx);
    }
}
