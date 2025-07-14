package dev.spagurder.afkautosleep.core;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public class EventHandler {

    public static void onClientJoin() {
        State.disable();
        State.logState();
    }

    public static void onClientTick(Minecraft client) {
        while (KeyMappings.SELECT_BED.consumeClick()) {
            if (State.isEnabled()) {
                State.disable(client);
            } else {
                State.tryEnable(client);
            }
        }

        if (!State.isEnabled()) return;
        if (client.level == null || client.player == null) return;
        if (client.level.getGameTime() % 100 == 0) {
            if (BedUtils.isSleepTime(client) && !client.player.isSleeping()) {
                BlockPos bedPos = State.getSelectedBed();
                String errorMessage = BedUtils.checkBedError(client, bedPos);
                if (errorMessage != null) {
                    Util.messageClient(client, errorMessage, false);
                    State.disable(client);
                    return;
                }
                BedUtils.trySleep(client, State.getSelectedBed());
            }
        }
    }

}
