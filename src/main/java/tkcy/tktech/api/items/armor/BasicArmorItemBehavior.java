package tkcy.tktech.api.items.armor;

import java.util.List;
import java.util.function.BiConsumer;

import net.minecraft.item.ItemStack;

import gregtech.api.items.metaitem.stats.IItemBehaviour;

public class BasicArmorItemBehavior implements IItemBehaviour {

    private final BiConsumer<ItemStack, List<String>> tooltipsSupplier;

    public BasicArmorItemBehavior(BiConsumer<ItemStack, List<String>> tooltipsSupplier) {
        this.tooltipsSupplier = tooltipsSupplier;
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        tooltipsSupplier.accept(itemStack, lines);
    }
}
