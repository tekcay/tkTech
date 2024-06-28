package tkcy.simpleaddon.api.utils;

import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public final class TKCYSALog {

    public static Logger logger;

    private TKCYSALog() {}

    public static void init(@NotNull Logger modLogger) {
        logger = modLogger;
    }

    public static String stackToString(ItemStack itemStack) {
        return String.format("%d %s", itemStack.getCount(), itemStack.getDisplayName());
    }
}
