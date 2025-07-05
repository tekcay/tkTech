package tkcy.tktech.modules;

import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import gregtech.api.modules.GregTechModule;
import gregtech.api.modules.IGregTechModule;

import tkcy.tktech.TkTech;
import tkcy.tktech.api.utils.TkTechLog;

@GregTechModule(moduleID = TkTechModules.MODULE_CORE,
                containerID = TkTech.MODID,
                name = "TkTech Core Module",
                description = "Core module of TkTech",
                coreModule = true)
public class TkTechCoreModule implements IGregTechModule {

    @NotNull
    @Override
    public Logger getLogger() {
        return TkTechLog.logger;
    }
}
