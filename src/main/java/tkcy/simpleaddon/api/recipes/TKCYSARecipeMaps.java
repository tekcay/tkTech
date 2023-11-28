package tkcy.simpleaddon.api.recipes;

import gregtech.api.gui.GuiTextures;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.builders.BlastRecipeBuilder;
import gregtech.core.sound.GTSoundEvents;

public final class TKCYSARecipeMaps {

    public static final RecipeMap<BlastRecipeBuilder> ALLOY_BLAST_RECIPES = new RecipeMap<>("alloy_blast_smelter", 9, 0,
            3, 1, new BlastRecipeBuilder(), false)
                    .setSlotOverlay(false, false, false, GuiTextures.FURNACE_OVERLAY_1)
                    .setSlotOverlay(false, false, true, GuiTextures.FURNACE_OVERLAY_1)
                    .setSlotOverlay(false, true, false, GuiTextures.FURNACE_OVERLAY_2)
                    .setSlotOverlay(false, true, true, GuiTextures.FURNACE_OVERLAY_2)
                    .setSlotOverlay(true, true, false, GuiTextures.FURNACE_OVERLAY_2)
                    .setSlotOverlay(true, true, true, GuiTextures.FURNACE_OVERLAY_2)
                    .setSound(GTSoundEvents.FURNACE);

    private TKCYSARecipeMaps() {}
}
