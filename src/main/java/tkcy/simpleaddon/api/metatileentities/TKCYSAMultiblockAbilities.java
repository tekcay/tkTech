package tkcy.simpleaddon.api.metatileentities;

import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("InstantiationOfUtilityClass")
public class TKCYSAMultiblockAbilities {

    public static final MultiblockAbility<IItemHandlerModifiable> BRICK_BUS = new MultiblockAbility<>("brick_bus");
    public static final MultiblockAbility<IFluidTank> BRICK_HATCH = new MultiblockAbility<>("brick_hatch");
}
