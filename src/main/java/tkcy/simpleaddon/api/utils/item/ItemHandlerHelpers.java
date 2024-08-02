package tkcy.simpleaddon.api.utils.item;

import net.minecraft.item.ItemStack;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemHandlerHelpers {

    public ItemStack copyWithAmount(ItemStack baseStack, int amount) {
        ItemStack temp = baseStack.copy();
        temp.setCount(amount);
        return temp;
    }
}
