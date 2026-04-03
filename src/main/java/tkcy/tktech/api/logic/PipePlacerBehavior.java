package tkcy.tktech.api.logic;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import tkcy.tktech.api.utils.IEnumUtils;

public enum PipePlacerBehavior implements IEnumUtils<PipePlacerBehavior> {

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

    //
    //
    // public void serialize(NBTTagCompound nbtTagCompound) {
    // nbtTagCompound.setInteger("PipePlacerBehavior", this.ordinal());
    // }
    //
    // public PipePlacerBehavior deserialize(NBTTagCompound nbtTagCompound) {
    // int ordinal = nbtTagCompound.getInteger("PipePlacerBehavior");
    // return PipePlacerBehavior.values()[ordinal];
    // }
    //
    // public static PipePlacerBehavior nextMode(PipePlacerBehavior currentBehavior) {
    // int currentOrdinal = currentBehavior.ordinal();
    // int newOrdinal = currentOrdinal == 1 ? 0 : currentOrdinal + 1;
    // return PipePlacerBehavior.values()[newOrdinal];
    // }
    //
    //
}
