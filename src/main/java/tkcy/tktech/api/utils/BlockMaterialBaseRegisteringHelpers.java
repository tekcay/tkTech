package tkcy.tktech.api.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.registry.MaterialRegistry;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.function.TriConsumer;
import gregtech.common.blocks.BlockMaterialBase;
import gregtech.common.blocks.MetaBlocks;

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lombok.experimental.UtilityClass;
import tkcy.tktech.common.block.TkTechMetaBlocks;

@UtilityClass
public class BlockMaterialBaseRegisteringHelpers {

    public static <T extends BlockMaterialBase> void registerOreDict(Map<Material, T> materialToBlockMap,
                                                                     OrePrefix orePrefix) {
        for (Map.Entry<Material, T> entry : materialToBlockMap.entrySet()) {
            Material material = entry.getKey();
            T block = entry.getValue();
            ItemStack itemStack = block.getItem(material);
            OreDictUnifier.registerOre(itemStack, orePrefix, material);
        }
    }

    public static <T extends BlockMaterialBase> void registerColors(List<T> blocks, BlockColors blockColors,
                                                                    ItemColors itemColors) {
        for (T block : blocks) {
            blockColors.registerBlockColorHandler((s, w, p, i) -> block.getGtMaterial(s).getMaterialRGB(), block);
            itemColors.registerItemColorHandler((s, i) -> block.getGtMaterial(s).getMaterialRGB(), block);
        }
    }

    private static <
            T extends BlockMaterialBase> TriConsumer<String, Material[], Integer> getBlockMaterialBaseCreator(Map<Material, T> materialToBlockMap,
                                                                                                              List<T> blocks,
                                                                                                              Function<Material[], T> creator,
                                                                                                              String translationKey) {
        return (modid, materials, index) -> {

            T block = creator.apply(materials);
            block.setRegistryName(modid, translationKey + index);
            for (Material m : materials) {
                materialToBlockMap.put(m, block);
            }
            blocks.add(block);

        };
    }

    public static <
            T extends BlockMaterialBase> void createBlockMaterialBase(Map<Material, T> materialToBlockMap,
                                                                      List<T> blocks,
                                                                      Function<Material[], T> creator,
                                                                      TkTechMetaBlocks.TranslationKeys blocksToRegister,
                                                                      Predicate<Material> doGenerate) {
        createGeneratedBlock(
                doGenerate,
                getBlockMaterialBaseCreator(materialToBlockMap, blocks, creator, blocksToRegister.name()));
    }

    // TODO generates a null block/item. Why ?
    ////
    // 16 by default
    ////
    /**
     * Same method as createGeneratedBlock() in {@link MetaBlocks}.
     */
    public static void createGeneratedBlock(Predicate<Material> materialPredicate,
                                            TriConsumer<String, Material[], Integer> blockGenerator) {
        for (MaterialRegistry registry : GregTechAPI.materialManager.getRegistries()) {
            Int2ObjectMap<Material[]> blocksToGenerate = new Int2ObjectAVLTreeMap<>();
            for (Material material : registry) {
                if (materialPredicate.test(material)) {
                    int id = material.getId();
                    int metaBlockID = id / 16;
                    int subBlockID = id % 16;
                    if (!blocksToGenerate.containsKey(metaBlockID)) {
                        Material[] materials = new Material[16];
                        Arrays.fill(materials, Materials.NULL);
                        blocksToGenerate.put(metaBlockID, materials);
                    }

                    (blocksToGenerate.get(metaBlockID))[subBlockID] = material;
                }
            }
            blocksToGenerate.forEach((key, value) -> blockGenerator.accept(registry.getModid(), value, key));
        }
    }
}
