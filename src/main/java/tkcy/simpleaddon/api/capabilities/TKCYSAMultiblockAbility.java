package tkcy.simpleaddon.api.capabilities;

import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;

@SuppressWarnings("InstantiationOfUtilityClass")
public class TKCYSAMultiblockAbility {

    public static final MultiblockAbility<IItemHandlerModifiable> BRICK_ITEMS = new MultiblockAbility<>("brick_items");
    public static final MultiblockAbility<IFluidTank> BRICK_FLUIDS = new MultiblockAbility<>("brick_fluids");
}
