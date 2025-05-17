package tkcy.tktech.api.recipes.logic.markers;

import gregtech.api.capability.impl.AbstractRecipeLogic;

import tkcy.tktech.mixins.gregtech.MixinRecipeLogicTOPModule;

/**
 * Implement an {@link AbstractRecipeLogic} with this to tell TOP to not display the energy consumption when running.
 * This is effectively done in {@link MixinRecipeLogicTOPModule}.
 */
public interface IHideEnergyRecipeLogic {}
