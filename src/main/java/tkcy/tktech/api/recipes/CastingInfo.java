package tkcy.tktech.api.recipes;

import static gregtech.api.unification.material.Materials.*;
import static tkcy.tktech.api.unification.materials.TKCYSAMaterials.Ceramic;
import static tkcy.tktech.api.utils.MaterialHelper.getMaterialFluidTemperature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.Tuple;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import lombok.Getter;

@Getter
public class CastingInfo {

    private final Material moldMaterial;
    private final int maxHandledTemperature;

    public static final BiConsumer<CastingInfo, List<String>> addToTooltip = (castingInfo, tooltip) -> tooltip
            .add(I18n.format("tkcysa.casting.tooltip",
                    castingInfo.moldMaterial.getLocalizedName(),
                    castingInfo.maxHandledTemperature));

    public CastingInfo(Material material) {
        this.moldMaterial = material;
        this.maxHandledTemperature = (int) (0.8F * material.getFluid().getTemperature());
    }

    public static int getFluidMaterialGapMultiplier(Material moldMaterial, Material material) {
        int multiplier = (getMaterialFluidTemperature.apply(moldMaterial) -
                getMaterialFluidTemperature.apply(material)) / (GTValues.L * 5);
        return Math.max(1, multiplier);
    }

    public static final List<Material> MOLD_MATERIALS = new ArrayList<>();
    public static final List<CastingInfo> CASTING_INFOS = new ArrayList<>();
    public static final List<OrePrefix> MOLD_ORE_PREFIXES = new ArrayList<>();

    private static final Consumer<Material> addToCastingInfos = material -> CASTING_INFOS
            .add(new CastingInfo(material));

    public boolean canHandleFluidMaterial(Material fluidMaterial) {
        return getMaterialFluidTemperature.apply(fluidMaterial) <= this.getMaxHandledTemperature();
    }

    static {
        MOLD_MATERIALS.add(Steel);
        MOLD_MATERIALS.add(Ceramic);
        MOLD_MATERIALS.add(TungstenCarbide);
        MOLD_MATERIALS.add(Carbon);

        MOLD_MATERIALS.forEach(addToCastingInfos);
    }

    static {
        MOLD_ORE_PREFIXES.add(OrePrefix.ingot);
        MOLD_ORE_PREFIXES.add(OrePrefix.plate);
        MOLD_ORE_PREFIXES.add(OrePrefix.stick);
        MOLD_ORE_PREFIXES.add(OrePrefix.stickLong);
        MOLD_ORE_PREFIXES.add(OrePrefix.gear);
        MOLD_ORE_PREFIXES.add(OrePrefix.gearSmall);
        MOLD_ORE_PREFIXES.add(OrePrefix.bolt);
        MOLD_ORE_PREFIXES.add(OrePrefix.ring);
    }

    public static final Tuple<List<Material>, List<OrePrefix>> MATERIALS_OREPREFIXES_MOLDS = new Tuple<>(MOLD_MATERIALS,
            MOLD_ORE_PREFIXES);
}
