package tkcy.simpleaddon.api.utils;

import static gregtech.api.util.GTTransferUtils.insertItem;

import java.util.stream.IntStream;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import gregtech.api.capability.impl.FilteredItemHandler;
import gregtech.api.util.GTLog;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemHandlerHelpers {

    public static boolean setFillPredicate(FilteredItemHandler filteredItemHandler, ItemStack itemStackToTest) {
        if (filteredItemHandler.getSlots() == 0) return false;
        if (itemStackToTest.isEmpty()) return false;
        boolean canFill = IntStream.range(0, filteredItemHandler.getSlots())
                .boxed()
                .anyMatch(slotIndex -> !filteredItemHandler.isItemValid(slotIndex, itemStackToTest));
        if (!canFill) return false;
        filteredItemHandler.setFillPredicate(itemStack -> itemStack.isItemEqual(itemStackToTest));
        return true;
    }

    public static boolean moveHandlerToFiltered(IItemHandler sourceInventory, TKFilteredItemHandler targetInventory) {
        boolean didItTransferSomething = false;
        for (int srcIndex = 0; srcIndex < sourceInventory.getSlots(); srcIndex++) {
            ItemStack sourceStack = sourceInventory.extractItem(srcIndex, Integer.MAX_VALUE, true);
            if (sourceStack.isEmpty()) {
                continue;
            }
            GTLog.logger.info("try to insert : " + sourceStack.getDisplayName());

            if (targetInventory.hasItemFillPredicate()) {
                GTLog.logger.info("has predicate(moveHGandler) ");
                GTLog.logger.info("filter is " + targetInventory.getContentStack().getDisplayName());
                if (targetInventory.getItemFillPredicate().test(sourceStack)) {
                    GTLog.logger.info("mayched predicate : " + sourceStack.getDisplayName());
                    ItemStack remainder = insertItem(targetInventory, sourceStack, true);
                    int amountToInsert = sourceStack.getCount() - remainder.getCount();
                    if (amountToInsert > 0) {
                        sourceStack = sourceInventory.extractItem(srcIndex, amountToInsert, false);
                        insertItem(targetInventory, sourceStack, false);
                        didItTransferSomething = true;
                    }
                }
            }
        }
        return didItTransferSomething;
    }
}
