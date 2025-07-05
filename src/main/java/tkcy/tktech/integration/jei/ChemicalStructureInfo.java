package tkcy.tktech.integration.jei;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.gui.resources.TextureArea;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;

import lombok.Getter;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import tkcy.tktech.api.render.ChemicalStructureRenderUtils;

@Getter
public class ChemicalStructureInfo implements IRecipeWrapper {

    private final Material material;
    private final ItemStack dust;
    private final FluidStack fluidStack;

    public ChemicalStructureInfo(Material material) {
        this.material = material;
        if (material.hasFluid()) {
            this.fluidStack = material.getFluid(1000);
        } else this.fluidStack = null;

        if (material.hasProperty(PropertyKey.DUST)) {
            this.dust = OreDictUnifier.getDust(material, 1);
        } else this.dust = null;
    }

    private <T> List<List<T>> list(T t) {
        return Collections.singletonList(Collections.singletonList(t));
    }

    @Override
    public void getIngredients(@NotNull IIngredients ingredients) {
        if (dust != null) {
            ingredients.setInput(VanillaTypes.ITEM, dust);
        }
        if (fluidStack != null) {
            ingredients.setOutput(VanillaTypes.FLUID, fluidStack);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int fontHeight = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;

        int start = 40;
        int linesDrawn = 0;
        minecraft.fontRenderer.drawString(material.getLocalizedName(), 0, fontHeight * linesDrawn + start, 0x111111);
    }

    public TextureArea getChemicalStructure() {
        return ChemicalStructureRenderUtils.getMoleculeTexture(this.material);
    }
}
