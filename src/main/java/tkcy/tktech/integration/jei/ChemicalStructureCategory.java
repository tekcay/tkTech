package tkcy.tktech.integration.jei;

import static tkcy.tktech.api.utils.GuiUtils.SLOT_DIM;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.gui.GuiTextures;
import gregtech.api.unification.OreDictUnifier;
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

    private final IGuiHelper guiHelper;

    protected final IDrawable slot;
    protected final IDrawable icon;
    protected IDrawable chemicalStructure;

    private ChemicalStructureInfo info;
    private final int ingredientsYoffset = 20;
    private final int ingredientsXoffset = 20;

    public ChemicalStructureCategory(IGuiHelper guiHelper) {
        super("chemical_structure_location",
                "chemical_structure_location.name",
                guiHelper.createBlankDrawable(176, 166),
                guiHelper);

        this.guiHelper = guiHelper;
        this.icon = guiHelper.createDrawableIngredient(OreDictUnifier.get(OrePrefix.ingot, Materials.Copper));
        this.slot = guiHelper
                .drawableBuilder(GuiTextures.SLOT.imageLocation, 0, 0, SLOT_DIM, SLOT_DIM)
                .setTextureSize(SLOT_DIM, SLOT_DIM)
                .build();
    }

    @NotNull
    @Override
    public IRecipeWrapper getRecipeWrapper(@NotNull ChemicalStructureInfo recipeWrapper) {
        return recipeWrapper;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayout recipeLayout, @NotNull ChemicalStructureInfo recipeWrapper,
                          @NotNull IIngredients ingredients) {
        this.info = recipeWrapper;

        int xPosition = ingredientsXoffset;
        int slotIndex = 0;

        if (recipeWrapper.isHasDust()) {
            IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
            itemStackGroup.init(slotIndex, true, xPosition, ingredientsYoffset);
            itemStackGroup.set(slotIndex, recipeWrapper.getDusts());
            xPosition += SLOT_DIM;
            slotIndex++;
        }

        if (recipeWrapper.isHasFluid()) {
            IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
            fluidStackGroup.init(slotIndex, true, xPosition, ingredientsYoffset);
            fluidStackGroup.set(slotIndex, recipeWrapper.getFluidStack());
        }
    }

    @Override
    public void drawExtras(@NotNull Minecraft minecraft) {
        int xPosition = ingredientsXoffset;
        int yOffset = ingredientsYoffset - 1;

        if (info.isHasDust()) {
            slot.draw(minecraft, xPosition, yOffset);
            xPosition += SLOT_DIM - 1;
        }

        if (info.isHasFluid()) {
            slot.draw(minecraft, xPosition, yOffset);
        }

        // To avoid auto-supersizing textures
        int width = info.getChemicalStructureWidth() / 2;
        int height = info.getChemicalStructureHeight() / 2;

        this.chemicalStructure = guiHelper
                .drawableBuilder(ChemicalStructureRenderUtils.getMoleculeTexture(info.getMaterial()).imageLocation,
                        0, 0, width, height)
                .setTextureSize(width, height)
                .build();

        chemicalStructure.draw(minecraft, 20, yOffset + SLOT_DIM * 2 + 1);
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

    @NotNull
    @Override
    public String getModName() {
        return TkTech.MODID;
    }
}
