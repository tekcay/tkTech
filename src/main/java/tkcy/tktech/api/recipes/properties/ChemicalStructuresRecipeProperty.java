package tkcy.tktech.api.recipes.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.properties.RecipeProperty;
import gregtech.api.unification.material.Material;

import tkcy.tktech.api.unification.properties.TkTechMaterialPropertyKeys;
import tkcy.tktech.api.utils.MaterialHelper;
import tkcy.tktech.modules.RecipePropertiesKey;

public class ChemicalStructuresRecipeProperty extends RecipeProperty<ChemicalStructuresRecipeProperty.Container>
                                              implements
                                              IRecipePropertyHelper<ChemicalStructuresRecipeProperty.Container> {

    private static final String isInputNbtKey = "isInput";
    private static final String materialsNbtKey = "materials";

    public static final String KEY = RecipePropertiesKey.CHEMICAL_STRUCTURE_KEY;
    private static ChemicalStructuresRecipeProperty INSTANCE;

    @NotNull
    public static ChemicalStructuresRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChemicalStructuresRecipeProperty();
        }
        return INSTANCE;
    }

    private ChemicalStructuresRecipeProperty() {
        super(KEY, Container.class);
    }

    public record Container(boolean isInput, List<Material> materials) {}

    @Override
    public @NotNull NBTBase serialize(@NotNull Object value) {
        Container recipeProperty = castValue(value);
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setBoolean(isInputNbtKey, recipeProperty.isInput);
        NBTTagList nbtTagList = MaterialHelper.serializeMaterials(recipeProperty.materials);
        nbtTagCompound.setTag(materialsNbtKey, nbtTagList);
        return nbtTagCompound;
    }

    @Override
    public @NotNull Object deserialize(@NotNull NBTBase nbt) {
        NBTTagCompound nbtTagCompound = (NBTTagCompound) nbt;
        boolean isInput = nbtTagCompound.getBoolean(isInputNbtKey);

        NBTTagList materialsTag = nbtTagCompound.getTagList(materialsNbtKey, Constants.NBT.TAG_LIST);
        List<Material> materials = MaterialHelper.deserializeMaterials(materialsTag);

        return new Container(isInput, materials);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {}

    @Override
    public Predicate<Container> testSuppliedValue() {
        return container -> {
            if (container.materials.isEmpty()) return false;

            for (Material material : container.materials) {
                if (material == null || !material.hasProperty(TkTechMaterialPropertyKeys.CHEMICAL_STRUCTURE)) {
                    return false;
                }
            }
            return true;
        };
    }

    @Override
    public Container getDefaultValue() {
        return new Container(true, new ArrayList<>());
    }

    @Override
    public String getErrorMessage() {
        return "Can not contain an empty list of materials or materials that do not have CHEMICAL_STRUCTURE materialProperty!";
    }

    @Override
    public RecipeProperty<Container> getProperty() {
        return this;
    }
}
