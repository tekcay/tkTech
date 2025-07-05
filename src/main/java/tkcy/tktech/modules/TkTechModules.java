package tkcy.tktech.modules;

import gregtech.api.modules.IModuleContainer;
import gregtech.api.modules.ModuleContainer;

import tkcy.tktech.TkTech;

@ModuleContainer
public class TkTechModules implements IModuleContainer {

    public static final String MODULE_CORE = "tktech_core";
    public static final String MODULE_JEI = "tktech_jei_integration";

    @Override
    public String getID() {
        return TkTech.MODID;
    }
}
