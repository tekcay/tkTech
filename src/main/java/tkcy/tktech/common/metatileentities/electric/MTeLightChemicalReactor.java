package tkcy.tktech.common.metatileentities.electric;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.RecipeLogicEnergy;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockLamp;

import tkcy.tktech.api.recipes.properties.LightRecipeProperty;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.api.utils.BlockStateHelper;
import tkcy.tktech.api.utils.WorldInteractionsHelper;

public class MTeLightChemicalReactor extends SimpleMachineMetaTileEntity {

    public MTeLightChemicalReactor(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TkTechRecipeMaps.RECIPE_MAP_TEST, Textures.CHEMICAL_REACTOR_OVERLAY, 1, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTeLightChemicalReactor(this.metaTileEntityId);
    }

    @Override
    protected RecipeLogicEnergy createWorkable(RecipeMap<?> recipeMap) {
        return new LightRecipeLogic(this, recipeMap, () -> energyContainer);
    }

    private static class LightRecipeLogic extends RecipeLogicEnergy {

        public LightRecipeLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap,
                                Supplier<IEnergyContainer> energyContainer) {
            super(tileEntity, recipeMap, energyContainer);
        }

        @Override
        protected void decreaseProgress() {}

        @Override
        protected boolean canProgressRecipe() {
            Recipe recipe = getPreviousRecipe();
            if (recipe != null && recipe.hasProperty(LightRecipeProperty.getInstance())) {
                EnumDyeColor color = LightRecipeProperty.getInstance().getRequiredLightFromRecipe(recipe);

                if (color != null && !hasLamp(color)) {
                    return false;
                }
                if (color == null && !WorldInteractionsHelper.isInTheDark(getMetaTileEntity(), 3)) {
                    return false;
                }
            }
            return super.canProgressRecipe();
        }

        protected boolean hasLamp(@NotNull EnumDyeColor enumDyeColor) {
            BlockPos blockPosToCheck = getMetaTileEntity().getPos().up();
            Block block = BlockStateHelper.getBlockAtBlockPos(blockPosToCheck, getMetaTileEntity().getWorld());
            if (block instanceof BlockLamp blockLamp) {
                return blockLamp.isLightEnabled(blockLamp.blockState.getBaseState()) && blockLamp.color == enumDyeColor;
            } else return false;
        }
    }
}
