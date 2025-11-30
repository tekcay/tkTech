package tkcy.tktech.api.unification.properties;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.github.bsideup.jabel.Desugar;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.MaterialProperties;
import gregtech.api.unification.material.properties.PropertyKey;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.tktech.api.unification.properties.PhysicalProperties")
@ZenRegister
@Desugar
public record PhysicalProperties(int bp, int bpPressure, int mp, int flameTemperature, int thermalConductivity,
                                 int autoIgnitionTemperature, boolean isPyrophoric, boolean isHygroscopic,
                                 boolean oxidizes)
        implements ITooltipMaterialProperty<PhysicalProperties> {

    @ZenMethod
    public static void addPhysicalMaterialProperty(@NotNull Material material,
                                                   @NotNull PhysicalPropertiesBuilder builder) {
        material.getProperties().setProperty(
                TkTechMaterialPropertyKeys.PHYSICAL_PROPERTIES,
                builder.build());
    }

    public static List<String> createPhysicalPropertiesTooltip(@Nullable Material material) {
        List<String> tooltips = new ArrayList<>();

        if (material.hasProperty(TkTechMaterialPropertyKeys.PHYSICAL_PROPERTIES)) {
            PhysicalProperties physicalProperties = material
                    .getProperty(TkTechMaterialPropertyKeys.PHYSICAL_PROPERTIES);

            if (physicalProperties.oxidizes()) {
                tooltips.add(I18n.format("tktech.physical_properties.oxidizes"));
            }
            if (physicalProperties.isHygroscopic()) {
                tooltips.add(I18n.format("tktech.physical_properties.hygroscopic"));
            }
            if (physicalProperties.isPyrophoric()) {
                tooltips.add(I18n.format("tktech.physical_properties.pyrophoric"));
            }
            if (physicalProperties.mp() > 0) {
                tooltips.add(I18n.format("tktech.physical_properties.mp", physicalProperties.mp()));
            }
            if (physicalProperties.bp() > 0) {
                if (physicalProperties.bpPressure() > 0) {
                    tooltips.add(I18n.format("tktech.physical_properties.bp_pressure", physicalProperties.bp(),
                            physicalProperties.bpPressure()));
                } else tooltips.add(I18n.format("tktech.physical_properties.bp", physicalProperties.bp()));
            }
            if (physicalProperties.flameTemperature() > 0) {
                tooltips.add(I18n.format("tktech.physical_properties.flame_temperature",
                        physicalProperties.flameTemperature()));
            }
            if (physicalProperties.autoIgnitionTemperature() > 0) {
                tooltips.add(I18n.format("tktech.physical_properties.auto_ignition_temperature",
                        physicalProperties.autoIgnitionTemperature()));
            }
            if (physicalProperties.thermalConductivity() > 0) {
                tooltips.add(I18n.format("tktech.physical_properties.thermal_conductivity",
                        physicalProperties.thermalConductivity()));
            }
        }
        return tooltips;
    }

    @Override
    public PropertyKey<PhysicalProperties> getPropertyKey() {
        return TkTechMaterialPropertyKeys.PHYSICAL_PROPERTIES;
    }

    @Override
    public void verifyProperty(MaterialProperties properties) {
        properties.ensureSet(TkTechMaterialPropertyKeys.PHYSICAL_PROPERTIES, true);
    }

    @Override
    public List<String> createTooltip(@NotNull Material material) {
        return createPhysicalPropertiesTooltip(material);
    }
}
