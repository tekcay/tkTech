package tkcy.tktech.common.block;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import gregtech.api.unification.material.Material;
import gregtech.common.blocks.MetaBlocks;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.experimental.UtilityClass;
import tkcy.tktech.api.unification.flags.TkTechMaterialFlags;
import tkcy.tktech.api.unification.ore.TkTechOrePrefix;
import tkcy.tktech.api.utils.BlockMaterialBaseRegisteringHelpers;

@UtilityClass
public class TkTechMetaBlocks {

    public static final Map<Material, BlockMaterialCasing> CASINGS = new Object2ObjectOpenHashMap<>();
    public static final Map<Material, BlockMaterialWall> WALLS = new Object2ObjectOpenHashMap<>();
    public static final Map<Material, BlockMaterialCoil> COILS = new Object2ObjectOpenHashMap<>();

    public static final List<BlockMaterialCasing> CASINGS_BLOCKS = new ArrayList<>();
    public static final List<BlockMaterialWall> WALLS_BLOCKS = new ArrayList<>();
    public static final List<BlockMaterialCoil> COIL_BLOCKS = new ArrayList<>();

    public static BlockStrippedWood STRIPPED_WOOD;

    public enum TranslationKeys {
        meta_block_casing,
        meta_block_wall,
        meta_block_coil
    }

    public static void init() {
        BlockMaterialBaseRegisteringHelpers.createBlockMaterialBase(CASINGS, CASINGS_BLOCKS,
                BlockMaterialCasing::create,
                TranslationKeys.meta_block_casing,
                material -> material.hasFlag(TkTechMaterialFlags.GENERATE_CASING));

        BlockMaterialBaseRegisteringHelpers.createBlockMaterialBase(WALLS, WALLS_BLOCKS,
                BlockMaterialWall::create,
                TranslationKeys.meta_block_wall,
                material -> material.hasFlag(TkTechMaterialFlags.GENERATE_WALL));

        BlockMaterialBaseRegisteringHelpers.createBlockMaterialBase(COILS, COIL_BLOCKS,
                BlockMaterialCoil::create,
                TranslationKeys.meta_block_coil,
                material -> material.hasFlag(TkTechMaterialFlags.GENERATE_COIL));

        STRIPPED_WOOD = new BlockStrippedWood();
        STRIPPED_WOOD.setRegistryName("stripped_wood");
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        for (BlockMaterialCasing blockMaterialCasing : CASINGS_BLOCKS) blockMaterialCasing.onModelRegister();
        for (BlockMaterialWall blockMaterialWall : WALLS_BLOCKS) blockMaterialWall.onModelRegister();
        for (BlockMaterialCoil blockMaterialCoil : COIL_BLOCKS) blockMaterialCoil.onModelRegister();

        registerItemModel(STRIPPED_WOOD);
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
        BlockMaterialBaseRegisteringHelpers.registerOreDict(CASINGS, TkTechOrePrefix.casing);
        BlockMaterialBaseRegisteringHelpers.registerOreDict(WALLS, TkTechOrePrefix.wall);
        BlockMaterialBaseRegisteringHelpers.registerOreDict(COILS, TkTechOrePrefix.coil);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> @NotNull String getPropertyName(@NotNull IProperty<T> property,
                                                                             Comparable<?> value) {
        return property.getName((T) value);
    }

    /**
     * From {@link MetaBlocks}.
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemModel(Block block) {
        for (IBlockState state : block.getBlockState().getValidStates()) {
            // noinspection ConstantConditions
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
                    block.getMetaFromState(state),
                    new ModelResourceLocation(block.getRegistryName(),
                            MetaBlocks.statePropertiesToString(state.getProperties())));
        }
    }
}
