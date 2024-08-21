package mczme.lingshi.client.BlockEntityRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mczme.lingshi.client.model.ModelMap;
import mczme.lingshi.common.block.ChoppingBoardBlock;
import mczme.lingshi.common.block.entity.SkilletBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.Random;

import static mczme.lingshi.client.util.RenderUtil.fluidRender;


public class SkilletBER implements BlockEntityRenderer<SkilletBlockEntity> {

    public SkilletBER(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void render(SkilletBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemStackHandler itemStack = pBlockEntity.getItemStacks();
        FluidStack fluidStack = pBlockEntity.getFluid();
        Direction direction = pBlockEntity.getBlockState().getValue(ChoppingBoardBlock.FACING);
        double[] X = {0.5, 0.3, 0.6, 0.3, 0.6};
        double[] Z = {0.5, 0.3, 0.3, 0.6, 0.6};
        double[] Y = {0.13, 0.125, 0.123, 0.125, 0.122};

        if (!pBlockEntity.isEmpty()) {
            if (!fluidStack.isEmpty()) {
                if(fluidStack.getFluid().isSame(Fluids.WATER)){
                    fluidRender(fluidStack.getFluid(), pPoseStack, pBufferSource, pBlockEntity.getLevel(), pBlockEntity.getBlockPos(), pPackedLight, 2.5f);
                }else {
                    fluidRender(fluidStack.getFluid(), pPoseStack, pBufferSource, pBlockEntity.getLevel(), pBlockEntity.getBlockPos(), pPackedLight, 1.5f);
                }
            }

            for (int i = 0; i < pBlockEntity.getMAX(); i++) {
                if (itemStack.getStackInSlot(i).isEmpty()) continue;
                pPoseStack.pushPose();
                pPoseStack.translate(X[i], Y[i], Z[i]);
                pPoseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
                pPoseStack.mulPose(Axis.YP.rotationDegrees(randomAngel(pBlockEntity.stirFryCount)));
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                pPoseStack.scale(0.5F, 0.5F, 0.5F);
                Minecraft.getInstance().getItemRenderer().renderStatic(ModelMap.get(itemStack.getStackInSlot(i)), ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBufferSource, pBlockEntity.getLevel(), (int) pBlockEntity.getBlockPos().asLong());
                pPoseStack.popPose();
            }
        }
    }

    private float randomAngel(int stirFry) {
        Random rand = new Random();
        int seed = switch (stirFry % 3) {
            case 1 -> 123456;
            case 2 -> 886549;
            default -> 0;
        };
        rand.setSeed(seed);
        return seed == 0 ? 0 : rand.nextFloat() * 180;
    }

}
