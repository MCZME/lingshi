package mczme.lingshi.client.BlockEntityRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mczme.lingshi.common.block.ChoppingBoardBlock;
import mczme.lingshi.common.block.entity.SkilletBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SkilletBER implements BlockEntityRenderer<SkilletBlockEntity> {

    public SkilletBER(BlockEntityRendererProvider.Context pContext){

    }

    @Override
    public void render(SkilletBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        List<ItemStack> itemStack = pBlockEntity.getItemStacks();
        Direction direction = pBlockEntity.getBlockState().getValue(ChoppingBoardBlock.FACING);

        if(!itemStack.isEmpty()){
            for (int i = 0; i <itemStack.size(); i++) {
                if(itemStack.get(i).isEmpty()) continue;
                pPoseStack.pushPose();
                pPoseStack.translate(0.5,0.2+i/10.0,0.5);
                pPoseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                pPoseStack.scale(0.5F, 0.5F, 0.5F);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemStack.get(i), ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBufferSource, pBlockEntity.getLevel(), (int) pBlockEntity.getBlockPos().asLong());
                pPoseStack.popPose();
            }
        }
    }
}