package tkcy.tktech.api.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

import gregtech.api.items.toolitem.ToolHelper;
import gregtech.common.items.ToolItems;

import codechicken.lib.raytracer.CuboidRayTraceResult;

public interface IOnFileClick {

    /**
     * Called when player clicks a file on specific side of this meta tile entity
     *
     * @return true if something happened, so the tool will get damaged and animation will be played
     */
    default boolean onFileClick(EntityPlayer playerIn, EnumHand hand,
                                EnumFacing wrenchSide,
                                CuboidRayTraceResult hitResult) {
        playFileClickSound(playerIn);
        return true;
    }

    default void playFileClickSound(EntityPlayer player) {
        ToolHelper.playToolSound(ToolItems.FILE.getRaw(), player);
    }
}
