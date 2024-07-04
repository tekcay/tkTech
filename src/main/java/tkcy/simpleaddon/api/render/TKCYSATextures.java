package tkcy.simpleaddon.api.render;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.TekCaySimpleAddon;

@UtilityClass
@Mod.EventBusSubscriber(modid = TekCaySimpleAddon.MODID, value = Side.CLIENT)
public class TKCYSATextures {

    public static OrientedOverlayRenderer ROLLING_MILL_OVERLAY = new OrientedOverlayRenderer("machines/rolling_mill");

    public static void preInit() {
        // ROLLING_MILL_OVERLAY = new OrientedOverlayRenderer("machines/rolling_mill");
    }
}
