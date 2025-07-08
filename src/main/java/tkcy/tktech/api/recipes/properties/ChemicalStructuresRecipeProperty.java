package tkcy.tktech.api.recipes.properties;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.recipes.properties.RecipeProperty;
import gregtech.api.unification.material.Material;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tkcy.tktech.api.recipes.recipemaps.IChemStructureToMaterials;
import tkcy.tktech.api.unification.properties.TkTechMaterialPropertyKeys;
import tkcy.tktech.api.utils.MaterialHelper;
import tkcy.tktech.modules.RecipePropertiesKey;

public class ChemicalStructuresRecipeProperty extends RecipeProperty<ChemicalStructuresRecipeProperty.Container>
                                              implements
                                              IRecipePropertyHelper<ChemicalStructuresRecipeProperty.Container> {

    private static final String outputMaterialsNbtKey = "inputMaterials";
    private static final String inputMaterialsNbtKey = "outputMaterials";

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

    @Getter
    @AllArgsConstructor
    public static class Container implements IChemStructureToMaterials {

        private Set<Material> inputMaterialsChemStructure;
        private Set<Material> outputMaterialsChemStructure;
    }

    @Override
    public @NotNull NBTBase serialize(@NotNull Object value) {
        Container recipeProperty = castValue(value);
        NBTTagCompound nbtTagCompound = new NBTTagCompound();

        NBTTagList inputNbtTagList = MaterialHelper.serializeMaterials(recipeProperty.getInputMaterialsChemStructure());
        nbtTagCompound.setTag(inputMaterialsNbtKey, inputNbtTagList);

        NBTTagList outputNbtTagList = MaterialHelper
                .serializeMaterials(recipeProperty.getOutputMaterialsChemStructure());
        nbtTagCompound.setTag(outputMaterialsNbtKey, outputNbtTagList);

        return nbtTagCompound;
    }

    @Override
    public @NotNull Object deserialize(@NotNull NBTBase nbt) {
        NBTTagCompound nbtTagCompound = (NBTTagCompound) nbt;

        NBTTagList inputMaterialsTag = nbtTagCompound.getTagList(inputMaterialsNbtKey, Constants.NBT.TAG_LIST);
        Set<Material> inputMaterials = MaterialHelper.deserializeMaterialsToSet(inputMaterialsTag);

        NBTTagList outputMaterialsTag = nbtTagCompound.getTagList(outputMaterialsNbtKey, Constants.NBT.TAG_LIST);
        Set<Material> outputMaterials = MaterialHelper.deserializeMaterialsToSet(outputMaterialsTag);

        return new Container(inputMaterials, outputMaterials);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {}

    @Override
    public Predicate<Container> testSuppliedValue() {
        return container -> testMaterials(container.getInputMaterialsChemStructure()) &&
                testMaterials(container.getOutputMaterialsChemStructure());
    }

    private boolean testMaterials(@Nullable Set<Material> materials) {
        if (materials == null) return false;
        for (Material material : materials) {
            if (material == null || !material.hasProperty(TkTechMaterialPropertyKeys.CHEMICAL_STRUCTURE)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Container getDefaultValue() {
        return new Container(new HashSet<>(), new HashSet<>());
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
