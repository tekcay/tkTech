package tkcy.simpleaddon.api.recipes.properties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.unification.material.Material;
import gregtech.common.blocks.BlockWireCoil;
import tkcy.simpleaddon.modules.RecipePropertiesKey;

public class CoilTypeProperty extends RecipeProperty<BlockWireCoil.CoilType> {

    public static final String KEY = RecipePropertiesKey.COIL_KEY;

    private static CoilTypeProperty INSTANCE;

    private CoilTypeProperty() {
        super(KEY, BlockWireCoil.CoilType.class);
    }

    public static CoilTypeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CoilTypeProperty();
        }
        return INSTANCE;
    }

    private static String getInfo(Object value) {
        BlockWireCoil.CoilType coilType = (BlockWireCoil.CoilType) value;
        Material coilMaterial = coilType.getMaterial();
        return coilMaterial == null ? "invalid" : coilMaterial.getLocalizedName();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tkcysa.recipe.coil",
                getInfo(value)), x, y, color);
    }
}
