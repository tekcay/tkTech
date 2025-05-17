package tkcy.tktech.api.recipes.logic.markers;

import gregtech.api.capability.impl.AbstractRecipeLogic;

import tkcy.tktech.mixins.gregtech.MixinWorkableInfoTOPModule;

/**
 * Implement an {@link AbstractRecipeLogic} with this to tell TOP to not display the recipe duration when running.
 * This is effectively done in {@link MixinWorkableInfoTOPModule}.
 */
public interface IHideRecipeProgress {}
