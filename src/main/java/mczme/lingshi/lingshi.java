package mczme.lingshi;

import mczme.lingshi.common.createtab.CreateTabs;
import mczme.lingshi.common.registry.ModBlocks;
import mczme.lingshi.common.registry.ModItems;
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


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public lingshi(IEventBus modEventBus, ModContainer modContainer)
    {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        CreateTabs.register(modEventBus);

    }


}
