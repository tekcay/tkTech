package tkcy.simpleaddon.common.metatileentities.primitive;

import net.minecraft.util.ResourceLocation;

import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class PrimitiveCasting extends SimpleMachineMetaTileEntity {

    public PrimitiveCasting(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, ICubeRenderer renderer, int tier,
                            boolean hasFrontFacing) {
        super(metaTileEntityId, TKCYSARecipeMaps.CASTING, renderer, 0, hasFrontFacing);
    }
}
