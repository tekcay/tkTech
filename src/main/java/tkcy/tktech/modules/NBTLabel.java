package tkcy.tktech.modules;

public enum NBTLabel {

    CURRENT_TOOL_USES,
    RECIPE_TOOL_USES,
    ITEM_OUTPUTS,
    FLUID_OUTPUTS,
    ITEM_INVENTORY,
    ITEM_QUANTITY,
    SLOT_INDEX,
    TOTAL_CAPACITY,
    BLOCK_POS_X,
    BLOCK_POS_Y,
    BLOCK_POS_Z,
    TOOL_ORDINAL,
    INPUT_IN_WORLD_STACK,
    OUTPUT_IN_WORLD_STACK,
    FAILED_OUTPUT_STACK;

    @Override
    public String toString() {
        return this.name();
    }
}
