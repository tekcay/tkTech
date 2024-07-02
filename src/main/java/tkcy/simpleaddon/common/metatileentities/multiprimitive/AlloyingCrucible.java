package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import static tkcy.simpleaddon.api.predicates.Predicates.*;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;

import tkcy.simpleaddon.api.machines.NoEnergyMultiController;
import tkcy.simpleaddon.api.recipes.logic.NoEnergyLogic;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;

public class AlloyingCrucible extends NoEnergyMultiController {

    public AlloyingCrucible(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYSARecipeMaps.ALLOYING);
        this.recipeMapWorkable = new NoEnergyLogic(this);
        initializeAbilities();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new AlloyingCrucible(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.FRONT, RelativeDirection.UP)
                .aisle("XXX", "XAX", "XXX")
                .aisle("CYC", "C#C", "CCC")
                .aisle("CCC", "CIC", "CCC")
                .where('C', cokeBrick()
                        .or(brickFluidHatch(false, 2)))
                .where('I', brickItemBus(false, 1))
                .where('A', brickFluidHatch(true, 1))
                .where('X', cokeBrick())
                .where('#', air())
                .where('Y', selfPredicate())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.PRIMITIVE_BLAST_FURNACE_OVERLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.COKE_BRICKS;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("tkcysa.machine.alloying_crucible.1"));
    }
}
