package tkcy.tktech.api.utils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MetaTileEntityUtils {

    @Nullable
    public static MetaTileEntity getMetaTileEntity(@Nullable TileEntity tileEntity) {
        if (tileEntity instanceof IGregTechTileEntity gregTechTileEntity) {
            return gregTechTileEntity.getMetaTileEntity();
        } else return null;
    }

    @Nullable
    public static MetaTileEntity getMetaTileEntity(World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        return getMetaTileEntity(tileEntity);
    }
}
