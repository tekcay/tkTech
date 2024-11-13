package tkcy.simpleaddon.modules;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.util.function.TriConsumer;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NBTHelpers {

    public static NBTTagList getSerializedItemsTag(List<ItemStack> itemOutputs) {
        NBTTagList itemOutputsList = new NBTTagList();
        for (ItemStack itemOutput : itemOutputs) {
            itemOutputsList.appendTag(itemOutput.writeToNBT(new NBTTagCompound()));
        }
        return itemOutputsList;
    }

    public static NBTTagList getSerializedFluidsTag(List<FluidStack> fluidOutputs) {
        NBTTagList fluidOutputsList = new NBTTagList();
        for (FluidStack fluidOutput : fluidOutputs) {
            fluidOutputsList.appendTag(fluidOutput.writeToNBT(new NBTTagCompound()));
        }
        return fluidOutputsList;
    }

    public static TriConsumer<NBTTagCompound, List<ItemStack>, NBTLabel> itemStacksSerializer = (nbtTagCompound,
                                                                                                 itemStacks,
                                                                                                 nbtLabel) -> nbtTagCompound
                                                                                                         .setTag(nbtLabel
                                                                                                                 .name(),
                                                                                                                 getSerializedItemsTag(
                                                                                                                         itemStacks));

    public static TriConsumer<NBTTagCompound, List<FluidStack>, NBTLabel> fluidStacksSerializer = (nbtTagCompound,
                                                                                                   fluidStacks,
                                                                                                   nbtLabel) -> nbtTagCompound
                                                                                                           .setTag(nbtLabel
                                                                                                                   .name(),
                                                                                                                   getSerializedFluidsTag(
                                                                                                                           fluidStacks));

    public static NonNullList<ItemStack> getDeserializedItemStacks(@NotNull NBTTagCompound compound,
                                                                   @NotNull NBTLabel nbtLabel) {
        NBTTagList itemOutputsList = compound.getTagList(nbtLabel.name(), Constants.NBT.TAG_COMPOUND);
        NonNullList<ItemStack> itemOutputs = NonNullList.create();
        for (int i = 0; i < itemOutputsList.tagCount(); i++) {
            itemOutputs.add(new ItemStack(itemOutputsList.getCompoundTagAt(i)));
        }
        return itemOutputs;
    }

    public static List<FluidStack> getDeserializedFluidStacks(@NotNull NBTTagCompound compound,
                                                              @NotNull NBTLabel nbtLabel) {
        NBTTagList fluidOutputsList = compound.getTagList(nbtLabel.name(), Constants.NBT.TAG_COMPOUND);
        List<FluidStack> fluidOutputs = new ArrayList<>();
        for (int i = 0; i < fluidOutputsList.tagCount(); i++) {
            fluidOutputs.add(FluidStack.loadFluidStackFromNBT(fluidOutputsList.getCompoundTagAt(i)));
        }
        return fluidOutputs;
    }
}
