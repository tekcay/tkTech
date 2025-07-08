package tkcy.tktech.api.render;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

import net.minecraft.util.ResourceLocation;

import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import tkcy.tktech.api.unification.properties.ChemicalStructureProperty;

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

    /**
     * Useful for chemical structures vertical alignment.
     */
    public static int getTallestChemStructureHeight(Stream<IDrawable> chemStructures) {
        return chemStructures
                .mapToInt(IDrawable::getHeight)
                .max()
                .orElse(0);
    }

    public static IDrawable buildChemStructureDrawable(IGuiHelper guiHelper, ResourceLocation imageLocation, int width,
                                                       int height) {
        return guiHelper
                .drawableBuilder(imageLocation, 0, 0, width, height)
                .setTextureSize(width, height)
                .build();
    }

    public static Set<IDrawable> buildChemicalStructures(IGuiHelper guiHelper, Set<Material> materials) {
        Set<IDrawable> drawables = new LinkedHashSet<>();
        for (Material material : materials) {
            ChemicalStructureProperty materialProperty = ChemicalStructureProperty.INSTANCE.getProperty(material);

            int width = materialProperty.getTextureWidth() / 2;
            int height = materialProperty.getTextureHeight() / 2;

            ResourceLocation imageLocation = ChemicalStructureRenderUtils.getMoleculeTexture(material).imageLocation;
            drawables.add(buildChemStructureDrawable(guiHelper, imageLocation, width, height));
        }
        return drawables;
    }
}
