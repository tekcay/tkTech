package tkcy.tktech.api.utils;

import net.minecraft.nbt.NBTTagCompound;

public interface IEnumUtils<T extends Enum<?>> {

    T getEnum();

    T[] getValues();

    String getNBTKey();

    default boolean isLastValue() {
        return getEnum().ordinal() + 1 == getValues().length;
    }

    default void serialize(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger(getNBTKey(), getEnum().ordinal());
    }

    default T deserialize(NBTTagCompound nbtTagCompound) {
        int ordinal = nbtTagCompound.getInteger(getNBTKey());
        return getValues()[ordinal];
    }

    default T next() {
        if (isLastValue()) {
            return getValues()[0];
        } else {
            return getValues()[getEnum().ordinal() + 1];
        }
    }
}
