package tkcy.tktech.api.render;

import gregtech.api.gui.resources.TextureArea;

import mezz.jei.api.gui.IDrawable;

public interface IChemicalStructureCategory {

    int getBackgroundHeight();

    int getBackgroundWidth();

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

    default TextureArea backgroundImage() {
        return TkTechTextures.REACTION_BACKGROUND;
    }

    default void drawBackground() {
        drawBackground(0, 0);
    }

    /**
     * Draws with {@code width} = {@link #getBackgroundWidth()} and {@code height} = {@link #getBackgroundHeight()}.
     */
    default void drawBackground(int xOffset, int yOffset) {
        drawBackground(xOffset, yOffset, getBackgroundWidth(), getBackgroundHeight());
    }

    default void drawBackground(int xOffset, int yOffset, int width, int height) {
        backgroundImage().draw(xOffset, yOffset, width, height);
    }

    default int getCenterXOffset(int guiWidth, int drawableWidth) {
        return guiWidth / 2 - drawableWidth / 2;
    }

    default int getCenterXOffset(int guiWidth, IDrawable drawable) {
        return getCenterXOffset(guiWidth, drawable.getWidth());
    }
}
