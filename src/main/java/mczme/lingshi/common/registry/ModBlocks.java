package mczme.lingshi.common.registry;

import mczme.lingshi.common.block.RiceSeedling;
import mczme.lingshi.lingshi;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(lingshi.MODID);

    public static final Supplier<CropBlock> RICE = BLOCKS.registerBlock("rice", CropBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.PLANT)
            .noCollission()
            .randomTicks()
            .instabreak()
            .sound(SoundType.CROP)
            .pushReaction(PushReaction.DESTROY));
    public static final Supplier<RiceSeedling> RICE_SEEDING = BLOCKS.registerBlock("rice_seeding", RiceSeedling::new,
            BlockBehaviour.Properties.of());

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
