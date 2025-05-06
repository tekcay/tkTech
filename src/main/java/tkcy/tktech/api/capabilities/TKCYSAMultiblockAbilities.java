package tkcy.tktech.api.capabilities;

import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("InstantiationOfUtilityClass")
public class TKCYSAMultiblockAbilities {

    public static final MultiblockAbility<IItemHandlerModifiable> BRICK_BUS = new MultiblockAbility<>("brick_bus");
    public static final MultiblockAbility<IFluidTank> BRICK_HATCH = new MultiblockAbility<>("brick_hatch");
    public static final MultiblockAbility<IItemHandler> CRATE_VALVE = new MultiblockAbility<>("crate_valve");
    public static final MultiblockAbility<IItemHandler> CHEST_VALVE = new MultiblockAbility<>("chest_valve");
}
