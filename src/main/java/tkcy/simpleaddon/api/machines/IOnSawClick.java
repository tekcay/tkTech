package tkcy.simpleaddon.api.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

import gregtech.api.items.toolitem.ToolHelper;
import gregtech.common.items.ToolItems;

import codechicken.lib.raytracer.CuboidRayTraceResult;

public interface IOnSawClick {

    /**
     * Called when player clicks an saw on specific side of this meta tile entity
     *
     * @return true if something happened, so the tool will get damaged and animation will be played
     */
    default boolean onSawClick(EntityPlayer playerIn, EnumHand hand,
                               EnumFacing wrenchSide,
                               CuboidRayTraceResult hitResult) {
        return true;
    }

    default void playSawClickSound(EntityPlayer player) {
        ToolHelper.playToolSound(ToolItems.FILE.getRaw(), player);
    }
}
