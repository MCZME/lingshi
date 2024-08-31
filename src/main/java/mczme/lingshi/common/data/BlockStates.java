package mczme.lingshi.common.data;

import mczme.lingshi.common.registry.ModBlocks;
import mczme.lingshi.lingshi;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static mczme.lingshi.common.block.FoodBlock.AMOUNT;

public class BlockStates extends BlockStateProvider {


    public BlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, lingshi.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        horizontalBlock(ModBlocks.CHOPPING_BOARD.get(), "block/chopping_board");
        horizontalBlock(ModBlocks.SAUTEED_SEASONAL_VEGETABLE.get(),"block/plate","block/sauteed_seasonal_vegetable");
    }

    private void horizontalBlock(Block block, String modelLocation) {
        VariantBlockStateBuilder variantBuilder = getVariantBuilder(block);

        variantBuilder.forAllStates(state -> ConfiguredModel.builder()
                .modelFile(modelFile(modelLocation))
                .rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
                .build());
    }

    private void horizontalBlock(Block block, String modelLocation_0 ,String modelLocation_1 ) {
        VariantBlockStateBuilder variantBuilder = getVariantBuilder(block);

        variantBuilder.forAllStates(state -> {
            String modelLocation = state.getValue(AMOUNT)==1?modelLocation_1:modelLocation_0;
            return ConfiguredModel.builder()
                    .modelFile(modelFile(modelLocation))
                    .rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
                    .build();
        });
    }

    private ModelFile modelFile(String location) {
        return new ModelFile.ExistingModelFile(ResourceLocation.fromNamespaceAndPath(lingshi.MODID,location) , models().existingFileHelper);
    }
}
