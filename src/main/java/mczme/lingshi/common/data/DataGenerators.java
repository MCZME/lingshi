package mczme.lingshi.common.data;

import mczme.lingshi.common.data.lang.ChineseLanguageProvider;
import mczme.lingshi.common.data.loot.BlockLoot;
import mczme.lingshi.common.data.recipe.ChoppingBoardRecipeDatagen;
import mczme.lingshi.common.data.tag.BlockTags;
import mczme.lingshi.common.data.tag.ItemTags;
import mczme.lingshi.lingshi;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = lingshi.MODID)
public class DataGenerators {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

//        block loot
        event.getGenerator().addProvider(
                event.includeServer(),
                new LootTableProvider(output, Set.of(),
                        List.of(new LootTableProvider.SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)),
                        lookupProvider)
        );

//        lang datagen
        event.getGenerator().addProvider(
                event.includeServer(),
                new ChineseLanguageProvider(output)
        );

//        state datagen
        event.getGenerator().addProvider(
                event.includeServer(),
                new BlockStates(output, existingFileHelper)
        );

//        chopping_board recipe datagen
        event.getGenerator().addProvider(
                event.includeServer(),
                new ChoppingBoardRecipeDatagen(output, lookupProvider)
        );

//        block tag datagen
        BlockTags pBlockTags = new BlockTags(output, lookupProvider, existingFileHelper);
        event.getGenerator().addProvider(
                event.includeServer(),
                pBlockTags
        );

//        Item tag datagen
        event.getGenerator().addProvider(
                event.includeServer(),
                new ItemTags(output, lookupProvider, pBlockTags.contentsGetter(), existingFileHelper)
        );
    }
}
