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

    public static final IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();

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
    public static TextureArea buildGt(String imageLocation, double width, double height) {
        return new TextureArea(GTUtility.gregtechId(imageLocation), 0, 0, width, height);
    }

    public static TextureArea buildGTFromImageLocation(String imageLocation) throws IOException {
        ResourceLocation resourceLocation = GTUtility.gregtechId(imageLocation);
        InputStream inputStream = resourceManager.getResource(resourceLocation).getInputStream();
        BufferedImage image = ImageIO.read(inputStream);
        return new TextureArea(GTUtility.gregtechId(imageLocation), 0, 0, image.getWidth(), image.getHeight());
    }
}
