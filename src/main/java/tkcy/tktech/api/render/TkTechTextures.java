package tkcy.tktech.api.render;

import java.io.IOException;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

import gregtech.api.gui.resources.AdoptableTextureArea;
import gregtech.api.gui.resources.TextureArea;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

import lombok.experimental.UtilityClass;
import tkcy.tktech.TkTech;
import tkcy.tktech.api.utils.RenderUtils;

@UtilityClass
@Mod.EventBusSubscriber(modid = TkTech.MODID, value = Side.CLIENT)
public class TkTechTextures {

    public static OrientedOverlayRenderer ROLLING_MILL_OVERLAY = new OrientedOverlayRenderer("machines/rolling_mill");
    public static SimpleOverlayRenderer WALL_TEXTURE = new SimpleOverlayRenderer("material_sets/dull/wall");
    public static SimpleOverlayRenderer CASING_TEXTURE = new SimpleOverlayRenderer("material_sets/dull/casing");
    public static SimpleOverlayRenderer COIL_TEXTURE = new SimpleOverlayRenderer("material_sets/dull/coil");

    public static final TextureArea REACTION_BACKGROUND = AdoptableTextureArea.fullImage(
            "textures/chemicalstructures/reaction_background.jpeg", 641,
            363, 0, 0);

    public static final TextureArea REACTION_ARROW;
    public static final TextureArea REACTION_PLUS;

    static {
        try {
            REACTION_ARROW = RenderUtils.buildGTFromImageLocation("textures/chemicalstructures/reaction_arrow.jpeg");
            REACTION_PLUS = RenderUtils.buildGTFromImageLocation("textures/chemicalstructures/reaction_plus.jpeg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void preInit() {
        // ROLLING_MILL_OVERLAY = new OrientedOverlayRenderer("machines/rolling_mill");
    }
}
