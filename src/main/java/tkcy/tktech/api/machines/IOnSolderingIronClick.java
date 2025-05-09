package tkcy.tktech.api.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

import codechicken.lib.raytracer.CuboidRayTraceResult;

public interface IOnSolderingIronClick {

    /**
     * Called when player clicks a soldering iron on specific side of this meta tile entity
     *
     * @return true if something happened, so the tool will get damaged and animation will be played
     */
    default boolean onSolderingIronClick(EntityPlayer playerIn, EnumHand hand,
                                         EnumFacing wrenchSide,
                                         CuboidRayTraceResult hitResult) {
        return true;
    }
}
