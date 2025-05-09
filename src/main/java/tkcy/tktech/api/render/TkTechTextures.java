package tkcy.tktech.api.render;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

import lombok.experimental.UtilityClass;
import tkcy.tktech.TkTech;

@UtilityClass
@Mod.EventBusSubscriber(modid = TkTech.MODID, value = Side.CLIENT)
public class TkTechTextures {

    public static OrientedOverlayRenderer ROLLING_MILL_OVERLAY = new OrientedOverlayRenderer("machines/rolling_mill");
    public static SimpleOverlayRenderer WALL_TEXTURE = new SimpleOverlayRenderer("material_sets/dull/wall");
    public static SimpleOverlayRenderer CASING_TEXTURE = new SimpleOverlayRenderer("material_sets/dull/casing");
    public static SimpleOverlayRenderer COIL_TEXTURE = new SimpleOverlayRenderer("material_sets/dull/coil");

    public static void preInit() {
        // ROLLING_MILL_OVERLAY = new OrientedOverlayRenderer("machines/rolling_mill");
    }
}
