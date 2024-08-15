package mczme.lingshi;

import mczme.lingshi.common.createtab.CreateTabs;
import mczme.lingshi.common.registry.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;


@Mod(lingshi.MODID)
public class lingshi
{
    public static final String MODID = "lingshi";
    private static final Logger LOGGER = LogUtils.getLogger();



    public lingshi(IEventBus modEventBus, ModContainer modContainer)
    {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        CreateTabs.register(modEventBus);
        BlockEntityTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModSerializer.register(modEventBus);
        ModMenuTypes.register(modEventBus);
    }


}
