package tkcy.tktech.common;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.IBlockOre;
import gregtech.common.blocks.BlockCompressed;
import gregtech.common.blocks.BlockFrame;

import tkcy.tktech.TkTech;
import tkcy.tktech.api.render.TkTechTextures;
import tkcy.tktech.api.unification.properties.ITooltipMaterialProperty;
import tkcy.tktech.common.block.TkTechMetaBlocks;
import tkcy.tktech.common.item.TkTechToolItems;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = TkTech.MODID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preLoad() {
        super.preLoad();
        TkTechTextures.preInit();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        TkTechMetaBlocks.registerItemModels();
        TkTechToolItems.registerModels();
    }

    @SubscribeEvent
    public static void addMaterialFormulaHandler(@NotNull ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof ItemBlock) {
            Block block = ((ItemBlock) itemStack.getItem()).getBlock();
            if (!(block instanceof BlockFrame) && !(block instanceof BlockCompressed) &&
                    !(block instanceof IBlockOre) && !(block instanceof IFluidBlock)) {
                // Do not apply this tooltip to blocks other than:
                // - Frames
                // - Compressed Blocks
                // - Ores
                // - Fluids
                return;
            }
        }

        // Handles Item tooltips
        List<String> tooltips = new ArrayList<>();

        // Test for Items
        UnificationEntry unificationEntry = OreDictUnifier.getUnificationEntry(itemStack);

        if (unificationEntry != null && unificationEntry.material != null) {
            ITooltipMaterialProperty.addTooltips(unificationEntry.material, tooltips);
        }

        if (!tooltips.isEmpty()) {
            for (String s : tooltips) {
                if (s == null || s.isEmpty()) continue;
                event.getToolTip().add(s);
            }
        }
    }

    @Override
    public void onLoad() {
        registerColors();
    }

    public static void registerColors() {
        TkTechToolItems.registerColors();
        TkTechMetaBlocks.registerColors();
    }
}
