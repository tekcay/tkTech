package tkcy.tktech.common.metatileentities.electric;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.RecipeLogicEnergy;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.texture.Textures;

import lombok.Getter;
import tkcy.tktech.api.logic.light.IRequiresLightRecipeLogicMachine;
import tkcy.tktech.api.logic.light.IRequiresNoLightRecipeLogicMachine;
import tkcy.tktech.api.logic.light.LightChemicalReactorLogic;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;

@Getter
public class MTeLightChemicalReactor extends SimpleMachineMetaTileEntity
                                     implements IRequiresLightRecipeLogicMachine, IRequiresNoLightRecipeLogicMachine {

    private final int scanRadius = 3;

    public MTeLightChemicalReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TkTechRecipeMaps.RECIPE_MAP_TEST, Textures.CHEMICAL_REACTOR_OVERLAY, 1, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTeLightChemicalReactor(this.metaTileEntityId);
    }

    @Override
    protected RecipeLogicEnergy createWorkable(RecipeMap<?> recipeMap) {
        return new LightChemicalReactorLogic<>(this, recipeMap, () -> energyContainer);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tktech.light_chemical_reactor.tooltip.1"));
        tooltip.add(I18n.format("tktech.light_chemical_reactor.light_color.tooltip"));
        tooltip.add(I18n.format(TextComponentUtil.translationWithColor(
                TextFormatting.WHITE, "tktech.tooltip.or")
                .getFormattedText()));
        tooltip.add(I18n.format("tktech.light_chemical_reactor.scan_radius.tooltip",
                this.scanRadius, this.scanRadius, this.scanRadius));
    }

    @Override
    @Nullable
    public BlockPos gtLampPos() {
        return getPos().up();
    }

    @Override
    @Nullable
    public BlockPos scanCenterBlockPos() {
        return getPos();
    }

    @Override
    public int scanRadius() {
        return 3;
    }
}
