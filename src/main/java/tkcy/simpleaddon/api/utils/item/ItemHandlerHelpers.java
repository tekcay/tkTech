package tkcy.simpleaddon.api.utils.item;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemHandlerHelpers {

    public static final Function<IItemHandler, Stream<ItemStack>> handlerToStacksInSlots = itemHandler -> IntStream
            .range(0, itemHandler.getSlots())
            .boxed()
            .map(itemHandler::getStackInSlot);

    public ItemStack copyWithAmount(ItemStack baseStack, int amount) {
        ItemStack temp = baseStack.copy();
        temp.setCount(amount);
        return temp;
    }

    // Untested
    public boolean removeStack(IItemHandler handler, List<ItemStack> stacksToRemove, boolean simulate) {
        for (int slot = 0; slot < handler.getSlots(); slot++) {
            ItemStack handlerStack = handler.getStackInSlot(slot);

            for (ItemStack itemStack : stacksToRemove) {
                if (handlerStack.isItemEqual(itemStack)) {
                    ItemStack extractedStack = handler.extractItem(slot, itemStack.getCount(), true);
                    if (!extractedStack.isEmpty()) itemStack.shrink(extractedStack.getCount());
                    if (itemStack.isEmpty()) stacksToRemove.remove(itemStack);
                }
            }
        }

        return stacksToRemove.isEmpty();
    }
}
