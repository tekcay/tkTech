package tkcy.simpleaddon.api.utils.item;

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
}
