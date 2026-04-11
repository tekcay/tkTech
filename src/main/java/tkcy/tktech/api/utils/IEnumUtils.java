package tkcy.tktech.api.utils;

import java.util.function.IntSupplier;
import java.util.stream.Stream;

import net.minecraft.nbt.NBTTagCompound;

import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.CycleButtonWidget;
import gregtech.api.gui.widgets.LabelWidget;

import codechicken.lib.util.FontUtils;

public interface IEnumUtils<T extends Enum<?>> {

    int PADDING = 3;
    int SIZE = 18;

    T getEnum();

    T[] getValues();

    String getNBTKey();

    String getButtonLocalizationKey();

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

    default String[] valuesString() {
        String[] valuesString = new String[getValues().length];
        StreamHelper.initIntStream(getValues().length)
                .forEach(ordinal -> valuesString[ordinal] = getValues()[ordinal].name());
        return valuesString;
    }

    default void widget(ModularUI.Builder builder, int x, int y, IntSupplier supplier, Runnable consumer) {
        builder.widget(label(x, y));
        cycleButton(builder, x, y + FontUtils.fontRenderer.FONT_HEIGHT, supplier, consumer);
    }

    default Widget label(int x, int y) {
        return new LabelWidget(x, y, getButtonLocalizationKey());
    }

    default void cycleButton(ModularUI.Builder builder, int x, int y, IntSupplier intSupplie, Runnable onComplete) {
        builder.widget(new CycleButtonWidget(
                x,
                y,
                getMaxButtonWidth(this),
                SIZE,
                valuesString(),
                intSupplie,
                i -> onComplete.run()));
    }

    static int getMaxButtonWidth(IEnumUtils<?> enumUtils) {
        return Stream.of(enumUtils.getValues())
                .map(Enum::toString)
                .mapToInt(String::length)
                .max()
                .getAsInt() * 9 + PADDING;
    }
}
