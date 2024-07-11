package tkcy.simpleaddon.api.utils;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public final class TKCYSALog {

    public static Logger logger;

    public static void init(@NotNull Logger modLogger) {
        logger = modLogger;
    }

    public static void logItemStackList(@NotNull List<ItemStack> itemStacks) {
        itemStacks.forEach(TKCYSALog::logItemStack);
    }

    public static void logFluidStackList(@NotNull List<FluidStack> itemStacks) {
        itemStacks.forEach(TKCYSALog::logFluidStack);
    }

    public static void logItemStack(@NotNull ItemStack itemStack) {
        logger.info(itemStackToString(itemStack));
    }

    public static void logFluidStack(@NotNull FluidStack fluidStack) {
        logger.info(fluidStackToString(fluidStack));
    }

    public static String itemStackToString(@NotNull ItemStack itemStack) {
        return String.format("%d * %s", itemStack.getCount(), itemStack.getDisplayName());
    }

    public static String fluidStackToString(@NotNull FluidStack fluidStack) {
        return String.format("%d * %s", fluidStack.amount, fluidStack.getLocalizedName());
    }
}
