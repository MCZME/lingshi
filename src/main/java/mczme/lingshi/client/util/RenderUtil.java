package mczme.lingshi.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.joml.Matrix4f;

public class RenderUtil {

    public static void fluidRender(Fluid fluid, PoseStack pPoseStack, MultiBufferSource pBufferSource, Level pLevel, BlockPos pPos,int pPackedLight,float height){
        pPoseStack.pushPose();
        pPoseStack.scale(13/16F, 13/16F, 13/16F);
        pPoseStack.translate(2/16F, 0F, 2/16F);
        TextureAtlasSprite[] atextureatlassprite = net.neoforged.neoforge.client.textures.FluidSpriteCache.getFluidSprites(pLevel, pPos, fluid.defaultFluidState());
        TextureAtlasSprite still = atextureatlassprite[0];
        int colour = fluid.isSame(Fluids.WATER)?BiomeColors.getAverageWaterColor(pLevel, pPos) : 0xA1EAD909;
        float red = (float) FastColor.ARGB32.red(colour) / 255.0F;
        float green = (float) FastColor.ARGB32.green(colour) / 255.0F;
        float blue = (float) FastColor.ARGB32.blue(colour) / 255.0F;
        float offset = height /16;
        float uScale = still.getU1() - still.getU0();
        float vScale = still.getV1() - still.getV0();
        float u0 = still.getU0() + uScale * (float)0;
        float u1 = still.getU0() + uScale;
        float v0 = still.getV0() + vScale * (float)0;
        float v1 = still.getV0() + vScale;
        RenderType type = RenderType.translucentMovingBlock();
        VertexConsumer consumer = pBufferSource.getBuffer(type);
        Matrix4f matrix = pPoseStack.last().pose();
        consumer.addVertex(matrix, (float)0, offset, (float)0).setColor(red, green, blue, 1.0F).setUv(u0, v0).setLight(pPackedLight).setNormal(0.0F, 1.0F, 0.0F);
        consumer.addVertex(matrix, (float)0, offset, (float)1).setColor(red, green, blue, 1.0F).setUv(u0, v1).setLight(pPackedLight).setNormal(0.0F, 1.0F, 0.0F);
        consumer.addVertex(matrix, (float)1, offset, (float)1).setColor(red, green, blue, 1.0F).setUv(u1, v1).setLight(pPackedLight).setNormal(0.0F, 1.0F, 0.0F);
        consumer.addVertex(matrix, (float)1, offset, (float)0).setColor(red, green, blue, 1.0F).setUv(u1, v0).setLight(pPackedLight).setNormal(0.0F, 1.0F, 0.0F);
        pPoseStack.popPose();
    }
}
