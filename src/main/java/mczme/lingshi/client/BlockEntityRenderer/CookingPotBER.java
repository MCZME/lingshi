package mczme.lingshi.client.BlockEntityRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mczme.lingshi.common.block.entity.CookingPotBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.ItemStackHandler;

import static mczme.lingshi.client.util.RenderUtil.fluidRender;
import static mczme.lingshi.common.block.CookingPotBlock.COVER;

public class CookingPotBER implements BlockEntityRenderer<CookingPotBlockEntity> {

    public CookingPotBER(BlockEntityRendererProvider.Context pContext) {

    }

    @Override
    public void render(CookingPotBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if(!pBlockEntity.getBlockState().getValue(COVER)){
            ItemStackHandler itemStack = pBlockEntity.getItemStacks();
            FluidStack fluidStack = pBlockEntity.getFluid();

            if (!pBlockEntity.isEmpty()) {
                if (!fluidStack.isEmpty()) {
                    fluidRender(fluidStack.getFluid(), pPoseStack, pBufferSource, pBlockEntity.getLevel(), pBlockEntity.getBlockPos(), pPackedLight, new float[]{10F/16.0F,16/16.0F,10F/16.0F,5F/16.0F,5F/16.0F,11F});
                }

                for (int i = 0; i < pBlockEntity.getMAX(); i++) {
                    if (itemStack.getStackInSlot(i).isEmpty()) continue;
                    pPoseStack.pushPose();
                    pPoseStack.translate(1.5*i/16F+0.25, 0.3, 0.5);
                    pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
                    pPoseStack.scale(0.5F, 0.5F, 0.5F);
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemStack.getStackInSlot(i), ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBufferSource, pBlockEntity.getLevel(), (int) pBlockEntity.getBlockPos().asLong());
                    pPoseStack.popPose();
                }
            }
        }
    }
}
