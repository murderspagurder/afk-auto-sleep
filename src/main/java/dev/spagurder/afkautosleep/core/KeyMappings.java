package dev.spagurder.afkautosleep.core;

import com.mojang.blaze3d.platform.InputConstants;
import dev.spagurder.afkautosleep.AfkAutoSleep;
import net.minecraft.client.KeyMapping;

public class KeyMappings {

    public static final KeyMapping SELECT_BED = new KeyMapping(
            "key." + AfkAutoSleep.MOD_ID + ".selectBed",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_B,
            "category." + AfkAutoSleep.MOD_ID + ".keybinds"
    );

}
