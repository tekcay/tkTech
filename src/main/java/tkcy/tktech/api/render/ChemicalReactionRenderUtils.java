package tkcy.tktech.api.render;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import tkcy.tktech.api.utils.RenderUtils;

public class ChemicalReactionRenderUtils {

    public static void render(List<IDrawable> inputs, List<IDrawable> outputs) {}

    public static IDrawable getPlusSign(IGuiHelper guiHelper, double scale) {
        return RenderUtils.buildTextureAreaDrawable(guiHelper, TkTechTextures.REACTION_PLUS, scale);
    }

    public static IDrawable getReactionArrow(IGuiHelper guiHelper, double scale) {
        return RenderUtils.buildTextureAreaDrawable(guiHelper, TkTechTextures.REACTION_ARROW, scale);
    }
}
