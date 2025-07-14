package dev.spagurder.afkautosleep.core;

import dev.spagurder.afkautosleep.AfkAutoSleep;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

public class State {

    private static boolean enabled = false;
    private static BlockPos selectedBed = null;

    public static boolean isEnabled() {
        return enabled;
    }

    public static BlockPos getSelectedBed() {
        return selectedBed;
    }

    public static void tryEnable(Minecraft client) {
        BlockPos bed = BedUtils.getTargetedBed(client);
        if (bed == null) {
            Util.messageClient(client, "Must be targeting a bed!", false);
            return;
        }
        enabled = true;
        selectedBed = bed;
        logState(client);
    }

    public static void disable() { disable(null); }

    public static void disable(@Nullable Minecraft client) {
        enabled = false;
        selectedBed = null;
        logState(client);
    }

    public static void logState() { logState(null); }

    public static void logState(@Nullable Minecraft client) {
        AfkAutoSleep.LOG.info("{} {}", AfkAutoSleep.MOD_ID, enabled ? "enabled" : "disabled");
        if (client != null) {
            Util.messageClient(client, AfkAutoSleep.MOD_ID + " " + (enabled ? "enabled" : "disabled"), enabled);
        }
    }

}
