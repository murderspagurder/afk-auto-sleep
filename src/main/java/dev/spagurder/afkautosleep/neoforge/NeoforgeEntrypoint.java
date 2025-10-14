package dev.spagurder.afkautosleep.neoforge;

//? neoforge {
/*import dev.spagurder.afkautosleep.AfkAutoSleep;
import dev.spagurder.afkautosleep.core.EventHandler;
import dev.spagurder.afkautosleep.core.KeyMappings;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod(value = AfkAutoSleep.MOD_ID)
@EventBusSubscriber(modid = AfkAutoSleep.MOD_ID, value = Dist.CLIENT)
public class NeoforgeEntrypoint {

    public NeoforgeEntrypoint() {
        AfkAutoSleep.initClient();
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        AfkAutoSleep.LOG.info("Initializing {} Client", AfkAutoSleep.MOD_ID);
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyMappings.SELECT_BED);
    }

    @SubscribeEvent
    public static void onClientLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        EventHandler.onClientJoin();
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        EventHandler.onClientTick(Minecraft.getInstance());
    }

}
*///?}
