package tkcy.tktech.api.machines;

import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import gregtech.api.unification.ore.OrePrefix;

import tkcy.tktech.api.utils.StringsHelper;

public interface IUnificationToolMachine {

    /**
     * Returns the orePrefix of all the parts that can be made via this metaTileEntity recipes.
     */
    @NotNull
    List<OrePrefix> getPartsOrePrefixes();

    default String addPartsOrePrefixInformation() {
        return getPartsOrePrefixes().stream()
                .map(OrePrefix::name)
                .map(StringsHelper::convertCamelToTitleCase)
                .collect(Collectors.joining("s, ")) + "s.";
    }
}
