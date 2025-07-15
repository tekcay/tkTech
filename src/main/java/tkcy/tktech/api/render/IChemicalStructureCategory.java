package tkcy.tktech.api.render;

import gregtech.api.gui.resources.TextureArea;

import mezz.jei.api.gui.IDrawable;

public interface IChemicalStructureCategory {

    int getChemicalBackgroundHeight();

    int getChemicalBackgroundWidth();

    int getChemicalBackgroundXOffset();

    int getChemicalBackgroundYOffset();

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
        drawReactionBackground(getChemicalBackgroundXOffset(), getChemicalBackgroundYOffset());
    }

    /**
     * Draws with {@code width} = {@link #getChemicalBackgroundWidth()} and {@code height} =
     * {@link #getChemicalBackgroundHeight()}.
     */
    default void drawReactionBackground(int xOffset, int yOffset) {
        drawReactionBackground(xOffset, yOffset, getChemicalBackgroundWidth(), getChemicalBackgroundHeight());
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
