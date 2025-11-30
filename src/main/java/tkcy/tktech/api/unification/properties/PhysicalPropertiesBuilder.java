package tkcy.tktech.api.unification.properties;

import com.google.common.base.Preconditions;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Extracted from {@link PhysicalProperties} so it won't conflict in groovy
 */
@ZenClass("mods.tktech.api.unification.properties.PhysicalPropertiesBuilder")
@ZenRegister
public class PhysicalPropertiesBuilder {

    private int thermalConductivity = 0;
    private int bp = 0;
    private int mp = 0;
    private int bpPressure = 0;
    private int flameTemperature = 0;
    private int autoIgnitionTemperature = 0;
    private boolean isPyrophoric = false;
    private boolean isHygroscopic = false;
    private boolean oxidizes = false;

    @ZenMethod
    public PhysicalPropertiesBuilder thermalConductivity(int thermalConductivity) {
        this.thermalConductivity = thermalConductivity;
        return this;
    }

    @ZenMethod
    public PhysicalPropertiesBuilder bp(int boilingPoint) {
        Preconditions.checkArgument(boilingPoint > 0, "Boiling point must be > 0");
        this.bp = boilingPoint;
        return this;
    }

    @ZenMethod
    public static PhysicalPropertiesBuilder initBuilder() {
        return new PhysicalPropertiesBuilder();
    }

    @ZenMethod
    public PhysicalPropertiesBuilder bp(int boilingPoint, int boilingPressure) {
        Preconditions.checkArgument(boilingPoint > 0, "Boiling point must be > 0");
        Preconditions.checkArgument(boilingPressure > 0, "Boiling pressure must be > 0");
        this.bp = boilingPoint;
        this.bpPressure = boilingPressure;
        return this;
    }

    @ZenMethod
    public PhysicalPropertiesBuilder mp(int meltingPoint) {
        Preconditions.checkArgument(meltingPoint > 0, "Melting point must be > 0");
        this.mp = meltingPoint;
        return this;
    }

    @ZenMethod
    public PhysicalPropertiesBuilder flameTemperature(int flameTemperature) {
        Preconditions.checkArgument(flameTemperature > 0, "flameTemperature must be > 0");
        this.flameTemperature = flameTemperature;
        return this;
    }

    @ZenMethod
    public PhysicalPropertiesBuilder autoIgnitionTemperature(int autoIgnitionTemperature) {
        Preconditions.checkArgument(autoIgnitionTemperature > 0, "autoIgnitionTemperature must be > 0");
        this.autoIgnitionTemperature = autoIgnitionTemperature;
        return this;
    }

    @ZenMethod
    public PhysicalPropertiesBuilder pyrophoric() {
        this.isPyrophoric = true;
        return this;
    }

    @ZenMethod
    public PhysicalPropertiesBuilder hygroscopic() {
        this.isHygroscopic = true;
        return this;
    }

    @ZenMethod
    public PhysicalPropertiesBuilder oxidizes() {
        this.oxidizes = true;
        return this;
    }

    public PhysicalProperties build() {
        return new PhysicalProperties(bp, bpPressure, mp, flameTemperature, thermalConductivity,
                autoIgnitionTemperature, isPyrophoric, isHygroscopic, oxidizes);
    }
}
