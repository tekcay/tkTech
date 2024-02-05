package tkcy.simpleaddon.common.metatileentities.primitive;

import static tkcy.simpleaddon.modules.NBTLabel.HIT_NUMBER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.items.toolitem.ToolClasses;
import gregtech.api.items.toolitem.ToolHelper;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.simpleaddon.api.items.toolitem.ToolAction;
import tkcy.simpleaddon.api.recipes.builders.ToolRecipeBuilder;
import tkcy.simpleaddon.modules.ToolsModule;

public class MetaTileEntityParts extends MetaTileEntity implements ToolAction {

    private int hitNumber = 0;
    private int hitNumberToTransform;
    private final RecipeMap<ToolRecipeBuilder> recipeMap;
    private Recipe currentRecipe;
    private String toolClass;
    private final Map<String, Consumer<EntityPlayer>> toolClassToToolAction;

    public MetaTileEntityParts(ResourceLocation metaTileEntityId, RecipeMap<ToolRecipeBuilder> recipeMap) {
        super(metaTileEntityId);
        this.recipeMap = recipeMap;
        this.toolClassToToolAction = setToolClassToOnUsage();
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 9, this, false);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new NotifiableItemStackHandler(this, 2, this, true);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, new FluidTank(2000));
    }

    private void setToolClass(ItemStack itemStack, EntityPlayer playerIn) {
        Set<String> toolClasses = itemStack.getItem().getToolClasses(itemStack);
        if (toolClasses.isEmpty()) this.toolClass = null;

        this.toolClass = ToolsModule.getToolClass(toolClasses);

        if (!this.toolClassToToolAction.containsKey(this.toolClass)) return;
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                CuboidRayTraceResult hitResult) {
        if (facing.equals(EnumFacing.UP))
            return super.onRightClick(playerIn, hand, facing, hitResult);

        if (!super.onRightClick(playerIn, hand, facing, hitResult)) {

            ItemStack toolItemStack = playerIn.getHeldItem(hand);
            if (!playerIn.isSneaking() || toolItemStack.isEmpty()) return true;

            this.setToolClass(toolItemStack, playerIn);
            if (!this.tryToDamage(toolItemStack, playerIn)) return true;

            // Recipe getCRecipe()

        }
        return true;
    }

    private boolean tryToDamage(ItemStack toolStack, EntityPlayer playerIn) {
        if (this.toolClass == null) return false;
        ToolHelper.damageItem(toolStack, playerIn);
        return true;
    }

    @SideOnly(Side.CLIENT)
    protected SimpleOverlayRenderer getBaseRenderer() {
        return Textures.COKE_BRICKS;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline,
                new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        ColourMultiplier multiplier = new ColourMultiplier(
                GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        colouredPipeline = ArrayUtils.add(pipeline, multiplier);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.SOUTH);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.NORTH);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.EAST);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.WEST);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.1"));
        tooltip.add(I18n.format("tkcya.machine.axe_support.tooltip.2"));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityParts(metaTileEntityId, this.recipeMap);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger(HIT_NUMBER.name(), hitNumber);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.hitNumber = data.getInteger(HIT_NUMBER.name());
    }

    @Override
    public void writeInitialSyncData(@NotNull PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.hitNumber);
    }

    @Override
    public void receiveInitialSyncData(@NotNull PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.hitNumber = buf.readInt();
    }

    public static void onHardHammerClick(EntityPlayer player) {}

    @Override
    public Map<String, Consumer<EntityPlayer>> setToolClassToOnUsage() {
        Map<String, Consumer<EntityPlayer>> map = new HashMap<>();
        map.put(ToolClasses.HARD_HAMMER, MetaTileEntityParts::onHardHammerClick);
        return map;
    }

    @Override
    @Nullable
    public Recipe getCurrentRecipe() {
        return this.recipeMap.getRecipeList()
                .stream()
                .findAny().orElse(null);
    }
}
