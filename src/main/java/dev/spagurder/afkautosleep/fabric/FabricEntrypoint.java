package dev.spagurder.afkautosleep.fabric;

//? fabric {
import dev.spagurder.afkautosleep.AfkAutoSleep;
import dev.spagurder.afkautosleep.core.EventHandler;
import dev.spagurder.afkautosleep.core.KeyMappings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//? if >=26.2 {
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
//?} else {
/*import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
*///?}
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class FabricEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AfkAutoSleep.initClient();

        //? if >=26.2 {
        KeyMappingHelper.registerKeyMapping(KeyMappings.SELECT_BED);
        KeyMappingHelper.registerKeyMapping(KeyMappings.OPEN_SETTINGS);
        //?} else {
        /*KeyBindingHelper.registerKeyBinding(KeyMappings.SELECT_BED);
        *///?}
        //? if <26.2 {
        /*KeyBindingHelper.registerKeyBinding(KeyMappings.OPEN_SETTINGS);
        *///?}

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            EventHandler.onClientJoin();
        });

        ClientTickEvents.END_CLIENT_TICK.register(EventHandler::onClientTick);
    }

}
//?}
