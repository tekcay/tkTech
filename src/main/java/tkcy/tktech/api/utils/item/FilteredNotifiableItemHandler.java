package tkcy.tktech.api.utils.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;

public class FilteredNotifiableItemHandler extends NotifiableItemStackHandler {

    private final List<Predicate<ItemStack>> slotIndexesToFillPredicate;

    public FilteredNotifiableItemHandler(MetaTileEntity metaTileEntity, int slots, MetaTileEntity entityToNotify,
                                         boolean isExport) {
        super(metaTileEntity, slots, entityToNotify, isExport);
        this.slotIndexesToFillPredicate = new ArrayList<>();
        IntStream.range(0, slots)
                .boxed()
                .forEach(slotIndex -> slotIndexesToFillPredicate.add(slotIndex, itemStack -> true));
    }

    public FilteredNotifiableItemHandler setFillPredicate(Predicate<ItemStack> fillPredicate, int slot) {
        validateSlotIndex(slot);
        slotIndexesToFillPredicate.set(slot, fillPredicate);
        return this;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        validateSlotIndex(slot);
        Predicate<ItemStack> fillPredicate = slotIndexesToFillPredicate.get(slot);
        return fillPredicate == null || fillPredicate.test(stack);
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        if (isItemValid(slot, stack)) return super.insertItem(slot, stack, simulate);
        else return stack;
    }
}
