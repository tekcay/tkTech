package tkcy.tktech.api.logic.pipeplacer;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import tkcy.tktech.api.utils.IEnumUtils;

public enum PipePlacerBehavior implements IEnumUtils<PipePlacerBehavior> {

    NONE,
    PLACE,
    REMOVAL;

    public ITextComponent getMessage() {
        return new TextComponentString("tktech.pipeplacer.PipePlacerBehavior." + this.name());
    }

    @Override
    public PipePlacerBehavior getEnum() {
        return this;
    }

    @Override
    public PipePlacerBehavior[] getValues() {
        return values();
    }

    @Override
    public String getNBTKey() {
        return "PipePlacerBehavior";
    }

    @Override
    public String getButtonLocalizationKey() {
        return "tktech.pipeplacer.pipePlacerBehavior.button";
    }
}
