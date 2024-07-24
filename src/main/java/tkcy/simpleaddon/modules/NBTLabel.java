package tkcy.simpleaddon.modules;

public enum NBTLabel {

    CURRENT_TOOL_USES,
    RECIPE_TOOL_USES,
    ITEM_OUTPUTS,
    FLUID_OUTPUTS,
    ITEM_INVENTORY,
    SLOT_INDEX;

    @Override
    public String toString() {
        return this.name();
    }
}
