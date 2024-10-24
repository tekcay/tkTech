package tkcy.simpleaddon.api.metatileentities.capabilitiescontainers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.helpers.MultipleContainerWrapper;

@Getter
public abstract class DefaultContainerMetatileEntity extends MetaTileEntity implements IDataInfoProvider {

    protected abstract MultipleContainerWrapper getContainerWrapper();

    protected DefaultContainerMetatileEntity(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    protected abstract void doSomething();

    @Override
    public void update() {
        super.update();
        if (getOffsetTimer() % 10 == 0) {
            doSomething();
        }
    }

    @NotNull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();

        Arrays.stream(getContainerWrapper().getContainers())
                .filter(Objects::nonNull)
                .forEach(defaultContainer -> list.add(new TextComponentTranslation(
                        "behavior.tricorder.container.value", defaultContainer.printValue())));
        return list;
    }
}
