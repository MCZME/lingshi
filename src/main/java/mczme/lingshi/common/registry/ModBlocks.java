package mczme.lingshi.common.registry;

import mczme.lingshi.common.block.*;
import mczme.lingshi.common.fluid.OilBlock;
import mczme.lingshi.lingshi;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(lingshi.MODID);

    public static final Supplier<CropBlock> RICE = BLOCKS.registerBlock("rice", RiceBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY));
    public static final Supplier<RiceSeedlingBlock> RICE_SEEDLING = BLOCKS.registerBlock("rice_seedling", RiceSeedlingBlock::new,
            BlockBehaviour.Properties.of().noCollission().mapColor(MapColor.PLANT).instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).randomTicks());
    public static final Supplier<RiceSeedlingTopBlock> RICE_SEEDING_TOP = BLOCKS.registerBlock("rice_seedling_top", RiceSeedlingTopBlock::new,
            BlockBehaviour.Properties.of().noCollission().mapColor(MapColor.PLANT).instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).randomTicks());

    public static final Supplier<TeaTreeBlock> TEA_TREE = BLOCKS.registerBlock("tea_tree", TeaTreeBlock::new,
            BlockBehaviour.Properties.of().randomTicks().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY));
    public static final Supplier<TeaBlock> TEA_LEAF = BLOCKS.registerBlock("tea_leaf", TeaBlock::new,
            BlockBehaviour.Properties.of().randomTicks().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY));

    public static final Supplier<SkilletBlock> SKILLET = BLOCKS.registerBlock("skillet", SkilletBlock::new,
            BlockBehaviour.Properties.of());
    public static final Supplier<ChoppingBoardBlock> CHOPPING_BOARD = BLOCKS.registerBlock("chopping_board", ChoppingBoardBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final Supplier<LiquidBlock> OIL_LIQUID_BLOCK = BLOCKS.register("oil_liquid_block",()->new OilBlock(ModFluids.OIL_SOURCE.get(),BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
