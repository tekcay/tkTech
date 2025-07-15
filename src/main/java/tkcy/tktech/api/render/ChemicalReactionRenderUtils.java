package tkcy.tktech.api.render;

import java.util.List;
import java.util.stream.Stream;

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

    public static int getReactionWith(List<IDrawable> inputs, List<IDrawable> outputs, IDrawable plusSign,
                                      IDrawable reactionArrow, int xSpacing) {
        int plusSigns = inputs.size() + outputs.size() - 2;
        int plusSignsSpacing = plusSigns * (plusSign.getWidth() + xSpacing * 2);
        int reactionArrowSpacing = reactionArrow.getWidth() + xSpacing * 2;
        return Stream.concat(inputs.stream(), outputs.stream())
                .mapToInt(IDrawable::getWidth)
                .sum() + plusSignsSpacing + reactionArrowSpacing;
    }

    public static int getReactionXOffset(int reactionWidth, int uiWidth) {
        return (uiWidth - reactionWidth) / 2;
    }
}
