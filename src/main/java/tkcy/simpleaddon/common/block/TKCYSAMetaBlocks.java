package tkcy.simpleaddon.common.block;

import java.util.*;

import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import gregtech.api.unification.material.Material;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.unification.flags.TKCYSAMaterialFlags;
import tkcy.simpleaddon.api.unification.ore.TKCYSAOrePrefix;
import tkcy.simpleaddon.api.utils.BlockMaterialBaseRegisteringHelpers;

@UtilityClass
public class TKCYSAMetaBlocks {

    public static final Map<Material, BlockMaterialCasing> CASINGS = new Object2ObjectOpenHashMap<>();
    public static final List<BlockMaterialCasing> CASINGS_BLOCKS = new ArrayList<>();

    public enum TranslationKeys {
        meta_block_casing
    }

    public static void init() {
        BlockMaterialBaseRegisteringHelpers.createBlockMaterialBase(CASINGS, CASINGS_BLOCKS,
                BlockMaterialCasing::build,
                TranslationKeys.meta_block_casing,
                material -> material.hasFlag(TKCYSAMaterialFlags.GENERATE_CASING));
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        for (BlockMaterialCasing blockMaterialCasing : CASINGS_BLOCKS) blockMaterialCasing.onModelRegister();
    }

    @SideOnly(Side.CLIENT)
    public static void registerColors() {
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();

        BlockMaterialBaseRegisteringHelpers.registerColors(CASINGS_BLOCKS, blockColors, itemColors);
    }

    public static void registerOreDict() {
        BlockMaterialBaseRegisteringHelpers.registerOreDict(CASINGS, TKCYSAOrePrefix.casing);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> @NotNull String getPropertyName(@NotNull IProperty<T> property,
                                                                             Comparable<?> value) {
        return property.getName((T) value);
    }
}
