package mczme.lingshi.client.screen;

import mczme.lingshi.common.block.entity.CookingPotBlockEntity;
import mczme.lingshi.common.block.entity.SkilletBlockEntity;
import mczme.lingshi.common.block.entity.baseblockentity.ICanBeHeated;
import mczme.lingshi.common.datamap.DataMapTypes;
import mczme.lingshi.common.datamap.ingredient.CookingFoodData;
import mczme.lingshi.common.registry.BlockEntityTypes;
import mczme.lingshi.common.registry.ModFluids;
import mczme.lingshi.lingshi;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class CookingHud implements LayeredDraw.Layer {

    public static final CookingHud hud = new CookingHud();

    public static final ResourceLocation HUD_Sprite = ResourceLocation.fromNamespaceAndPath(lingshi.MODID, "textures/gui/cooking_hud.png");
    private ItemStackHandler itemStackHandler;
    private FluidStack fluidStack = FluidStack.EMPTY;
    private ItemStack container = ItemStack.EMPTY;
    private int Count;
    private int[] cookingTime;
    private ItemStack result = ItemStack.EMPTY;
    private int MAX_SLOT;
    private Player player;
    private BlockPos blockPos;

    private final int X = 28;
    private final int Y = 104;

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, @NotNull DeltaTracker pDeltaTracker) {
        if (Minecraft.getInstance().level == null) {
            return;
        }
        this.player = Minecraft.getInstance().player;
        if (player != null && getHitResult(BlockEntityTypes.SKILLET_BLOCKENTITY.get())) {
            getData((SkilletBlockEntity)Minecraft.getInstance().level.getBlockEntity(blockPos));
            int j = 0;
            if (!result.isEmpty()) {
                pGuiGraphics.renderItem(result, X - 18, Y);
                pGuiGraphics.blit(HUD_Sprite, X, Y , 16, 16, 0, 36,16,16, 64, 64);
                pGuiGraphics.drawCenteredString(Minecraft.getInstance().font, String.valueOf(Count), X + 24, Y + 6, 0xffffff);
                if (!container.isEmpty()) {
                    pGuiGraphics.renderItem(container, X + 36, Y);
                }
                j++;
            }
            if (!fluidStack.isEmpty()) {
                CookingFoodData cookingFoodData = fluidStack.getFluidHolder().getData(DataMapTypes.COOKING_FOOD_FLUID);
                if (cookingFoodData != null) {
                    drawFluidProgress(pGuiGraphics, cookingFoodData, j);
                }
                j++;
            }
            for (int i = 0; i < MAX_SLOT; i++) {
                if (!itemStackHandler.getStackInSlot(i).isEmpty()) {
                    pGuiGraphics.renderItem(itemStackHandler.getStackInSlot(i), X - 18, Y + (i + j) * 18);
                    CookingFoodData cookingFoodData = itemStackHandler.getStackInSlot(i).getItemHolder().getData(DataMapTypes.COOKING_FOOD_ITEM);
                    if (cookingFoodData != null) {
                        drawItemProgress(pGuiGraphics, cookingFoodData, i, j);
                    }
                }
            }
        } else if (player != null && getHitResult(BlockEntityTypes.COOKING_POT_BLOCKENTITY.get())) {
            getData((CookingPotBlockEntity)Minecraft.getInstance().level.getBlockEntity(blockPos));
            int j = 0;
            if (!result.isEmpty()) {
                pGuiGraphics.renderItem(result, X - 18, Y);
//                pGuiGraphics.blit(HUD_Sprite, X, Y , 16, 16, 0, 36,16,16, 64, 64);
                pGuiGraphics.drawCenteredString(Minecraft.getInstance().font, String.valueOf(Count), X + 24, Y + 6, 0xffffff);
                if (!container.isEmpty()) {
                    pGuiGraphics.renderItem(container, X + 36, Y);
                }
                j++;
            }
            if (!fluidStack.isEmpty()) {
                CookingFoodData cookingFoodData = fluidStack.getFluidHolder().getData(DataMapTypes.COOKING_FOOD_FLUID);
                if (cookingFoodData != null) {
                    drawFluidProgress(pGuiGraphics, cookingFoodData, j);
                }
                j++;
            }
            for (int i = 0; i < MAX_SLOT; i++) {
                if (!itemStackHandler.getStackInSlot(i).isEmpty()) {
                    pGuiGraphics.renderItem(itemStackHandler.getStackInSlot(i), X - 18, Y + (i + j) * 18);
                    CookingFoodData cookingFoodData = itemStackHandler.getStackInSlot(i).getItemHolder().getData(DataMapTypes.COOKING_FOOD_ITEM);
                    if (cookingFoodData != null) {
                        drawItemProgress(pGuiGraphics, cookingFoodData, i, j);
                    }
                }
            }
        }
    }

    private <T extends ICanBeHeated> void getData(T blockEntity) {
        if (blockEntity != null) {
            this.itemStackHandler = blockEntity.getItemStacks();
            this.fluidStack = blockEntity.getFluid();
            this.cookingTime = blockEntity.getCookingTime();
            this.MAX_SLOT = blockEntity.getMAX();
            this.result = blockEntity.getResult();
            this.container = blockEntity.getContainer();
            if(blockEntity instanceof SkilletBlockEntity blockEntity1){
                this.Count = blockEntity1.stirFryCount;
            }else if (blockEntity instanceof CookingPotBlockEntity blockEntity1){
                this.Count = blockEntity1.stewingTime;
            }
        }
    }

    private boolean getHitResult(BlockEntityType pType) {
        Vec3 start = player.getEyePosition();
        Vec3 end = player.getLookAngle().normalize().scale(3).add(start);
        BlockHitResult blockHitResult = Minecraft.getInstance().level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        this.blockPos = blockHitResult.getBlockPos();
        return Minecraft.getInstance().level.getBlockEntity(blockPos) != null && Minecraft.getInstance().level.getBlockEntity(blockPos).getType() == pType;
    }

    private void drawItemProgress(GuiGraphics pGuiGraphics, CookingFoodData cookingFoodData, int i, int j) {
        int pHeight = 8;
        int progress = Math.min(cookingTime[i], 580);
        int index = i + j;
        pGuiGraphics.blit(HUD_Sprite, X, Y + index * 18 + 7, cookingFoodData.cookedTime() * 2, pHeight, 0, 0, cookingFoodData.cookedTime() * 2 + 2, pHeight, 64, 64);
        pGuiGraphics.blit(HUD_Sprite, X + cookingFoodData.cookedTime() * 2, Y + index * 18 + 7, (cookingFoodData.burntTime() - cookingFoodData.cookedTime()) * 2, pHeight, 2, 8, (cookingFoodData.burntTime() - cookingFoodData.cookedTime()) * 2, pHeight, 64, 64);
        pGuiGraphics.blit(HUD_Sprite, X + cookingFoodData.burntTime() * 2, Y + index * 18 + 7, 62 - cookingFoodData.burntTime() * 2, pHeight, cookingFoodData.burntTime() * 2 + 2, 24, 62 - cookingFoodData.burntTime() * 2, pHeight, 64, 64);
        pGuiGraphics.blit(HUD_Sprite, X + progress / 10, Y + index * 18 + 9, 4, 4, 0, 32, 4, 4, 64, 64);
    }

    private void drawFluidProgress(GuiGraphics pGuiGraphics, CookingFoodData cookingFoodData, int j) {
        int pHeight = 8;
        int progress = Math.min(cookingTime[5], 580);
        ItemStack bucket = ModFluids.MOD_FLUID_TYPE.get().getBucket(fluidStack);
        if (!bucket.isEmpty()) {
            pGuiGraphics.renderItem(bucket, X - 18, Y + j * 18);
        }
        pGuiGraphics.blit(HUD_Sprite, X, Y + j * 18+ 7, cookingFoodData.cookedTime() * 2, pHeight, 0, 0, cookingFoodData.cookedTime() * 2 + 2, pHeight, 64, 64);
        pGuiGraphics.blit(HUD_Sprite, X + cookingFoodData.cookedTime() * 2, Y + j * 18+ 7, 62 - cookingFoodData.cookedTime() * 2, pHeight, cookingFoodData.cookedTime() * 2 + 2, 16, 62 - cookingFoodData.cookedTime() * 2, pHeight, 64, 64);
        pGuiGraphics.blit(HUD_Sprite, X + progress / 10, Y + j * 18 + 9, 4, 4, 0, 32, 4, 4, 64, 64);
    }

    public static CookingHud getInstance() {
        return hud;
    }
}
