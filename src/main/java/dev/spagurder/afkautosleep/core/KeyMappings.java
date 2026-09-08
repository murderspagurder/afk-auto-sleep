package dev.spagurder.afkautosleep.core;

import com.mojang.blaze3d.platform.InputConstants;
import dev.spagurder.afkautosleep.AfkAutoSleep;
import net.minecraft.client.KeyMapping;
//? if >=26.2 {
import net.minecraft.resources.Identifier;
//?} else {
/*import net.minecraft.resources.ResourceLocation;
*///?}

public class KeyMappings {

    //? if >=26.2 {
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(
            AfkAutoSleep.MOD_ID, "keybinds"
    ));
    //?} else if >1.21.8 {
    /*private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath(
            AfkAutoSleep.MOD_ID, "keybinds"
    ));
    *///?} else {
    /*private static final String CATEGORY = "category." + AfkAutoSleep.MOD_ID + ".keybinds";
    *///?}

    public static final KeyMapping SELECT_BED = new KeyMapping(
            "key." + AfkAutoSleep.MOD_ID + ".selectBed",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_B,
            CATEGORY
    );

    public static final KeyMapping OPEN_SETTINGS = new KeyMapping(
            "key." + AfkAutoSleep.MOD_ID + ".openSettings",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_Y,
            CATEGORY
    );

}
