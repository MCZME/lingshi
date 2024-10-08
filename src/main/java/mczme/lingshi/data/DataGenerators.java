package mczme.lingshi.data;

import mczme.lingshi.data.lang.ChineseLanguageProvider;
import mczme.lingshi.data.loot.BlockLoot;
import mczme.lingshi.data.recipe.Recipes;
import mczme.lingshi.data.tag.BlockTags;
import mczme.lingshi.data.tag.ItemTags;
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

//        block loot datagen
        event.getGenerator().addProvider(
                event.includeServer(),
                new LootTableProvider(output, Set.of(),
                        List.of(new LootTableProvider.SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)),
                        lookupProvider)
        );

//        lang datagen
        event.getGenerator().addProvider(
                event.includeClient(),
                new ChineseLanguageProvider(output)
        );

//        state datagen
        event.getGenerator().addProvider(
                event.includeClient(),
                new BlockStates(output, existingFileHelper)
        );

//                Item Model datagen
        event.getGenerator().addProvider(
                event.includeClient(),
                new ItemModels(output, existingFileHelper)
        );

//        recipe datagen
        event.getGenerator().addProvider(
                event.includeServer(),
                new Recipes(output, lookupProvider)
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

//        Cooking Food datagen
        event.getGenerator().addProvider(
                event.includeServer(),
                new CookingFoodDataGen(output, lookupProvider)
        );
    }
}
