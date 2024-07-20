package tkcy.simpleaddon.api.utils;

import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GTLog;

import lombok.Getter;

@Getter
public class TKFilteredItemHandler extends GTItemStackHandler {

    private Predicate<ItemStack> itemFillPredicate;
    private ItemStack predicateStack;
    private ItemStack contentStack;

    public TKFilteredItemHandler(MetaTileEntity metaTileEntity) {
        super(metaTileEntity);
    }

    public TKFilteredItemHandler(MetaTileEntity metaTileEntity, int size) {
        super(metaTileEntity, size);
    }

    public boolean hasItemFillPredicate() {
        return this.itemFillPredicate != null;
    }

    public void setItemFillPredicate(@NotNull ItemStack itemStack) {
        GTLog.logger.info("set filter to " + itemStack.getDisplayName());
        this.predicateStack = itemStack;
        this.contentStack = itemStack;
        this.itemFillPredicate = stack -> stack.isItemEqual(itemStack);
    }

    public void reset() {
        this.contentStack = null;
        this.itemFillPredicate = null;
        this.predicateStack = null;
    }

    public boolean trySetFillPredicate(@NotNull IItemHandler itemHandler) {
        if (itemHandler.getSlots() == 0) return false;

        ItemStack filter = this.setContentStackFilter(itemHandler);
        if (filter == null) return false;

        this.setItemFillPredicate(filter);
        return true;
    }

    public static Stream<ItemStack> get(@NotNull IItemHandler itemHandler) {
        return IntStream.range(0, itemHandler.getSlots())
                .boxed()
                .map(itemHandler::getStackInSlot)
                .filter(itemStack -> !itemStack.isEmpty());
    }

    @Nullable
    private ItemStack setContentStackFilter(@NotNull IItemHandler itemHandler) {
        return get(itemHandler)
                .findFirst()
                .orElse(null);
    }

    public void updateContent() {
        if (!this.hasItemFillPredicate()) {
            GTLog.logger.info("update : no predicate");
            this.reset();
            return;
        }

        int amount = get(this)
                .map(ItemStack::getCount)
                .reduce(0, Math::addExact);

        GTLog.logger.info("content amount : " + amount);
        GTLog.logger.info("content : " + contentStack.getDisplayName());
        this.contentStack.setCount(amount);
    }
}
