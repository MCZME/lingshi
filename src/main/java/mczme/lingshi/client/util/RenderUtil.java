package mczme.lingshi.client.util;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class RenderUtil {

    /**
     * 渲染一个流体平面
     */
    public static void fluidRender(Fluid fluid, PoseStack pPoseStack, MultiBufferSource pBufferSource, Level pLevel, BlockPos pPos, int pPackedLight, float[] size) {
        pPoseStack.pushPose();
        pPoseStack.scale(size[0], size[1], size[2]);
        pPoseStack.translate(size[3], 0F, size[4]);
        TextureAtlasSprite[] atextureatlassprite = net.neoforged.neoforge.client.textures.FluidSpriteCache.getFluidSprites(pLevel, pPos, fluid.defaultFluidState());
        TextureAtlasSprite still = atextureatlassprite[0];
        int colour = fluid.isSame(Fluids.WATER) ? BiomeColors.getAverageWaterColor(pLevel, pPos) : 0xA1EAD909;
        float red = (float) FastColor.ARGB32.red(colour) / 255.0F;
        float green = (float) FastColor.ARGB32.green(colour) / 255.0F;
        float blue = (float) FastColor.ARGB32.blue(colour) / 255.0F;
        float offset = size[5] / 16;
        float uScale = still.getU1() - still.getU0();
        float vScale = still.getV1() - still.getV0();
        float u0 = still.getU0() + uScale * (float) 0;
        float u1 = still.getU0() + uScale;
        float v0 = still.getV0() + vScale * (float) 0;
        float v1 = still.getV0() + vScale;
        RenderType type = RenderType.translucentMovingBlock();
        VertexConsumer consumer = pBufferSource.getBuffer(type);
        Matrix4f matrix = pPoseStack.last().pose();
        consumer.addVertex(matrix, (float) 0, offset, (float) 0).setColor(red, green, blue, 1.0F).setUv(u0, v0).setLight(pPackedLight).setNormal(0.0F, 1.0F, 0.0F);
        consumer.addVertex(matrix, (float) 0, offset, (float) 1).setColor(red, green, blue, 1.0F).setUv(u0, v1).setLight(pPackedLight).setNormal(0.0F, 1.0F, 0.0F);
        consumer.addVertex(matrix, (float) 1, offset, (float) 1).setColor(red, green, blue, 1.0F).setUv(u1, v1).setLight(pPackedLight).setNormal(0.0F, 1.0F, 0.0F);
        consumer.addVertex(matrix, (float) 1, offset, (float) 0).setColor(red, green, blue, 1.0F).setUv(u1, v0).setLight(pPackedLight).setNormal(0.0F, 1.0F, 0.0F);
        pPoseStack.popPose();
    }

    /**
     * 渲染一个流体方块
     */
    public static void fluidRender(FluidStack fluidStack, float X, float Y, float Z, float length, int height, float width, int capacity, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight) {
        Fluid fluid = fluidStack.getFluid();
        if (fluid.isSame(Fluids.EMPTY)) {
            return;
        }
        pPoseStack.pushPose();
        IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
        ResourceLocation fluidStill = renderProperties.getStillTexture(fluidStack);
        Minecraft minecraft = Minecraft.getInstance();
        TextureAtlasSprite fluidStillSprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStill);
        int fluidColor = renderProperties.getTintColor(fluidStack);
        long amount = fluidStack.getAmount();
        long scaledAmount = (amount * height) / capacity;
        if (scaledAmount > height) {
            scaledAmount = height;
        }

        float red = (fluidColor >> 16 & 0xFF) / 255.0F;
        float green = (fluidColor >> 8 & 0xFF) / 255.0F;
        float blue = (fluidColor & 0xFF) / 255.0F;
        float alpha = ((fluidColor >> 24) & 0xFF) / 255F;

        float uMin = fluidStillSprite.getU0();
        float uMax = fluidStillSprite.getU1();
        float vMin = fluidStillSprite.getV0();
        float vMax = fluidStillSprite.getV1();
        uMax = uMax - 0.25F * (uMax - uMin);
        vMax = vMax - 0.25F * (vMax - vMin);

        VertexConsumer bufferBuilder = pBufferSource.getBuffer(RenderType.translucent());
        Matrix4f matrix = pPoseStack.last().pose();
//        顶面
        bufferBuilder.addVertex(matrix, X / 16, (Y + scaledAmount) / 16, Z / 16).setUv(uMin, vMax).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, X / 16, (Y + scaledAmount) / 16, (Z + width) / 16).setUv(uMax, vMax).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, (X + length) / 16, (Y + scaledAmount) / 16, (Z + width) / 16).setUv(uMax, vMin).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, (X + length) / 16, (Y + scaledAmount) / 16, Z / 16).setUv(uMin, vMin).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
//        北面
        bufferBuilder.addVertex(matrix, X / 16, Y / 16, Z / 16).setUv(uMin, vMax).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, X / 16, (Y + scaledAmount) / 16, Z / 16).setUv(uMax, vMax).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, (X + length) / 16, (Y + scaledAmount) / 16, Z / 16).setUv(uMax, vMin).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, (X + length) / 16, Y / 16, Z / 16).setUv(uMin, vMin).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
//        西面
        bufferBuilder.addVertex(matrix, X / 16, Y / 16, Z / 16).setUv(uMin, vMax).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, X / 16, Y / 16, (Z + width) / 16).setUv(uMax, vMax).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, X / 16, (Y + scaledAmount) / 16, (Z + width) / 16).setUv(uMax, vMin).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, X / 16, (Y + scaledAmount) / 16, Z / 16).setUv(uMin, vMin).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
//        南面
        bufferBuilder.addVertex(matrix, (X + length) / 16, (Y + scaledAmount) / 16, (Z + width) / 16).setUv(uMin, vMin).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, X / 16, (Y + scaledAmount) / 16, (Z + width) / 16).setUv(uMax, vMin).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, X / 16, Y / 16, (Z + width) / 16).setUv(uMax, vMax).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, (X + length) / 16, Y / 16, (Z + width) / 16).setUv(uMin, vMax).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
//        东面
        bufferBuilder.addVertex(matrix, (X + length) / 16, (Y + scaledAmount) / 16, Z / 16).setUv(uMin, vMin).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, (X + length) / 16, (Y + scaledAmount) / 16, (Z + width) / 16).setUv(uMax, vMin).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, (X + length) / 16, Y / 16, (Z + width) / 16).setUv(uMax, vMax).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);
        bufferBuilder.addVertex(matrix, (X + length) / 16, Y / 16, Z / 16).setUv(uMin, vMax).setNormal(0.0F, 1.0F, 0.0F).setColor(red, green, blue, alpha).setLight(pPackedLight);

        pPoseStack.popPose();
    }

}
