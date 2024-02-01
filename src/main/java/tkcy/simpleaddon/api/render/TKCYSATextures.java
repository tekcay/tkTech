package tkcy.simpleaddon.api.render;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

import gregtech.client.renderer.texture.cube.SimpleSidedCubeRenderer;

import tkcy.simpleaddon.TekCaySimpleAddon;

@Mod.EventBusSubscriber(modid = TekCaySimpleAddon.MODID, value = Side.CLIENT)
public final class TKCYSATextures {

    public static final SimpleSidedCubeRenderer BRICK = new SimpleSidedCubeRenderer(
            "primitive/brick");

    public static final SimpleSidedCubeRenderer FIRECLAY_RICK = new SimpleSidedCubeRenderer(
            "primitive/fireclay_rick");

    public static final SimpleSidedCubeRenderer REINFORCED_RICK = new SimpleSidedCubeRenderer(
            "primitive/reinforced_rick");

    public static final SimpleSidedCubeRenderer STRONG_BRICK = new SimpleSidedCubeRenderer(
            "primitive/strong_brick");
}
