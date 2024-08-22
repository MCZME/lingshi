package mczme.lingshi.common.registry;

import mczme.lingshi.client.menu.CookingPotMenu;
import mczme.lingshi.client.menu.SkilletMenu;
import mczme.lingshi.lingshi;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, lingshi.MODID);


    public static final Supplier<MenuType<SkilletMenu>> SKILLET_MENU = MENU_TYPES.register("skillet_menu", () -> IMenuTypeExtension.create(SkilletMenu::new));
    public static final Supplier<MenuType<CookingPotMenu>> COOKING_POT_MENU = MENU_TYPES.register("cooking_pot_menu", () -> IMenuTypeExtension.create(CookingPotMenu::new));

    public static void register(IEventBus eventBus){
        MENU_TYPES.register(eventBus);
    }

}
