package tkcy.tktech.api.render;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.util.ResourceLocation;

import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTUtility;

import lombok.experimental.UtilityClass;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import stanhebben.zenscript.annotations.ZenMethod;
import tkcy.tktech.api.unification.properties.ChemicalStructureProperty;
import tkcy.tktech.api.utils.RenderUtils;
import tkcy.tktech.api.utils.TkTechLog;

@UtilityClass
public class ChemicalStructureRenderUtils {

    public static final Map<Material, TextureArea> MATERIALS_TO_CHEMSTRUC_TEXTURE = new HashMap<>();

    public static boolean hasChemStructureTexture(Material material) {
        return MATERIALS_TO_CHEMSTRUC_TEXTURE.containsKey(material);
    }

    public static ResourceLocation getChemStructuresResourceLocation(Material material) {
        String path = String.format("textures/chemicalstructures/%s.png", material.getResourceLocation().getPath());
        return GTUtility.gregtechId(path);
    }

    /**
     * Must be called <strong>AFTER</strong> both Materials registration and extra MaterialProperties addition (see
     * {@link ChemicalStructureProperty#addChemicalStructureProperty(Material) addChemicalStructureProperty}).
     */
    @ZenMethod
    public static void registerChemicalStructuresTexture() {
        TkTechLog.logger.info("registering chemical structure textures...");
        for (Material material : ChemicalStructureProperty.MATERIALS_WITH_CHEMICAL_STRUCTURE) {
            try {
                ResourceLocation imageLocation = getChemStructuresResourceLocation(material);
                TextureArea texture = RenderUtils.buildGTFromImageLocation(imageLocation);
                MATERIALS_TO_CHEMSTRUC_TEXTURE.put(material, texture);
            } catch (IOException e) {
                TkTechLog.logger.error("Chemical structure texture of Material {} could not be registered!",
                        material.getResourceLocation());
            }
        }
        TkTechLog.logger.info("registering chemical structure textures done!");
    }

    public static IDrawable getChemStructureIDrawable(IGuiHelper guiHelper, Material material, double scale) {
        ResourceLocation imageLocation = getChemStructuresResourceLocation(material);
        TextureArea tx = getMoleculeTexture(material);
        int width = (int) (tx.imageWidth * scale);
        int height = (int) (tx.imageHeight * scale);
        return guiHelper.drawableBuilder(imageLocation, 0, 0, width, height)
                .setTextureSize(width, height)
                .build();
    }

    public static TextureArea getMoleculeTexture(Material material) {
        return MATERIALS_TO_CHEMSTRUC_TEXTURE.get(material);
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
        TextureArea textureArea = getMoleculeTexture(material);

        int height = (int) (textureArea.imageHeight * scale);
        int width = (int) (textureArea.imageWidth * scale);

        ResourceLocation imageLocation = textureArea.imageLocation;

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
                .map(material -> buildChemStructureDrawable(guiHelper, material, scale))
                .collect(Collectors.toList());
    }
}
