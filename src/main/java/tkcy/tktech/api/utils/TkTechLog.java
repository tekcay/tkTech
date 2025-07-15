package tkcy.tktech.api.utils;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import lombok.experimental.UtilityClass;
import tkcy.tktech.TkTech;

@UtilityClass
public final class TkTechLog {

    public static Logger logger = LogManager.getLogger(TkTech.MODID);

    public static void init(@NotNull Logger modLogger) {
        logger = modLogger;
    }

    public static void logItemStackList(@NotNull List<ItemStack> itemStacks) {
        itemStacks.forEach(TkTechLog::logItemStack);
    }

    public static void logFluidStackList(@NotNull List<FluidStack> itemStacks) {
        itemStacks.forEach(TkTechLog::logFluidStack);
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
