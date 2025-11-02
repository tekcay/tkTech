package tkcy.tktech.api.capabilities;

import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("InstantiationOfUtilityClass")
public class TkTechMultiblockAbilities {

    public static final MultiblockAbility<IItemHandlerModifiable> BRICK_BUS_INPUT = new MultiblockAbility<>(
            "brick_bus_input");
    public static final MultiblockAbility<IItemHandlerModifiable> BRICK_BUS_OUTPUT = new MultiblockAbility<>(
            "brick_bus_output");
    public static final MultiblockAbility<IFluidTank> BRICK_HATCH_OUTPUT = new MultiblockAbility<>(
            "brick_hatch_output");
    public static final MultiblockAbility<IFluidTank> BRICK_HATCH_INPUT = new MultiblockAbility<>("brick_hatch_input");
    public static final MultiblockAbility<IItemHandler> CRATE_VALVE = new MultiblockAbility<>("crate_valve");
    public static final MultiblockAbility<IItemHandler> CHEST_VALVE = new MultiblockAbility<>("chest_valve");
}
