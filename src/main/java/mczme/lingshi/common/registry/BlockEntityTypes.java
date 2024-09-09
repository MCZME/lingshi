package mczme.lingshi.common.registry;

import mczme.lingshi.common.block.entity.ChoppingBoardBlockEntity;
import mczme.lingshi.common.block.entity.CookingPotBlockEntity;
import mczme.lingshi.common.block.entity.GlassJarBlockEntity;
import mczme.lingshi.common.block.entity.SkilletBlockEntity;
import mczme.lingshi.lingshi;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, lingshi.MODID);

    public static final Supplier<BlockEntityType<ChoppingBoardBlockEntity>> CHOPPING_BOARD_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("chopping_board_block_entity", () ->
                    BlockEntityType.Builder.of(ChoppingBoardBlockEntity::new, 
                            ModBlocks.CHOPPING_BOARD.get()).build(null));
    public static final Supplier<BlockEntityType<SkilletBlockEntity>> SKILLET_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("skillet_block_entity", () ->
                       BlockEntityType.Builder.of(SkilletBlockEntity::new,
                                ModBlocks.SKILLET.get()).build(null));
    public static final Supplier<BlockEntityType<CookingPotBlockEntity>> COOKING_POT_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("cooking_pot_block_entity", () ->
                    BlockEntityType.Builder.of(CookingPotBlockEntity::new,
                            ModBlocks.COOKING_POT.get()).build(null));
    public static final Supplier<BlockEntityType<GlassJarBlockEntity>> GLASS_JAR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("glass_jar_block_entity", () ->
                    BlockEntityType.Builder.of(GlassJarBlockEntity::new,
                            ModBlocks.GLASS_JAR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
