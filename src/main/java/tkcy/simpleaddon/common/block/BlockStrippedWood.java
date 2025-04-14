package tkcy.simpleaddon.common.block;

import gregtech.api.block.IStateHarvestLevel;
import gregtech.api.block.VariantBlock;
import gregtech.api.items.toolitem.ToolClasses;
import lombok.AllArgsConstructor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.NotNull;

public class BlockStrippedWood extends VariantBlock<BlockStrippedWood.StrippedWoodType> {

    public BlockStrippedWood() {
        super(Material.WOOD);
        setTranslationKey("stripped_wood");
        setHardness(1.0f);
        setResistance(1.0f);
        setSoundType(SoundType.WOOD);
        setDefaultState(getState(StrippedWoodType.OAK_STRIPPED));
    }

    @Override
    public boolean canCreatureSpawn(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos,
                                    @NotNull SpawnPlacementType type) {
        return false;
    }

    @AllArgsConstructor
    public enum StrippedWoodType implements IStringSerializable, IStateHarvestLevel {

        OAK_STRIPPED("oak_stripped"),
        DARK_OAK_STRIPPED("dark_oak_stripped"),
        SPRUCE_STRIPPED("spruce_stripped"),
        BIRCH_STRIPPED("birch_stripped"),
        JUNGLE_STRIPPED("jungle_stripped"),
        ACACIA_STRIPPED("acacia_stripped"),
        RUBBER_STRIPPED("rubber_stripped");

        private final String name;

        @NotNull
        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public int getHarvestLevel(IBlockState state) {
            return 1;
        }

        @Override
        public String getHarvestTool(IBlockState state) {
            return ToolClasses.AXE;
        }
    }
}
