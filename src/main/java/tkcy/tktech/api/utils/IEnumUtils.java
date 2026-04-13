package tkcy.tktech.api.utils;

import java.util.function.IntSupplier;
import java.util.stream.Stream;

import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;

import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.CycleButtonWidget;
import gregtech.api.gui.widgets.LabelWidget;

import codechicken.lib.util.FontUtils;

public interface IEnumUtils<T extends Enum<?>> {

    int PADDING = 3;
    int SIZE = 18;

    T getValue();

    T[] getValues();

    String getNBTKey();

    String getBaseLocalizationKey();

    default boolean isLastValue() {
        return getValue().ordinal() + 1 == getValues().length;
    }

    default void serialize(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger(getNBTKey(), getValue().ordinal());
    }

    default T deserialize(NBTTagCompound nbtTagCompound) {
        int ordinal = nbtTagCompound.getInteger(getNBTKey());
        return getValues()[ordinal];
    }

    default T next() {
        if (isLastValue()) {
            return getValues()[0];
        } else {
            return getValues()[getValue().ordinal() + 1];
        }
    }

    default void widget(ModularUI.Builder builder, int x, int y, IntSupplier supplier, Runnable consumer) {
        builder.widget(label(x, y));
        cycleButton(builder, x, y + FontUtils.fontRenderer.FONT_HEIGHT, supplier, consumer);
    }

    default Widget label(int x, int y) {
        return new LabelWidget(x, y, getBaseLocalizationKey() + "button");
    }

    default void cycleButton(ModularUI.Builder builder, int x, int y, IntSupplier intSupplie, Runnable onComplete) {
        builder.widget(new CycleButtonWidget(
                x,
                y,
                getMaxButtonWidth(this),
                SIZE,
                valuesLocalizationKeys(this),
                intSupplie,
                i -> onComplete.run()));
    }

    static String[] valuesLocalizationKeys(IEnumUtils<?> iEnumUtils) {
        String[] valuesString = new String[iEnumUtils.getValues().length];

        for (int ordinal = 0; ordinal < iEnumUtils.getValues().length; ordinal++) {
            valuesString[ordinal] = iEnumUtils.getBaseLocalizationKey() + iEnumUtils.getValues()[ordinal].name();
        }

        return valuesString;
    }

    static int getMaxButtonWidth(IEnumUtils<?> enumUtils) {
        return Stream.of(valuesLocalizationKeys(enumUtils))
                .map(I18n::format)
                .mapToInt(String::length)
                .max()
                .orElse(0) * 9 + PADDING;
    }
}
