package tkcy.simpleaddon.common.metatileentities.multiblockpart;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerTypeWrapper;

public abstract class MetaTileEntityCapabilityHatch<T extends DefaultContainer> extends MetaTileEntityMultiblockPart
                                                   implements IMultiblockAbilityPart<T>, IDataInfoProvider {

    private final boolean isInput;
    private final ContainerTypeWrapper<T> containerType;
    private T container;

    protected MetaTileEntityCapabilityHatch(ResourceLocation metaTileEntityId, ContainerType containerType,
                                            boolean isInput, int tier) {
        super(metaTileEntityId, tier);
        this.isInput = isInput;
        this.containerType = (ContainerTypeWrapper<T>) containerType.getContainerTypeWrapper();
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public MultiblockAbility<T> getAbility() {
        return this.isInput ? this.containerType.getInputMultiblockAbility() :
                this.containerType.getOutputMultiblockAbility();
    }

    @Override
    @Nullable
    public <U> U getCapability(@NotNull Capability<U> capability, EnumFacing side) {
        if (capability.equals(this.containerType.getCapability())) {
            return this.containerType.getCapability().cast(this.container);
        }
        return null;
    }

    @NotNull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.container.value", container.printValue()));
        list.add(new TextComponentTranslation("behavior.tricorder.container.max_value", container.printMaxValue()));
        return list;
    }
}
