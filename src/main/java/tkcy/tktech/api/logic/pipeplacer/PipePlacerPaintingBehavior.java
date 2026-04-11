package tkcy.tktech.api.logic.pipeplacer;

import tkcy.tktech.api.utils.IEnumUtils;

public enum PipePlacerPaintingBehavior implements IEnumUtils<PipePlacerPaintingBehavior> {

    NONE,
    PAINT,
    REMOVAL;

    @Override
    public PipePlacerPaintingBehavior getEnum() {
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
    public String getButtonLocalizationKey() {
        return "tktech.pipeplacer.pipePlacerPaintingBehavior.button";
    }
}
