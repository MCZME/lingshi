package mczme.lingshi.client.ItemRender;

import com.mojang.blaze3d.vertex.PoseStack;
import mczme.lingshi.common.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.fluids.FluidStack;

import static mczme.lingshi.client.util.RenderUtil.fluidRender;

public class lingshiBEWLR extends BlockEntityWithoutLevelRenderer {
    public lingshiBEWLR() {
        super(null, null);
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if(pStack.is(ModItems.GLASS_JAR.get())){
            renderGlassJar(pStack,pPoseStack,pBuffer,pPackedLight,pPackedOverlay);
        }
    }

    private void renderGlassJar(ItemStack pStack,PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay){
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        pPoseStack.pushPose();
        if(pStack.get(DataComponents.BLOCK_ENTITY_DATA)!=null){
            CustomData customData = pStack.get(DataComponents.BLOCK_ENTITY_DATA);
            FluidStack fluidStack = FluidStack.EMPTY;
            if(customData.copyTag().getCompound("fluidStack")!=null){
                CompoundTag compoundTag = customData.copyTag().getCompound("fluidStack");
                fluidStack = FluidStack.parse(Minecraft.getInstance().level.registryAccess(), compoundTag).orElse(FluidStack.EMPTY);
            }
            if(!fluidStack.isEmpty()) {
                fluidRender(fluidStack,5.1F,1,5.1F,5.8F,10,5.8F,1000,pPoseStack,pBuffer,pPackedLight);
            }
            blockRenderer.renderSingleBlock(((BlockItem)pStack.getItem()).getBlock().defaultBlockState(), pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }else {
            blockRenderer.renderSingleBlock(((BlockItem)pStack.getItem()).getBlock().defaultBlockState(), pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }
        pPoseStack.popPose();

    }

    }
