package tkcy.tktech.api.render;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

import gregtech.api.gui.resources.AdoptableTextureArea;
import gregtech.api.gui.resources.TextureArea;
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

    public static final TextureArea REACTION_BACKGROUND = AdoptableTextureArea.fullImage(
            "textures/chemicalstructures/reaction_background.jpeg", 641,
            363, 0, 0);

    /**
     * Width = 138
     * Height = 37
     */
    public static final TextureArea REACTION_ARROW = TextureArea
            .fullImage("textures/chemicalstructures/reaction_arrow.jpeg");
    /**
     * Width = 47
     * Height = 75
     */
    public static final TextureArea REACTION_PLUS = TextureArea
            .fullImage("textures/chemicalstructures/reaction_plus.jpeg");

    public static void preInit() {
        // ROLLING_MILL_OVERLAY = new OrientedOverlayRenderer("machines/rolling_mill");
    }
}
