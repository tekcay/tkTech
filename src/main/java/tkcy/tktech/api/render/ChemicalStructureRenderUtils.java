package tkcy.tktech.api.render;

import java.util.List;
import java.util.stream.Collectors;
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
                String.format("textures/chemicalstructures/%s.jpeg", material.getResourceLocation().getPath()));
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

    public static IDrawable buildChemStructureDrawable(IGuiHelper guiHelper, Material material, double scale) {
        ChemicalStructureProperty property = ChemicalStructureProperty.INSTANCE.getProperty(material);
        int height = (int) (property.getTextureHeight() * scale);
        int width = (int) (property.getTextureWidth() * scale);

        TextureArea texture = getMoleculeTexture(material);
        ResourceLocation imageLocation = texture.imageLocation;

        return buildChemStructureDrawable(
                guiHelper,
                imageLocation,
                width,
                height);
    }

    public static IDrawable buildChemStructureDrawable(IGuiHelper guiHelper, Material material) {
        return buildChemStructureDrawable(guiHelper, material, 1.0D);
    }

    public static List<IDrawable> buildChemicalStructures(IGuiHelper guiHelper, List<Material> materials) {
        return buildChemicalStructures(guiHelper, materials, 1.0D);
    }

    public static List<IDrawable> buildChemicalStructures(IGuiHelper guiHelper, List<Material> materials,
                                                          double scale) {
        return materials.stream()
                .map(chemicalStructure -> buildChemStructureDrawable(guiHelper, chemicalStructure, scale))
                .collect(Collectors.toList());
    }
}
