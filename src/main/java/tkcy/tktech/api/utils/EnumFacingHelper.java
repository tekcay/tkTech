package tkcy.tktech.api.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import org.jetbrains.annotations.Nullable;

import lombok.experimental.UtilityClass;
import tkcy.tktech.modules.RecipePropertiesKey;

@UtilityClass
public class EnumFacingHelper {

    public static void serialize(EnumFacing enumFacing, NBTTagCompound compound) {
        compound.setString(RecipePropertiesKey.TOOL_CLICK_FACING_KEY, enumFacing.getName());
    }

    @Nullable
    public static EnumFacing deserialize(NBTTagCompound compound) {
        String enumFacingName = compound.getString(RecipePropertiesKey.TOOL_CLICK_FACING_KEY);
        return EnumFacing.byName(enumFacingName);
    }
}
