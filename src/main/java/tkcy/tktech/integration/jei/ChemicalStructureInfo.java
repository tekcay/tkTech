package tkcy.tktech.integration.jei;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;

import lombok.Getter;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import tkcy.tktech.api.unification.properties.ChemicalStructureProperty;
import tkcy.tktech.api.unification.properties.TkTechMaterialPropertyKeys;

@Getter
public class ChemicalStructureInfo implements IRecipeWrapper {

    private final Material material;
    private final List<ItemStack> dusts = new ArrayList<>();
    private FluidStack fluidStack;

    private boolean hasFluid = false;
    private boolean hasDust = false;

    private final int chemicalStructureWidth;
    private final int chemicalStructureHeight;

    public ChemicalStructureInfo(Material material) {
        if (!material.hasProperty(TkTechMaterialPropertyKeys.CHEMICAL_STRUCTURE)) {
            throw new IllegalStateException(String.format(
                    "Material %s does not have CHEMICAL_STRUCTURE material property!", material.getUnlocalizedName()));
        }

        this.material = material;
        ChemicalStructureProperty property = ChemicalStructureProperty.INSTANCE.getProperty(material);
        this.chemicalStructureHeight = property.getTextureHeight();
        this.chemicalStructureWidth = property.getTextureWidth();

        if (material.hasFluid()) {
            this.hasFluid = true;
            this.fluidStack = material.getFluid(1000);
        }

        if (material.hasProperty(PropertyKey.DUST)) {
            this.hasDust = true;
            this.dusts.add(OreDictUnifier.get(OrePrefix.dust, material));
            this.dusts.add(OreDictUnifier.get(OrePrefix.dustSmall, material));
            this.dusts.add(OreDictUnifier.get(OrePrefix.dustTiny, material));
        }
    }

    @Override
    public void getIngredients(@NotNull IIngredients ingredients) {
        if (hasDust) {
            ingredients.setOutputs(VanillaTypes.ITEM, dusts);
        }
        if (hasFluid) {
            ingredients.setOutput(VanillaTypes.FLUID, fluidStack);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int fontHeight = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
        minecraft.fontRenderer.drawString(material.getLocalizedName(), 0, fontHeight, 0x111111);
    }
}
