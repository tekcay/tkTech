package tkcy.simpleaddon.api.utils;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

public interface FilterStackHandler {

    void setItemStackFilter(@NotNull ItemStack itemStackFilter);

    void resetItemStackFilter();

    FilterStackHandler getItemStackFilter();

    boolean hasItemStackFilter();

    boolean canFillOutputSlot(ItemStack stackToTest);
}
