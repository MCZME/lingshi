package mczme.lingshi.client.BlockEntityRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mczme.lingshi.common.block.ChoppingBoardBlock;
import mczme.lingshi.common.block.entity.ChoppingBoardBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ChoppingBoardBER implements BlockEntityRenderer<ChoppingBoardBlockEntity> {

    public ChoppingBoardBER(BlockEntityRendererProvider.Context pContext){

    }

    @Override
    public void render(ChoppingBoardBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemStack itemStack = pBlockEntity.getTheItem();
        Direction direction = pBlockEntity.getBlockState().getValue(ChoppingBoardBlock.FACING);

        double f;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        if(!itemRenderer.getModel(itemStack, pBlockEntity.getLevel(), null, 0).isGui3d()){
            f=0.08;
        } else
        {
            f=0.21;
        }

        pPoseStack.pushPose();
        if(!itemStack.isEmpty()){
            pPoseStack.translate(0.5,f,0.5);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            pPoseStack.scale(0.5F, 0.5F, 0.5F);
            Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBufferSource, pBlockEntity.getLevel(), (int) pBlockEntity.getBlockPos().asLong());
        }
        pPoseStack.popPose();
    }
}
