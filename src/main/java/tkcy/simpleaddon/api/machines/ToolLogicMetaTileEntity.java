package tkcy.simpleaddon.api.machines;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;

import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

import tkcy.simpleaddon.api.recipes.logic.ToolRecipeLogic;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;
import tkcy.simpleaddon.modules.toolmodule.WorkingTool;

@WorkingTool
public abstract class ToolLogicMetaTileEntity extends MetaTileEntity {

    protected final ToolRecipeLogic logic;
    protected final RecipeMap<?> recipeMap;

    public ToolLogicMetaTileEntity(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap,
                                   boolean doOutputInWorld) {
        super(metaTileEntityId);
        this.recipeMap = recipeMap;
        this.logic = new ToolRecipeLogic(this, recipeMap, doOutputInWorld);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.tool_machine.sneak_right_click_with_tool.tooltip.1", getWorkingGtTool()));
        addExtraTooltip(stack, player, tooltip, advanced);
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player).build(getHolder(), player);
    }

    @SideOnly(Side.CLIENT)
    protected abstract SimpleOverlayRenderer getBaseRenderer();

    protected abstract ToolsModule.GtTool getWorkingGtTool();

    protected abstract ModularUI.Builder createUITemplate(EntityPlayer entityPlayer);

    protected void addExtraTooltip(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {}
}
