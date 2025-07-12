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
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import tkcy.tktech.TkTech;
import tkcy.tktech.api.render.ChemicalReactionRenderUtils;
import tkcy.tktech.api.render.ChemicalStructureRenderUtils;
import tkcy.tktech.api.render.IChemicalStructureCategory;
import tkcy.tktech.api.utils.GuiUtils;

public class ChemicalStructureCategory extends BasicRecipeCategory<ChemicalStructureInfo, ChemicalStructureInfo>
                                       implements IChemicalStructureCategory {

    private final IGuiHelper guiHelper;

    protected final IDrawable slot;
    protected final IDrawable icon;
    protected IDrawable chemicalStructure;

    private ChemicalStructureInfo info;

    public ChemicalStructureCategory(IGuiHelper guiHelper) {
        super("chemical_structure_location",
                "chemical_structure_location.name",
                GuiUtils.drawConfigHeightDependentJEIGui(guiHelper, 0.8D),
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
        int slotIndex = 0;

        if (recipeWrapper.isHasDust()) {
            IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
            itemStackGroup.init(slotIndex, true, slotXOffset(slotIndex), slotYOffset());
            itemStackGroup.set(slotIndex, recipeWrapper.getDusts());
            slotIndex++;
        }

        if (recipeWrapper.isHasFluid()) {
            IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
            fluidStackGroup.init(slotIndex, true, slotXOffset(slotIndex), slotYOffset());
            fluidStackGroup.set(slotIndex, recipeWrapper.getFluidStack());
        }
    }

    private int slotYOffset() {
        return yMargin() + materialNameHeight() + ySpacing();
    }

    private int slotXOffset(int slotIndex) {
        return xMargin() + slotIndex * (SLOT_DIM + xSpacing());
    }

    /**
     * Drawn in the recipeWrapper, see {@link ChemicalStructureInfo#drawInfo(Minecraft, int, int, int, int)}.
     */
    private int materialNameHeight() {
        return GuiUtils.FONT_HEIGHT;
    }

    @Override
    public void drawExtras(@NotNull Minecraft minecraft) {
        if (info == null) return;

        chemicalStructure = ChemicalStructureRenderUtils.getChemStructureIDrawable(guiHelper, info.getMaterial(), 0.5D);

        int xOffset = getReactionBackgroundXOffset();

        drawReactionBackground();

        info.setGuiWidth(background.getWidth());
        info.setYMargin(yMargin());

        int slotIndex = 0;

        if (info.isHasDust()) {
            slot.draw(minecraft, slotXOffset(slotIndex), slotYOffset());
            slotIndex++;
        }

        if (info.isHasFluid()) {
            slot.draw(minecraft, slotXOffset(slotIndex), slotYOffset());
        }

        xOffset += xMargin();
        chemicalStructure.draw(
                minecraft,
                // getCenterXOffset(getBackgroundWidth(), chemicalStructure.getWidth()),
                xOffset,
                getReactionBackgroundYOffset() + ySpacing());
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

    @Override
    public int getReactionBackgroundHeight() {
        return ySpacing() + chemicalStructure.getHeight() + ySpacing();
    }

    @Override
    public int getReactionBackgroundWidth() {
        return xMargin() + chemicalStructure.getWidth() + xMargin();
    }

    @Override
    public int getReactionBackgroundXOffset() {
        return ChemicalReactionRenderUtils.getReactionXOffset(getReactionBackgroundWidth(), background.getWidth());
    }

    @Override
    public int getReactionBackgroundYOffset() {
        return slotYOffset() + SLOT_DIM + ySpacing();
    }
}
