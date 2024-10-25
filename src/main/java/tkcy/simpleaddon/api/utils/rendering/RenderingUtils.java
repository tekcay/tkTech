package tkcy.simpleaddon.api.utils.rendering;

import java.util.Arrays;

import net.minecraft.util.EnumFacing;

import org.apache.commons.lang3.ArrayUtils;

import gregtech.api.unification.material.Material;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;

public class RenderingUtils {

    public static void renderAllSides(SimpleOverlayRenderer texture, CCRenderState renderState, Matrix4 translation,
                                      IVertexOperation[] pipeline) {
        Arrays.stream(EnumFacing.HORIZONTALS).forEach(enumFacing -> texture.renderSided(enumFacing, renderState,
                translation, pipeline));
    }

    public static void renderAllSidesColour(SimpleOverlayRenderer texture, Material material, CCRenderState renderState,
                                            Matrix4 translation,
                                            IVertexOperation[] pipeline) {
        IVertexOperation[] colourPipeline = getColourPipeline(pipeline, material);
        Arrays.stream(EnumFacing.HORIZONTALS).forEach(enumFacing -> texture.renderSided(enumFacing, renderState,
                translation, colourPipeline));
    }

    public static IVertexOperation[] getColourPipeline(IVertexOperation[] pipeline, Material material) {
        return ArrayUtils.add(pipeline,
                new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(material.getMaterialRGB())));
    }
}
