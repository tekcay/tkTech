package tkcy.tktech.api.machines;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

import lombok.Getter;
import tkcy.tktech.api.recipes.logic.OnBlockRecipeLogic;
import tkcy.tktech.modules.toolmodule.ToolsModule;
import tkcy.tktech.modules.toolmodule.WorkingTool;

@Getter
@WorkingTool
public abstract class ToolLogicMetaTileEntity extends MetaTileEntity implements IOnAnyToolClick {

    private final OnBlockRecipeLogic logic;

    public ToolLogicMetaTileEntity(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.logic = initRecipeLogic();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        String tools = getWorkingGtTool()
                .stream()
                .map(ToolsModule.GtTool::toString)
                .collect(Collectors.joining(", "));

        tooltip.add(I18n.format("tktech.tool_machine.sneak_right_click_with_tool.tooltip.1", tools));
        addExtraTooltip(stack, player, tooltip, advanced);
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public boolean showAnyToolClickTooltip() {
        return true;
    }

    @Override
    public void onAnyToolClick(ToolsModule.GtTool tool, boolean isPlayerSneaking) {
        if (!isPlayerSneaking) return;
        this.logic.runToolRecipeLogic(tool);
    }

    @Override
    public void onAnyToolClickTooltip(List<String> tooltips) {
        tooltips.add(I18n.format("tkcysa.metatileentity.on_any_tool_click.sneak.invalidate.tooltip"));
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }

    protected void addExtraTooltip(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {}

    @SideOnly(Side.CLIENT)
    protected abstract SimpleOverlayRenderer getBaseRenderer();

    protected abstract List<ToolsModule.GtTool> getWorkingGtTool();

    protected abstract OnBlockRecipeLogic initRecipeLogic();
}
