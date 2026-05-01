package tkcy.tktech.common.metatileentities.electric;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.RecipeLogicEnergy;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockLamp;

import tkcy.tktech.api.recipes.properties.RequiresLightRecipeProperty;
import tkcy.tktech.api.recipes.properties.RequiresNoLightRecipeProperty;
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

        private boolean mustCheckIfInDark;
        private EnumDyeColor lightColor;

        private void toggle(@Nullable EnumDyeColor enumDyeColor) {
            this.mustCheckIfInDark = !mustCheckIfInDark;
            this.lightColor = enumDyeColor;
        }

        public LightRecipeLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap,
                                Supplier<IEnergyContainer> energyContainer) {
            super(tileEntity, recipeMap, energyContainer);
        }

        private RequiresLightRecipeProperty requiresLightRecipeProperty() {
            return RequiresLightRecipeProperty.getInstance();
        }

        private RequiresNoLightRecipeProperty noLightRecipeProperty() {
            return RequiresNoLightRecipeProperty.getInstance();
        }

        @Override
        protected void decreaseProgress() {}

        @Override
        protected boolean canProgressRecipe() {
            Recipe recipe = getPreviousRecipe();

            if (!this.mustCheckIfInDark &&
                    recipe != null &&
                    recipe.hasProperty(noLightRecipeProperty())) {
                toggle(null);
            }

            if (this.mustCheckIfInDark && !WorldInteractionsHelper.isInTheDark(getMetaTileEntity(), 3)) {
                return false;
            }

            if (this.lightColor == null &&
                    recipe != null &&
                    recipe.hasProperty(requiresLightRecipeProperty())) {
                toggle(requiresLightRecipeProperty().getValueFromRecipe(recipe));
            }

            if (this.lightColor != null && !hasLamp()) {
                return false;
            }

            return super.canProgressRecipe();
        }

        protected boolean hasLamp() {
            BlockPos blockPosToCheck = getMetaTileEntity().getPos().up();
            Block block = BlockStateHelper.getBlockAtBlockPos(blockPosToCheck, getMetaTileEntity().getWorld());
            if (block instanceof BlockLamp blockLamp) {
                return blockLamp.isLightEnabled(blockLamp.blockState.getBaseState()) && blockLamp.color == lightColor;
            } else return false;
        }

        @Override
        public void invalidate() {
            super.invalidate();
            this.lightColor = null;
            this.mustCheckIfInDark = false;
        }

        @Override
        @NotNull
        public NBTTagCompound serializeNBT() {
            NBTTagCompound nbtTagCompound = super.serializeNBT();
            if (this.lightColor != null) {
                requiresLightRecipeProperty().serialize(nbtTagCompound, this.lightColor);
            }
            if (this.mustCheckIfInDark) {
                noLightRecipeProperty().serialize(nbtTagCompound);
            }
            return nbtTagCompound;
        }

        @Override
        public void deserializeNBT(@NotNull NBTTagCompound compound) {
            super.deserializeNBT(compound);
            this.mustCheckIfInDark = noLightRecipeProperty().deserialize(compound);
            this.lightColor = requiresLightRecipeProperty().deserialize(compound);
        }
    }
}
