package tkcy.tktech.common.metatileentities.multiblockpart;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.tktech.api.capabilities.multiblock.IControllerProxy;
import tkcy.tktech.api.capabilities.multiblock.TkTechMultiblockAbilities;
import tkcy.tktech.api.render.TkTechTextures;

public class MTeControllerProxyHatch extends MetaTileEntityMultiblockPart
                                     implements IMultiblockAbilityPart<IControllerProxy>, IControllerProxy {

    public MTeControllerProxyHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, 1);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTeControllerProxyHatch(metaTileEntityId);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        TkTechTextures.CONTROLLER_PROXY_HATCH.render(renderState, translation, pipeline);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, @NotNull List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("tktech.controller_proxy_hatch.tooltip.1"));
        tooltip.add(I18n.format("tktech.controller_proxy_hatch.tooltip.2"));
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        T capabilityResult = super.getCapability(capability, side);
        if (capabilityResult != null) return capabilityResult;

        if (isAttachedToMultiBlock()) {
            return getController().getCapability(capability, side);
        }

        return null;
    }

    @Override
    public MultiblockAbility<IControllerProxy> getAbility() {
        return TkTechMultiblockAbilities.CONTROLLER_PROXY;
    }

    @Override
    public void registerAbilities(List<IControllerProxy> abilityList) {
        abilityList.add(this);
    }
}
