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
    public static final Map<Material, BlockMaterialWall> WALLS = new Object2ObjectOpenHashMap<>();
    public static final Map<Material, BlockMaterialCoil> COILS = new Object2ObjectOpenHashMap<>();

    public static final List<BlockMaterialCasing> CASINGS_BLOCKS = new ArrayList<>();
    public static final List<BlockMaterialWall> WALLS_BLOCKS = new ArrayList<>();
    public static final List<BlockMaterialCoil> COIL_BLOCKS = new ArrayList<>();

    public enum TranslationKeys {
        meta_block_casing,
        meta_block_wall,
        meta_block_coil
    }

    public static void init() {
        BlockMaterialBaseRegisteringHelpers.createBlockMaterialBase(CASINGS, CASINGS_BLOCKS,
                BlockMaterialCasing::create,
                TranslationKeys.meta_block_casing,
                material -> material.hasFlag(TKCYSAMaterialFlags.GENERATE_CASING));

        BlockMaterialBaseRegisteringHelpers.createBlockMaterialBase(WALLS, WALLS_BLOCKS,
                BlockMaterialWall::create,
                TranslationKeys.meta_block_wall,
                material -> material.hasFlag(TKCYSAMaterialFlags.GENERATE_WALL));

        BlockMaterialBaseRegisteringHelpers.createBlockMaterialBase(COILS, COIL_BLOCKS,
                BlockMaterialCoil::create,
                TranslationKeys.meta_block_coil,
                material -> material.hasFlag(TKCYSAMaterialFlags.GENERATE_COIL));
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        for (BlockMaterialCasing blockMaterialCasing : CASINGS_BLOCKS) blockMaterialCasing.onModelRegister();
        for (BlockMaterialWall blockMaterialWall : WALLS_BLOCKS) blockMaterialWall.onModelRegister();
        for (BlockMaterialCoil blockMaterialCoil : COIL_BLOCKS) blockMaterialCoil.onModelRegister();
    }

    @SideOnly(Side.CLIENT)
    public static void registerColors() {
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();

        BlockMaterialBaseRegisteringHelpers.registerColors(CASINGS_BLOCKS, blockColors, itemColors);
        BlockMaterialBaseRegisteringHelpers.registerColors(WALLS_BLOCKS, blockColors, itemColors);
        BlockMaterialBaseRegisteringHelpers.registerColors(COIL_BLOCKS, blockColors, itemColors);
    }

    public static void registerOreDict() {
        BlockMaterialBaseRegisteringHelpers.registerOreDict(CASINGS, TKCYSAOrePrefix.casing);
        BlockMaterialBaseRegisteringHelpers.registerOreDict(WALLS, TKCYSAOrePrefix.wall);
        BlockMaterialBaseRegisteringHelpers.registerOreDict(COILS, TKCYSAOrePrefix.coil);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> @NotNull String getPropertyName(@NotNull IProperty<T> property,
                                                                             Comparable<?> value) {
        return property.getName((T) value);
    }
}
