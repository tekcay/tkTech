package tkcy.tktech.api.logic.pipeplacer;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import org.jetbrains.annotations.Nullable;

import tkcy.tktech.api.utils.IEnumUtils;
import tkcy.tktech.common.metatileentities.electric.MTePipePlacer;

public enum BlockingPipeFaceBehavior implements IEnumUtils<BlockingPipeFaceBehavior> {

    NONE,
    FRONT_FACING,
    OPPOSITE,
    REMOVAL;

    public ITextComponent getMessage() {
        return new TextComponentString("tktech.pipeplacer.blockingFaceBehavior." + this.name());
    }

    @Nullable
    public EnumFacing getBlockingPipeFace(MTePipePlacer pipePlacer) {
        return switch (this) {
            case OPPOSITE -> pipePlacer.getFrontFacing().getOpposite();
            case FRONT_FACING -> pipePlacer.getFrontFacing();
            default -> null;
        };
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

    @Override
    public String getButtonLocalizationKey() {
        return "tktech.pipeplacer.blockingFaceBehavior.button";
    }
}
