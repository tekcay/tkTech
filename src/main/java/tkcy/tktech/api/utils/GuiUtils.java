package tkcy.tktech.api.utils;

import net.minecraft.client.gui.FontRenderer;

import gregtech.api.gui.GuiTextures;
import gregtech.api.util.GTStringUtils;

import lombok.experimental.UtilityClass;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;

@UtilityClass
public class GuiUtils {

    /**
     * Standard slot dimension in guis and JEI.
     */
    public static final int SLOT_DIM = 18;

    /**
     * Minecraft's FontRenderer FONT_HEIGHT value.
     */
    public static final int FONT_HEIGHT = 9;

    public static final int STANDARD_JEI_UI_WIDTH = 176;
    public static final int STANDARD_JEI_UI_HEIGHT = 166;

    public static IDrawable standardSlot(IGuiHelper guiHelper) {
        return guiHelper
                .drawableBuilder(GuiTextures.SLOT.imageLocation, 0, 0, SLOT_DIM, SLOT_DIM)
                .setTextureSize(SLOT_DIM, SLOT_DIM)
                .build();
    }

    /**
     * <i>Taken from {@link GTStringUtils#drawCenteredStringWithCutoff(String, FontRenderer, int)}</i>
     * <br>
     * Draws a String centered within a given width.
     * If the String exceeds the given width, it is cutoff.
     * <br>
     * Just adds possiblity to use a custom {@code yOffset}.
     *
     * @param stringToDraw The String to draw
     * @param fontRenderer An instance of the MC FontRenderer
     * @param maxLength    The maximum width of the String
     * @param yOffset      The vertical position of the String
     */
    public static void drawCenteredStringWithCutoff(String stringToDraw, FontRenderer fontRenderer, int maxLength,
                                                    int yOffset) {
        // Account for really long names
        if (fontRenderer.getStringWidth(stringToDraw) > maxLength) {
            stringToDraw = fontRenderer.trimStringToWidth(stringToDraw, maxLength - 3, false) + "...";
        }

        // Ensure that the string is centered
        int startPosition = (maxLength - fontRenderer.getStringWidth(stringToDraw)) / 2;

        fontRenderer.drawString(stringToDraw, startPosition, yOffset, 0x111111);
    }
}
