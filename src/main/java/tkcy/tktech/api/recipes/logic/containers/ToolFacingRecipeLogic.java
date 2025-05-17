package tkcy.tktech.api.recipes.logic.containers;

import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.recipes.Recipe;

import lombok.Getter;
import lombok.Setter;
import tkcy.tktech.api.recipes.logic.IRecipePropertiesValueMap;
import tkcy.tktech.api.recipes.properties.IRecipePropertyHelper;
import tkcy.tktech.api.recipes.properties.ToolFacingRecipeProperty;
import tkcy.tktech.api.utils.EnumFacingHelper;

@Setter
@Getter
public class ToolFacingRecipeLogic extends ToolLogic implements IRecipeLogicContainer, IRecipePropertiesValueMap {

    private EnumFacing currentToolClickFacing;
    private EnumFacing recipeToolClickFacing;

    public ToolFacingRecipeLogic(AbstractRecipeLogic abstractRecipeLogic) {
        super(abstractRecipeLogic);
    }

    protected void setToolClickFacingFromRecipe(Recipe recipe) {
        setRecipeToolClickFacing(ToolFacingRecipeProperty.getInstance().getValueFromRecipe(recipe));
    }

    @Override
    public boolean canRecipeLogicProgress() {
        return super.canRecipeLogicProgress() && getCurrentToolClickFacing() == getRecipeToolClickFacing();
    }

    @Override
    public boolean prepareRecipe(@NotNull Recipe recipe, IItemHandler inputInventory) {
        setToolClickFacingFromRecipe(recipe);
        return super.prepareRecipe(recipe, inputInventory);
    }

    @Override
    public void resetLogic() {
        super.resetLogic();
        setRecipeToolClickFacing(null);
        setCurrentToolClickFacing(null);
    }

    @Override
    public void serializeRecipeLogic(@NotNull NBTTagCompound compound) {
        super.serializeRecipeLogic(compound);
        if (isNotWorking()) return;
        EnumFacingHelper.serialize(getRecipeToolClickFacing(), compound);
    }

    @Override
    public void deserializeRecipeLogic(@NotNull NBTTagCompound compound) {
        super.deserializeRecipeLogic(compound);
        setRecipeToolClickFacing(EnumFacingHelper.deserialize(compound));
    }

    @Override
    public void updateRecipeParameters(@NotNull Map<IRecipePropertyHelper<?>, Object> recipeParameters) {
        super.updateRecipeParameters(recipeParameters);
        recipeParameters.put(ToolFacingRecipeProperty.getInstance(), getCurrentToolClickFacing());
    }
}
