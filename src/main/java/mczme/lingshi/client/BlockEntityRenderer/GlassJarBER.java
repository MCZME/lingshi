package mczme.lingshi.client.BlockEntityRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import mczme.lingshi.common.block.entity.GlassJarBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.neoforged.neoforge.fluids.FluidStack;

import static mczme.lingshi.client.util.RenderUtil.fluidRender;

public class GlassJarBER implements BlockEntityRenderer<GlassJarBlockEntity> {

    public GlassJarBER(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void render(GlassJarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        FluidStack fluidStack = pBlockEntity.getFluidStack();
        if(!fluidStack.isEmpty()) {
            fluidRender(fluidStack,5.1F,1,5.1F,5.8F,10,5.8F,1000,pPoseStack,pBufferSource,pPackedLight);
        }
    }
}
