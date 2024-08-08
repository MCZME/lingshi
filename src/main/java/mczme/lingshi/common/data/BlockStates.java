package mczme.lingshi.common.data;

import mczme.lingshi.common.registry.ModBlocks;
import mczme.lingshi.lingshi;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {


    public BlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, lingshi.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        VariantBlockStateBuilder variantBuilder = getVariantBuilder(ModBlocks.CHOPPING_BOARD.get());

        variantBuilder.forAllStates(state -> ConfiguredModel.builder()
                .modelFile(modelFile("block/chopping_board"))
                .rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
                .build());
    }

    private ModelFile modelFile(String location) {
        return new ModelFile.ExistingModelFile(ResourceLocation.fromNamespaceAndPath(lingshi.MODID,location) , models().existingFileHelper);
    }
}
