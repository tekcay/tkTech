package tkcy.tktech.integration.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.gui.GuiTextures;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.integration.jei.basic.BasicRecipeCategory;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import tkcy.tktech.TkTech;
import tkcy.tktech.api.render.ChemicalStructureRenderUtils;

public class ChemicalStructureCategory extends BasicRecipeCategory<ChemicalStructureInfo, ChemicalStructureInfo> {

    private static final int SLOT_CENTER = 79;
    private static final int TEXT_START_X = 5;
    private static final int START_POS_Y = 40;

    protected final IDrawable slot;
    protected final IDrawable icon;
    protected IDrawable chemicalStructure;
    private String materialName;
    private Material material;
    private final IGuiHelper guiHelper;

    public ChemicalStructureCategory(IGuiHelper guiHelper) {
        super("chemical_structure_location",
                "chemical_structure_location.name",
                guiHelper.createBlankDrawable(176, 166),
                guiHelper);

        this.guiHelper = guiHelper;

        this.icon = guiHelper.createDrawableIngredient(OreDictUnifier.get(OrePrefix.ingot, Materials.Copper));
        this.slot = guiHelper
                .drawableBuilder(GuiTextures.SLOT.imageLocation, 0, 0, 18, 18)
                .setTextureSize(18, 18)
                .build();
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayout recipeLayout, @NotNull ChemicalStructureInfo recipeWrapper,
                          @NotNull IIngredients ingredients) {
        this.material = recipeWrapper.getMaterial();
        this.materialName = recipeWrapper.getMaterial().getLocalizedName();
        ItemStack dust = recipeWrapper.getDust();

        int yPosition = 19;

        if (dust != null) {
            IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
            itemStackGroup.init(0, true, SLOT_CENTER, yPosition);
            itemStackGroup.set(0, dust);
            yPosition += 19;
        }

        FluidStack fluidStack = recipeWrapper.getFluidStack();
        if (fluidStack != null) {
            IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
            // fluidStackGroup.init(0, true, SLOT_CENTER, 9, 16, 16, 1, false, null);
            fluidStackGroup.init(0, true, SLOT_CENTER, yPosition);
            fluidStackGroup.set(0, recipeWrapper.getFluidStack());
        }
    }

    @NotNull
    @Override
    public IRecipeWrapper getRecipeWrapper(@NotNull ChemicalStructureInfo recipe) {
        return recipe;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @NotNull
    @Override
    public String getTitle() {
        return I18n.format("tktech.category.chemstructure.title");
    }

    @Override
    public void drawExtras(@NotNull Minecraft minecraft) {
        // GTStringUtils.drawCenteredStringWithCutoff(materialName, minecraft.fontRenderer, 176);
        slot.draw(minecraft, SLOT_CENTER, 19);
        slot.draw(minecraft, SLOT_CENTER, 38);
        // chemicalStructure.draw(minecraft, SLOT_CENTER, 60);

        this.chemicalStructure = guiHelper
                .drawableBuilder(ChemicalStructureRenderUtils.getMoleculeTexture(this.material).imageLocation, 0, 0, 40,
                        40)
                .setTextureSize(40, 40)
                .build();

        chemicalStructure.draw(minecraft, SLOT_CENTER, 60);
    }

    @NotNull
    @Override
    public String getModName() {
        return TkTech.MODID;
    }
}
