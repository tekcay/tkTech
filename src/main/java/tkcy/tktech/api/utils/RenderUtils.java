package tkcy.tktech.api.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.GTUtility;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;

public class RenderUtils {

    public static final IResourceManager MINECRAFT_RESOURCE_MANAGER = Minecraft.getMinecraft().getResourceManager();

    /**
     * Builds {@link IDrawable} from a {@link TextureArea} from JEI category drawing.
     * <br>
     * Keeps texture aspect.
     */
    public static IDrawable buildTextureAreaDrawable(IGuiHelper guiHelper, TextureArea tx, double scale) {
        int width = (int) (tx.imageWidth * scale);
        int height = (int) (tx.imageHeight * scale);
        return guiHelper
                .drawableBuilder(tx.imageLocation, 0, 0, width, height)
                .setTextureSize(width, height)
                .build();
    }

    /**
     * Will use textures located in assets/gregtech/textures
     */
    public static TextureArea buildGTFromImageLocation(String imageLocation) throws IOException {
        ResourceLocation resourceLocation = GTUtility.gregtechId(imageLocation);
        return buildGTFromImageLocation(resourceLocation);
    }

    /**
     * Uses {@link #MINECRAFT_RESOURCE_MANAGER} to retrieve an instance of {@link BufferedImage}.
     * This allows to get the image dimensions from the file itself.
     * <br>
     * Use this when image dimensions are unknown.
     * 
     * @throws IOException if no file can be found or could not be built as an image.
     */
    public static TextureArea buildGTFromImageLocation(ResourceLocation imageLocation) throws IOException {
        InputStream inputStream = MINECRAFT_RESOURCE_MANAGER.getResource(imageLocation).getInputStream();
        BufferedImage image = ImageIO.read(inputStream);
        return new TextureArea(imageLocation, 0, 0, image.getWidth(), image.getHeight());
    }
}
