package tkcy.simpleaddon.api.metatileentities;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.Nullable;

import tkcy.simpleaddon.api.utils.StorageUtils;

/**
 *
 * @param <T> can be {@link FluidStack} or {@link ItemStack}
 */
public interface MetaTileEntityStorageFormat<T> {

    void initStorageUtil();

    StorageUtils<T> getStorageUtil();

    @Nullable
    T getContent();

    String getPercentageTranslationKey();

    String getCapacityTranslationKey();

    String getContentTextTranslationKey();

    void displayInfos(List<ITextComponent> textList);
}
