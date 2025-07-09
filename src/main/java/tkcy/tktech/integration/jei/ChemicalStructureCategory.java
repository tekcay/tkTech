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
import tkcy.tktech.api.render.ChemicalStructureRenderUtils;
import tkcy.tktech.api.render.IChemicalStructureCategory;
import tkcy.tktech.api.render.TkTechTextures;
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

    private int chemStructureYOffset() {
        return slotYOffset() + SLOT_DIM + ySpacing();
    }

    /**
     * Drawn in the recipeWrapper, see {@link ChemicalStructureInfo#drawInfo(Minecraft, int, int, int, int)}.
     */
    private int materialNameHeight() {
        return GuiUtils.FONT_HEIGHT;
    }

    @Override
    public void drawExtras(@NotNull Minecraft minecraft) {
        TkTechTextures.REACTION_BACKGROUND.draw(0, 0, 176, 166);

        info.setGuiWidth(getBackgroundWidth());
        info.setYMargin(yMargin());

        int slotIndex = 0;

        if (info.isHasDust()) {
            slot.draw(minecraft, slotXOffset(slotIndex), slotYOffset());
            slotIndex++;
        }

        if (info.isHasFluid()) {
            slot.draw(minecraft, slotXOffset(slotIndex), slotYOffset());
        }

        chemicalStructure = ChemicalStructureRenderUtils.buildChemStructureDrawable(guiHelper, info.getMaterial());
        chemicalStructure.draw(minecraft, getCenterXOffset(getBackgroundWidth(), chemicalStructure.getWidth()),
                chemStructureYOffset());
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
    public int getBackgroundHeight() {
        return GuiUtils.STANDARD_JEI_UI_HEIGHT;
    }

    @Override
    public int getBackgroundWidth() {
        return GuiUtils.STANDARD_JEI_UI_WIDTH;
    }
}
