package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.PrimitiveRecipeLogic;
import gregtech.api.gui.ModularUI;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.multi.MetaTileEntityCokeOven;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class PrimitiveRoastingOven extends MetaTileEntityCokeOven {

    public PrimitiveRoastingOven(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.recipeMapWorkable = new PrimitiveRecipeLogic(this, TKCYSARecipeMaps.PRIMITIVE_ROASTING);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "-X-")
                .aisle("XXX", "X#X", "-X-")
                .aisle("XXX", "XYX", "-X-")
                .where('X',
                        states(getCasingState())
                                .or(metaTileEntities(MetaTileEntities.COKE_OVEN_HATCH).setMaxGlobalLimited(2)))
                .where('#', air())
                .where('-', any())
                .where('Y', selfPredicate())
                .build();
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                CuboidRayTraceResult hitResult) {
        return false;
    }
}
