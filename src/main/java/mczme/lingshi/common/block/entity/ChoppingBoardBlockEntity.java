package mczme.lingshi.common.block.entity;

import mczme.lingshi.common.recipe.ChoppingBoardRecipe;
import mczme.lingshi.common.registry.BlockEntityTypes;
import mczme.lingshi.common.registry.ModRecipes;
import mczme.lingshi.common.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.ContainerSingleItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChoppingBoardBlockEntity extends BlockEntity implements ContainerSingleItem {

    private ItemStack item = ItemStack.EMPTY;

    public ChoppingBoardBlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypes.CHOPPING_BOARD_BLOCKENTITY.get(), pPos, pBlockState);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return this.saveCustomOnly(pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        if (pTag.contains("item", 10)) {
            this.item = ItemStack.parse(pRegistries, pTag.getCompound("item")).orElse(ItemStack.EMPTY);
        } else {
            this.item = ItemStack.EMPTY;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        if(!this.item.isEmpty()){
            pTag.put("item", this.item.save(pRegistries));
        }
    }

    @Override
    public ItemStack getTheItem() {
        return this.item;
    }

    @Override
    public void setTheItem(ItemStack pItem) {
        this.item= pItem;
    }

    public List<ItemStack> getRecipeAndResult(ItemStack tool){
        SingleRecipeInput input = new SingleRecipeInput(item);
        if (level.isClientSide()) {
            return null;
        }
        if(tool.is(ModTags.ChoppingBoard_TOOL)) {
            RecipeManager recipes = level.getRecipeManager();
            Optional<RecipeHolder<ChoppingBoardRecipe>> optional = recipes.getRecipeFor(
                    ModRecipes.CHOPPING_BOARD_RECIPE.get(),
                    input,
                    level
            );
            return optional
                    .map(RecipeHolder::value)
                    .map(e -> e.getResults())
                    .orElse(new ArrayList<>());
        }
        return new ArrayList<>();
    }


    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
