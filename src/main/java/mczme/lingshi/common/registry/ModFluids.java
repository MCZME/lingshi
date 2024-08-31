package mczme.lingshi.common.registry;

import mczme.lingshi.common.fluid.ModFluidType;
import mczme.lingshi.common.fluid.OilFluid;
import mczme.lingshi.lingshi;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, lingshi.MODID);

    public static final Supplier<FluidType> MOD_FLUID_TYPE = FLUID_TYPES.register("mod_fluid_type", () -> new ModFluidType(
            FluidType.Properties.create().descriptionId("block.lingshi.oil")
                    .fallDistanceModifier(0F)
                    .canExtinguish(true)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                    .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
                    .canHydrate(true)
                    .addDripstoneDripping(PointedDripstoneBlock.WATER_TRANSFER_PROBABILITY_PER_RANDOM_TICK, ParticleTypes.DRIPPING_DRIPSTONE_WATER, Blocks.WATER_CAULDRON, SoundEvents.POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON)));

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, lingshi.MODID);

    public static final Supplier<FlowingFluid> OIL_SOURCE = FLUIDS.register("oil_source", ()-> new OilFluid.Source(ModFluids.properties));
    public static final Supplier<FlowingFluid> OIL_FLOWING = FLUIDS.register("oil_flowing",()-> new OilFluid.Flowing(ModFluids.properties));
    public static final BaseFlowingFluid.Properties properties = new BaseFlowingFluid.Properties(MOD_FLUID_TYPE,ModFluids.OIL_SOURCE,ModFluids.OIL_FLOWING)
            .bucket(ModItems.OIL_BUCKET).slopeFindDistance(2).levelDecreasePerBlock(2).block(ModBlocks.OIL_LIQUID_BLOCK);

    public static void register(IEventBus modEventBus) {
        FLUID_TYPES.register(modEventBus);
        FLUIDS.register(modEventBus);
    }
}
