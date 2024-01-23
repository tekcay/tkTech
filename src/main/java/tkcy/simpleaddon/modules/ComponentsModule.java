package tkcy.simpleaddon.modules;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.items.MetaItems.*;
import static tkcy.simpleaddon.api.unification.ore.TKCYSAOrePrefix.*;
import static tkcy.simpleaddon.api.utils.ListHelper.buildList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

public final class ComponentsModule {

    public static List<Material> getIvAcceptedRubberMaterials() {
        return buildList(Polybenzimidazole);
    }

    public static List<Material> getEvAcceptedRubberMaterials() {
        return buildList(getIvAcceptedRubberMaterials());
    }

    public static List<Material> getHvAcceptedRubberMaterials() {
        return buildList(getEvAcceptedRubberMaterials(), Polycaprolactam);
    }

    public static List<Material> getMvAcceptedRubberMaterials() {
        return buildList(getHvAcceptedRubberMaterials(), Polydimethylsiloxane);
    }

    public static List<Material> getLvAcceptedRubberMaterials() {
        return buildList(getMvAcceptedRubberMaterials(), Rubber);
    }

    public static List<ItemStack> getMotors() {
        List<MetaItem<?>.MetaValueItem> motorsMeta = new ArrayList<>();
        motorsMeta.add(ELECTRIC_MOTOR_LV);
        motorsMeta.add(ELECTRIC_MOTOR_MV);
        motorsMeta.add(ELECTRIC_MOTOR_HV);
        motorsMeta.add(ELECTRIC_MOTOR_EV);
        motorsMeta.add(ELECTRIC_MOTOR_IV);
        return motorsMeta.stream()
                .map(MetaItem.MetaValueItem::getStackForm)
                .collect(Collectors.toList());
    }

    public static List<ItemStack> getPumps() {
        List<MetaItem<?>.MetaValueItem> pumpsMeta = new ArrayList<>();
        pumpsMeta.add(ELECTRIC_PUMP_LV);
        pumpsMeta.add(ELECTRIC_PUMP_MV);
        pumpsMeta.add(ELECTRIC_PUMP_HV);
        pumpsMeta.add(ELECTRIC_PUMP_EV);
        pumpsMeta.add(ELECTRIC_PUMP_IV);
        return pumpsMeta.stream()
                .map(MetaItem.MetaValueItem::getStackForm)
                .collect(Collectors.toList());
    }

    public static List<ItemStack> getConveyorModules() {
        List<MetaItem<?>.MetaValueItem> conveyorModulesMeta = new ArrayList<>();
        conveyorModulesMeta.add(CONVEYOR_MODULE_LV);
        conveyorModulesMeta.add(CONVEYOR_MODULE_MV);
        conveyorModulesMeta.add(CONVEYOR_MODULE_HV);
        conveyorModulesMeta.add(CONVEYOR_MODULE_EV);
        conveyorModulesMeta.add(CONVEYOR_MODULE_IV);
        return conveyorModulesMeta.stream()
                .map(MetaItem.MetaValueItem::getStackForm)
                .collect(Collectors.toList());
    }

    public static List<ItemStack> getPistons() {
        List<MetaItem<?>.MetaValueItem> pistonsMeta = new ArrayList<>();
        pistonsMeta.add(ELECTRIC_PISTON_LV);
        pistonsMeta.add(ELECTRIC_PISTON_MV);
        pistonsMeta.add(ELECTRIC_PISTON_HV);
        pistonsMeta.add(ELECTRIC_PISTON_EV);
        pistonsMeta.add(ELECTRIC_PISTON_IV);
        return pistonsMeta.stream()
                .map(MetaItem.MetaValueItem::getStackForm)
                .collect(Collectors.toList());
    }

    public static List<ItemStack> getRobotArms() {
        List<MetaItem<?>.MetaValueItem> robotArmsMeta = new ArrayList<>();
        robotArmsMeta.add(ROBOT_ARM_LV);
        robotArmsMeta.add(ROBOT_ARM_MV);
        robotArmsMeta.add(ROBOT_ARM_HV);
        robotArmsMeta.add(ROBOT_ARM_EV);
        robotArmsMeta.add(ROBOT_ARM_IV);
        return robotArmsMeta.stream()
                .map(MetaItem.MetaValueItem::getStackForm)
                .collect(Collectors.toList());
    }

    public static List<ItemStack> getSensors() {
        List<MetaItem<?>.MetaValueItem> sensorsMeta = new ArrayList<>();
        sensorsMeta.add(SENSOR_LV);
        sensorsMeta.add(SENSOR_MV);
        sensorsMeta.add(SENSOR_HV);
        sensorsMeta.add(SENSOR_EV);
        sensorsMeta.add(SENSOR_IV);
        return sensorsMeta.stream()
                .map(MetaItem.MetaValueItem::getStackForm)
                .collect(Collectors.toList());
    }

    public static List<ItemStack> getEmitters() {
        List<MetaItem<?>.MetaValueItem> emittersMeta = new ArrayList<>();
        emittersMeta.add(EMITTER_LV);
        emittersMeta.add(EMITTER_MV);
        emittersMeta.add(EMITTER_HV);
        emittersMeta.add(EMITTER_EV);
        emittersMeta.add(EMITTER_IV);
        return emittersMeta.stream()
                .map(MetaItem.MetaValueItem::getStackForm)
                .collect(Collectors.toList());
    }

    public static List<ItemStack> getFluidRegulators() {
        List<MetaItem<?>.MetaValueItem> fluidRegulatorsMeta = new ArrayList<>();
        fluidRegulatorsMeta.add(FLUID_REGULATOR_LV);
        fluidRegulatorsMeta.add(FLUID_REGULATOR_MV);
        fluidRegulatorsMeta.add(FLUID_REGULATOR_HV);
        fluidRegulatorsMeta.add(FLUID_REGULATOR_EV);
        fluidRegulatorsMeta.add(FLUID_REGULATOR_IV);
        return fluidRegulatorsMeta.stream()
                .map(MetaItem.MetaValueItem::getStackForm)
                .collect(Collectors.toList());
    }

    public static List<ItemStack> getFieldGenerators() {
        List<MetaItem<?>.MetaValueItem> fieldGeneratorsMeta = new ArrayList<>();
        fieldGeneratorsMeta.add(FIELD_GENERATOR_LV);
        fieldGeneratorsMeta.add(FIELD_GENERATOR_MV);
        fieldGeneratorsMeta.add(FIELD_GENERATOR_HV);
        fieldGeneratorsMeta.add(FIELD_GENERATOR_EV);
        fieldGeneratorsMeta.add(FIELD_GENERATOR_IV);
        return fieldGeneratorsMeta.stream()
                .map(MetaItem.MetaValueItem::getStackForm)
                .collect(Collectors.toList());
    }

    public static void initLvComponentsOre() {
        FLUID_REGULATOR_LV.setUnificationData(lvComponents, MarkerMaterials.Empty);
        FLUID_REGULATOR_LV.setUnificationData(lvComponents, MarkerMaterials.Empty);
        ELECTRIC_MOTOR_LV.setUnificationData(lvComponents, MarkerMaterials.Empty);
        ELECTRIC_PISTON_LV.setUnificationData(lvComponents, MarkerMaterials.Empty);
        ELECTRIC_PUMP_LV.setUnificationData(lvComponents, MarkerMaterials.Empty);
        ROBOT_ARM_LV.setUnificationData(lvComponents, MarkerMaterials.Empty);
        CONVEYOR_MODULE_LV.setUnificationData(lvComponents, MarkerMaterials.Empty);
        FIELD_GENERATOR_LV.setUnificationData(lvComponents, MarkerMaterials.Empty);
        EMITTER_LV.setUnificationData(lvComponents, MarkerMaterials.Empty);
        SENSOR_LV.setUnificationData(lvComponents, MarkerMaterials.Empty);
    }

    public static void initMvComponentsOre() {
        FLUID_REGULATOR_MV.setUnificationData(mvComponents, MarkerMaterials.Empty);
        ELECTRIC_MOTOR_MV.setUnificationData(mvComponents, MarkerMaterials.Empty);
        ELECTRIC_PISTON_MV.setUnificationData(mvComponents, MarkerMaterials.Empty);
        ELECTRIC_PUMP_MV.setUnificationData(mvComponents, MarkerMaterials.Empty);
        ROBOT_ARM_MV.setUnificationData(mvComponents, MarkerMaterials.Empty);
        CONVEYOR_MODULE_MV.setUnificationData(mvComponents, MarkerMaterials.Empty);
        FIELD_GENERATOR_MV.setUnificationData(mvComponents, MarkerMaterials.Empty);
        EMITTER_MV.setUnificationData(mvComponents, MarkerMaterials.Empty);
        SENSOR_MV.setUnificationData(mvComponents, MarkerMaterials.Empty);
    }

    public static void initHvComponentsOre() {
        FLUID_REGULATOR_HV.setUnificationData(hvComponents, MarkerMaterials.Empty);
        ELECTRIC_MOTOR_HV.setUnificationData(hvComponents, MarkerMaterials.Empty);
        ELECTRIC_PISTON_HV.setUnificationData(hvComponents, MarkerMaterials.Empty);
        ELECTRIC_PUMP_HV.setUnificationData(hvComponents, MarkerMaterials.Empty);
        ROBOT_ARM_HV.setUnificationData(hvComponents, MarkerMaterials.Empty);
        CONVEYOR_MODULE_HV.setUnificationData(hvComponents, MarkerMaterials.Empty);
        FIELD_GENERATOR_HV.setUnificationData(hvComponents, MarkerMaterials.Empty);
        EMITTER_HV.setUnificationData(hvComponents, MarkerMaterials.Empty);
        SENSOR_HV.setUnificationData(hvComponents, MarkerMaterials.Empty);
    }

    public static void initEvComponentsOre() {
        FLUID_REGULATOR_EV.setUnificationData(evComponents, MarkerMaterials.Empty);
        ELECTRIC_MOTOR_EV.setUnificationData(evComponents, MarkerMaterials.Empty);
        ELECTRIC_PISTON_EV.setUnificationData(evComponents, MarkerMaterials.Empty);
        ELECTRIC_PUMP_EV.setUnificationData(evComponents, MarkerMaterials.Empty);
        ROBOT_ARM_EV.setUnificationData(evComponents, MarkerMaterials.Empty);
        CONVEYOR_MODULE_EV.setUnificationData(evComponents, MarkerMaterials.Empty);
        FIELD_GENERATOR_EV.setUnificationData(evComponents, MarkerMaterials.Empty);
        EMITTER_EV.setUnificationData(evComponents, MarkerMaterials.Empty);
        SENSOR_EV.setUnificationData(evComponents, MarkerMaterials.Empty);
    }

    public static void initIvComponentsOre() {
        FLUID_REGULATOR_IV.setUnificationData(ivComponents, MarkerMaterials.Empty);
        ELECTRIC_MOTOR_IV.setUnificationData(ivComponents, MarkerMaterials.Empty);
        ELECTRIC_PISTON_IV.setUnificationData(ivComponents, MarkerMaterials.Empty);
        ELECTRIC_PUMP_IV.setUnificationData(ivComponents, MarkerMaterials.Empty);
        ROBOT_ARM_IV.setUnificationData(ivComponents, MarkerMaterials.Empty);
        CONVEYOR_MODULE_IV.setUnificationData(ivComponents, MarkerMaterials.Empty);
        FIELD_GENERATOR_IV.setUnificationData(ivComponents, MarkerMaterials.Empty);
        EMITTER_IV.setUnificationData(ivComponents, MarkerMaterials.Empty);
        SENSOR_IV.setUnificationData(ivComponents, MarkerMaterials.Empty);
    }

    public static List<OrePrefix> getComponentsOreprefixes() {
        ComponentsModule.initLvComponentsOre();
        ComponentsModule.initMvComponentsOre();
        ComponentsModule.initHvComponentsOre();
        ComponentsModule.initEvComponentsOre();
        ComponentsModule.initIvComponentsOre();
        return buildList(lvComponents, mvComponents, hvComponents, evComponents, ivComponents, luvComponents,
                zpmComponents, uvComponents);
    }

    public static List<ItemStack> getComponents() {
        List<ItemStack> components = new ArrayList<>();
        components.addAll(getMotors());
        components.addAll(getPumps());
        components.addAll(getPistons());
        components.addAll(getRobotArms());
        components.addAll(getConveyorModules());
        components.addAll(getFieldGenerators());
        components.addAll(getFluidRegulators());
        components.addAll(getSensors());
        components.addAll(getEmitters());
        return components;
    }
}
