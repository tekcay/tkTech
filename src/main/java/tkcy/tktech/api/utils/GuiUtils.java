package tkcy.tktech.api.utils;

import gregtech.api.gui.GuiTextures;
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
}
