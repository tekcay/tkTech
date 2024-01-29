package tkcy.simpleaddon.common.block;

import java.util.*;
import java.util.function.Predicate;

import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.registry.MaterialRegistry;
import gregtech.api.util.function.TriConsumer;

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import tkcy.simpleaddon.api.unification.flags.TKCYSAMaterialFlags;
import tkcy.simpleaddon.api.unification.ore.TKCYSAOrePrefix;

public class TKCYSAMetaBlocks {

    private TKCYSAMetaBlocks() {}

    public static final Map<Material, BlockMaterialCasing> CASINGS = new Object2ObjectOpenHashMap<>();
    public static final List<BlockMaterialCasing> CASINGS_BLOCKS = new ArrayList<>();

    public static void init() {
        createGeneratedBlock(
                material -> (material.hasFlag(TKCYSAMaterialFlags.GENERATE_CASING)),
                TKCYSAMetaBlocks::createCasingBlock);
    }

    ////
    // 16 by default
    ////
    protected static void createGeneratedBlock(Predicate<Material> materialPredicate,
                                               TriConsumer<String, Material[], Integer> blockGenerator) {
        for (MaterialRegistry registry : GregTechAPI.materialManager.getRegistries()) {
            Int2ObjectMap<Material[]> blocksToGenerate = new Int2ObjectAVLTreeMap<>();
            for (Material material : registry) {
                if (materialPredicate.test(material)) {
                    int id = material.getId();
                    int metaBlockID = id / 4;
                    int subBlockID = id % 4;
                    if (!blocksToGenerate.containsKey(metaBlockID)) {
                        Material[] materials = new Material[4];
                        Arrays.fill(materials, Materials.NULL);
                        blocksToGenerate.put(metaBlockID, materials);
                    }

                    (blocksToGenerate.get(metaBlockID))[subBlockID] = material;
                }
            }
            blocksToGenerate.forEach((key, value) -> blockGenerator.accept(registry.getModid(), value, key));
        }
    }

    private static void createCasingBlock(String modid, Material[] materials, int index) {
        BlockMaterialCasing block = BlockMaterialCasing.create(materials);
        block.setRegistryName(modid, "meta_block_casing_" + index);
        for (Material m : materials) {
            TKCYSAMetaBlocks.CASINGS.put(m, block);
        }
        CASINGS_BLOCKS.add(block);
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        // for (BlockMaterialCasing block : CASINGS_BLOCKS) block.onModelRegister();
        CASINGS.values().stream().distinct().forEach(BlockMaterialCasing::onModelRegister);
    }

    @SideOnly(Side.CLIENT)
    public static void registerColors() {
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();

        for (BlockMaterialCasing block : CASINGS_BLOCKS) {
            blockColors.registerBlockColorHandler((s, w, p, i) -> block.getGtMaterial(s).getMaterialRGB(), block);
            itemColors.registerItemColorHandler((s, i) -> block.getGtMaterial(s).getMaterialRGB(), block);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> @NotNull String getPropertyName(@NotNull IProperty<T> property,
                                                                             Comparable<?> value) {
        return property.getName((T) value);
    }

    public static void registerOreDict() {
        for (Map.Entry<Material, BlockMaterialCasing> entry : CASINGS.entrySet()) {
            Material material = entry.getKey();
            BlockMaterialCasing block = entry.getValue();
            ItemStack itemStack = block.getItem(material);
            OreDictUnifier.registerOre(itemStack, TKCYSAOrePrefix.casing, material);
        }
    }
}
