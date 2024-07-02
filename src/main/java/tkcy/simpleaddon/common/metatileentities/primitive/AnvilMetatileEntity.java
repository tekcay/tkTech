package tkcy.simpleaddon.common.metatileentities.primitive;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.simpleaddon.api.metatileentities.ToolLogicMetaTileEntity;
import tkcy.simpleaddon.api.recipes.builders.ToolRecipeBuilder;
import tkcy.simpleaddon.modules.ToolsModule;

public class AnvilMetatileEntity extends ToolLogicMetaTileEntity {

    public AnvilMetatileEntity(ResourceLocation metaTileEntityId, RecipeMap<ToolRecipeBuilder> recipeMap) {
        super(metaTileEntityId, recipeMap);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new GTItemStackHandler(this, 2);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 1, this, false);
    }

    @SideOnly(Side.CLIENT)
    protected SimpleOverlayRenderer getBaseRenderer() {
        return Textures.COKE_BRICKS;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline,
                new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        ColourMultiplier multiplier = new ColourMultiplier(
                GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        colouredPipeline = ArrayUtils.add(pipeline, multiplier);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.SOUTH);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.NORTH);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.EAST);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.WEST);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new AnvilMetatileEntity(this.metaTileEntityId, this.recipeMap);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.2"));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public boolean onHardHammerClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                     CuboidRayTraceResult hitResult) {
        this.logic.startWorking(ToolsModule.GtTool.HARD_HAMMER);
        return true;
    }
}
