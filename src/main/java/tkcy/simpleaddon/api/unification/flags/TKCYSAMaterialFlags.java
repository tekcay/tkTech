package tkcy.simpleaddon.api.unification.flags;

import static gregtech.api.unification.material.Materials.EXT2_METAL;
import static gregtech.api.unification.material.info.MaterialFlags.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.properties.PropertyKey;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class TKCYSAMaterialFlags {

    /**
     * Use to disable alloy blast recipes from generating
     */
    public static final MaterialFlag NO_ALLOY_BLAST_RECIPES = new MaterialFlag.Builder("no_alloy_blast_recipes")
            .requireProps(PropertyKey.BLAST, PropertyKey.FLUID)
            .build();

    /**
     * Use to disable everything related to alloy blasting
     */
    public static final MaterialFlag DISABLE_ALLOY_PROPERTY = new MaterialFlag.Builder("disable_alloy_property")
            .requireProps(PropertyKey.BLAST, PropertyKey.FLUID)
            .requireFlags(NO_ALLOY_BLAST_RECIPES)
            .build();

    public static final MaterialFlag GENERATE_ELECTRODES = new MaterialFlag.Builder("generate_electrodes")
            .requireFlags(GENERATE_LONG_ROD)
            .build();
    public static final MaterialFlag GENERATE_CASING = new MaterialFlag.Builder("generate_casing")
            .requireFlags(GENERATE_PLATE)
            .build();
    public static final MaterialFlag GENERATE_WALL = new MaterialFlag.Builder("generate_wall")
            .requireFlags(GENERATE_PLATE)
            .build();
    public static final MaterialFlag GENERATE_COIL = new MaterialFlag.Builder("generate_coil")
            .requireFlags(GENERATE_PLATE)
            .build();
    public static final MaterialFlag ALLOY = new MaterialFlag.Builder("alloy")
            .build();
    public static final MaterialFlag GENERATE_SOLDERING_IRON_TIP = new MaterialFlag.Builder(
            "generate_soldering_iron_tip")
                    .requireFlags(GENERATE_BOLT_SCREW, GENERATE_ROD)
                    .build();
    public static final MaterialFlag GENERATE_CURVED_PLATE = new MaterialFlag.Builder(
            "generate_curved_plate")
                    .requireFlags(GENERATE_BOLT_SCREW, GENERATE_ROD)
                    .build();
    public static final MaterialFlag IS_POLYMER = new MaterialFlag.Builder(
            "is_polymer")
                    .build();

    public static final Predicate<Material> isAlloy = material -> material.hasFlag(ALLOY);

    public static final List<MaterialFlag> GENERATE_ALL = new ArrayList<>();
    public static final List<MaterialFlag> GENERATE_ALL_NO_UNIF = new ArrayList<>();

    static {

        GENERATE_ALL.addAll(EXT2_METAL);
        GENERATE_ALL.addAll(Arrays.asList(GENERATE_ROTOR, GENERATE_SMALL_GEAR, GENERATE_SPRING,
                GENERATE_SPRING_SMALL, GENERATE_FRAME, GENERATE_GEAR, GENERATE_DOUBLE_PLATE, GENERATE_DENSE,
                GENERATE_FINE_WIRE, GENERATE_FOIL, GENERATE_CURVED_PLATE, GENERATE_CASING, GENERATE_WALL, GENERATE_COIL,
                DISABLE_DECOMPOSITION,
                NO_SMELTING));

        GENERATE_ALL_NO_UNIF.addAll(GENERATE_ALL);
        GENERATE_ALL_NO_UNIF.add(NO_UNIFICATION);
        // GENERATE_ALL_NO_UNIF.addAll(Arrays.asList(NO_UNIFICATION, NO_SMELTING, NO_SMASHING, NO_WORKING,
        // NO_SMASHING));
    }
}
