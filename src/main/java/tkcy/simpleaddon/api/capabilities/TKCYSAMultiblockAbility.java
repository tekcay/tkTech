package tkcy.simpleaddon.api.capabilities;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.abilities;

import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.TraceabilityPredicate;

@SuppressWarnings("InstantiationOfUtilityClass")
public class TKCYSAMultiblockAbility {

    public static final MultiblockAbility<IItemHandlerModifiable> BRICK_ITEMS_INPUT = new MultiblockAbility<>(
            "brick_items_input");
    public static final MultiblockAbility<IItemHandlerModifiable> BRICK_ITEMS_OUTPUT = new MultiblockAbility<>(
            "brick_items_input");
    public static final MultiblockAbility<IFluidTank> BRICK_FLUIDS_INPUT = new MultiblockAbility<>(
            "brick_fluids_input");
    public static final MultiblockAbility<IFluidTank> BRICK_FLUIDS_OUTPUT = new MultiblockAbility<>(
            "brick_fluids_output");

    public static final TraceabilityPredicate BRICK_ITEMS_INPUT_PREDICATE = abilities(
            TKCYSAMultiblockAbility.BRICK_ITEMS_INPUT);
    public static final TraceabilityPredicate BRICK_ITEMS_OUTPUT_PREDICATE = abilities(
            TKCYSAMultiblockAbility.BRICK_ITEMS_OUTPUT);
    public static final TraceabilityPredicate BRICK_FLUIDS_INPUT_PREDICATE = abilities(
            TKCYSAMultiblockAbility.BRICK_FLUIDS_INPUT);
    public static final TraceabilityPredicate BRICK_FLUIDS_OUTPUT_PREDICATE = abilities(
            TKCYSAMultiblockAbility.BRICK_FLUIDS_OUTPUT);
}
