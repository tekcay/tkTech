package tkcy.simpleaddon.modules;

public enum NBTLabel {

    CURRENT_TOOL_USES,
    RECIPE_TOOL_USES,
    ITEM_OUTPUTS,
    FLUID_OUTPUTS;

    @Override
    public String toString() {
        return this.name();
    }
}
