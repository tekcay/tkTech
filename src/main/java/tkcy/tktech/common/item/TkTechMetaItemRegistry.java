package tkcy.tktech.common.item;

import static tkcy.tktech.common.item.TkTechMetaItems.*;

import gregtech.api.items.metaitem.StandardMetaItem;

public class TkTechMetaItemRegistry extends StandardMetaItem {

    public TkTechMetaItemRegistry(short i) {
        super(i);
    }

    @Override
    public void registerSubItems() {
        MICA_SHEET = addItem(1, "mica.sheet");
        MICA_INSULATOR_SHEET = addItem(2, "mica_insulator.sheet");
        MICA_INSULATOR_FOIL = addItem(3, "mica_insulator.foil");

        // ULV Components
        ELECTRIC_MOTOR_ULV = addItem(200, "electric_motor_ulv");
        ELECTRIC_PUMP_ULV = addItem(201, "electric_pump_ulv");
        ELECTRIC_PISTON_ULV = addItem(202, "piston_ulv");
        CONVEYOR_MODULE_ULV = addItem(203, "conveyor_ulv");
        ROBOT_ARM_ULV = addItem(204, "robot_arm_ulv");
    }
}
