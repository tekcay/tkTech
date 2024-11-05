package tkcy.simpleaddon.api.metatileentities;

import java.util.List;
import java.util.function.Function;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.Nullable;

import tkcy.simpleaddon.api.utils.StorageUtils;
import tkcy.simpleaddon.api.utils.units.CommonUnits;

/**
 *
 * @param <T> can be {@link FluidStack} or {@link ItemStack}
 */
public interface MetaTileEntityStorageFormat<T> {

    StorageUtils<T> getStorageUtil();

    @Nullable
    T getContent();

    CommonUnits getBaseContentUnit();

    String getPercentageTranslationKey();

    String getCapacityTranslationKey();

    String getContentTextTranslationKey();

    void displayInfos(List<ITextComponent> textList);

    int getMaxCapacity();

    Function<T, String> getContentLocalizedNameProvider();

    Function<T, Integer> getContentAmountProvider();

    /**
     * This is what it is displayed between {@link #getBaseContentUnit()} and the result of {@link #getContentLocalizedNameProvider()} in {@link StorageUtils#getContentFormatted()}.
     * <br>
     * Basically:
     * <br>
     * {@literal <amount><unit>}<b>LinkingWord</b>{@literal <content type>.}
     */
    String getLinkingWordForContentDisplay();
}
