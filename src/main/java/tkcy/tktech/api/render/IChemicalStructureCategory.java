package tkcy.tktech.api.render;

import gregtech.api.gui.resources.TextureArea;

import mezz.jei.api.gui.IDrawable;

public interface IChemicalStructureCategory {

    int getReactionBackgroundHeight();

    int getReactionBackgroundWidth();

    int getReactionBackgroundXOffset();

    int getReactionBackgroundYOffset();

    default int ySpacing() {
        return 10;
    }

    default int xSpacing() {
        return 10;
    }

    default int xMargin() {
        return 20;
    }

    default int yMargin() {
        return 20;
    }

    default TextureArea reactionBackgroundImage() {
        return TkTechTextures.REACTION_BACKGROUND;
    }

    default void drawReactionBackground() {
        drawReactionBackground(getReactionBackgroundXOffset(), getReactionBackgroundYOffset());
    }

    /**
     * Draws with {@code width} = {@link #getReactionBackgroundWidth()} and {@code height} =
     * {@link #getReactionBackgroundHeight()}.
     */
    default void drawReactionBackground(int xOffset, int yOffset) {
        drawReactionBackground(xOffset, yOffset, getReactionBackgroundWidth(), getReactionBackgroundHeight());
    }

    default void drawReactionBackground(int xOffset, int yOffset, int width, int height) {
        reactionBackgroundImage().draw(xOffset, yOffset, width, height);
    }

    default int getCenterXOffset(int guiWidth, int drawableWidth) {
        return guiWidth / 2 - drawableWidth / 2;
    }

    default int getCenterXOffset(int guiWidth, IDrawable drawable) {
        return getCenterXOffset(guiWidth, drawable.getWidth());
    }
}
