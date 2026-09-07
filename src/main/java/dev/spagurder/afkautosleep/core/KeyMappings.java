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

    public static final KeyMapping SELECT_BED = new KeyMapping(
            "key." + AfkAutoSleep.MOD_ID + ".selectBed",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_B,
            //? if >=26.2 {
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath(
                    AfkAutoSleep.MOD_ID, "keybinds"
            ))
            //?} else if >1.21.8 {
            /*KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath(
                    AfkAutoSleep.MOD_ID, "keybinds"
            ))
            *///?} else {
            /*"category." + AfkAutoSleep.MOD_ID + ".keybinds"
            *///?}
    );

}
