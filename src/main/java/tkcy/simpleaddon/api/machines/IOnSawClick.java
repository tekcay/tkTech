package tkcy.simpleaddon.api.machines;

import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.items.toolitem.ItemGTTool;
import gregtech.api.items.toolitem.ToolBuilder;
import gregtech.api.items.toolitem.ToolHelper;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.common.items.ToolItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

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
        playSawClick(playerIn);
        return true;
    }

    default void playSawClick(EntityPlayer player) {
        ToolHelper.playToolSound(ToolItems.SAW.getRaw(), player);
    }
}
