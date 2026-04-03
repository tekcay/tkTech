package tkcy.tktech.api.logic;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import tkcy.tktech.api.utils.IEnumUtils;

public enum BlockingPipeFaceBehavior implements IEnumUtils<BlockingPipeFaceBehavior> {

    NONE,
    FRONT_FACING,
    OPPOSITE;

    public ITextComponent getMessage() {
        return new TextComponentString("tktech.pipeplacer.blockingFaceBehavior." + this.name());
    }

    @Override
    public BlockingPipeFaceBehavior getEnum() {
        return this;
    }

    @Override
    public BlockingPipeFaceBehavior[] getValues() {
        return values();
    }

    @Override
    public String getNBTKey() {
        return "BlockingPipeFaceBehavior";
    }
}
