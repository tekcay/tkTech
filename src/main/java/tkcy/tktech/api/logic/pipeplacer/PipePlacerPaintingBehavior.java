package tkcy.tktech.api.logic.pipeplacer;

import tkcy.tktech.api.utils.IEnumUtils;

public enum PipePlacerPaintingBehavior implements IEnumUtils<PipePlacerPaintingBehavior> {

    NONE,
    PAINT,
    REMOVE;

    @Override
    public PipePlacerPaintingBehavior getValue() {
        return this;
    }

    @Override
    public PipePlacerPaintingBehavior[] getValues() {
        return values();
    }

    @Override
    public String getNBTKey() {
        return "PipePlacerPaintingBehavior";
    }

    @Override
    public String getBaseLocalizationKey() {
        return "tktech.pipeplacer.pipePlacerPaintingBehavior.";
    }
}
