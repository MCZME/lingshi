package mczme.lingshi.client.screen;

import mczme.lingshi.common.block.entity.SkilletBlockEntity;
import mczme.lingshi.common.datamap.DataMapTypes;
import mczme.lingshi.common.datamap.ingredient.CookingFoodData;
import mczme.lingshi.common.network.CookingDataClientPayloadHandler;
import mczme.lingshi.common.registry.BlockEntityTypes;
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
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class CookingHud implements LayeredDraw.Layer {

    public static final CookingHud hud = new CookingHud();

    public static final ResourceLocation HUD_Sprite = ResourceLocation.fromNamespaceAndPath(lingshi.MODID, "textures/gui/cooking_hud.png");
    private ItemStackHandler itemStackHandler;
    private int[] cookingTime;
    private ItemStack result;
    private int MAX_SLOT;
    private Player player;
    private BlockPos blockPos;

    private final int X = 28;
    private final int Y = 140;

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, @NotNull DeltaTracker pDeltaTracker) {
        if (Minecraft.getInstance().level == null) {
            return;
        }
        this.player = Minecraft.getInstance().player;
        if (player != null && getHitResult(BlockEntityTypes.SKILLET_BLOCKENTITY.get())) {
            getData();
            for (int i = 0; i < MAX_SLOT; i++) {
                if (!itemStackHandler.getStackInSlot(i).isEmpty()) {
                    pGuiGraphics.renderItem(itemStackHandler.getStackInSlot(i), X-18, Y + i * 18);
                    CookingFoodData cookingFoodData = itemStackHandler.getStackInSlot(i).getItemHolder().getData(DataMapTypes.COOKING_FOOD_ITEM);
                    if (cookingFoodData != null) {
                        drawItemProgress(pGuiGraphics, cookingFoodData, i);
                    }
                }
            }
        }

    }

    private void getData() {
            SkilletBlockEntity blockEntity = null;
            if (Minecraft.getInstance().level != null) {
                blockEntity = (SkilletBlockEntity) Minecraft.getInstance().level.getBlockEntity(blockPos);
            }
            if (blockEntity != null) {
                this.itemStackHandler = blockEntity.getItemStacks();
                this.cookingTime = blockEntity.getCookingTime();
                this.result = CookingDataClientPayloadHandler.getResult();
                this.MAX_SLOT = blockEntity.getMAX();
            }
    }

    private boolean getHitResult(BlockEntityType pType) {
        Vec3 start = player.getEyePosition();
        Vec3 end = player.getLookAngle().normalize().scale(3).add(start);
        BlockHitResult blockHitResult = Minecraft.getInstance().level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        this.blockPos = blockHitResult.getBlockPos();
        return Minecraft.getInstance().level.getBlockEntity(blockPos) != null && Minecraft.getInstance().level.getBlockEntity(blockPos).getType() == pType;
    }

    private void drawItemProgress(GuiGraphics pGuiGraphics, CookingFoodData cookingFoodData, int i) {
        int pHeight = 8;
        pGuiGraphics.blit(HUD_Sprite, X, Y + i * 18 + 7, cookingFoodData.cookedTime() * 2, pHeight, 0, 0, cookingFoodData.cookedTime() * 2 + 2, pHeight, 64, 64);
        pGuiGraphics.blit(HUD_Sprite, X + cookingFoodData.cookedTime() * 2, Y+ i * 18 + 7, (cookingFoodData.burntTime() - cookingFoodData.cookedTime()) * 2, pHeight, 2, 8, (cookingFoodData.burntTime() - cookingFoodData.cookedTime()) * 2, pHeight, 64, 64);
        pGuiGraphics.blit(HUD_Sprite, X + cookingFoodData.burntTime() * 2, Y + i * 18 + 7, 62 - cookingFoodData.burntTime() * 2, pHeight, cookingFoodData.burntTime() * 2 + 2, 24, 62 - cookingFoodData.burntTime() * 2, pHeight, 64, 64);
        pGuiGraphics.blit(HUD_Sprite, X+1 + cookingTime[i] / 10, Y + i * 18 + 9, 4, 4, 0, 32, 4, 4, 64, 64);
    }

    public static CookingHud getInstance() {
        return hud;
    }
}
