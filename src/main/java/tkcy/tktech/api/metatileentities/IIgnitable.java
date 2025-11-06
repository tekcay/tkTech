package tkcy.tktech.api.metatileentities;

import java.util.List;

import net.minecraft.client.resources.I18n;

public interface IIgnitable {

    String NBT_LABEL = "isIgnited";

    boolean isIgnited();

    void ignite();

    void shutOff();

    default void addIgnitableInformations(List<String> tooltip) {
        tooltip.add(I18n.format("tktech.machine.ignitable.tooltip"));
    }
}
