package dev.spagurder.afkautosleep.core;

import com.mojang.blaze3d.platform.InputConstants;
import dev.spagurder.afkautosleep.AfkAutoSleep;
import net.minecraft.client.KeyMapping;
//? >=1.21.11 {
import net.minecraft.resources.Identifier;
//?} else {
//import net.minecraft.resources.ResourceLocation;
//?}

public class KeyMappings {

    public static final KeyMapping SELECT_BED = new KeyMapping(
            "key." + AfkAutoSleep.MOD_ID + ".selectBed",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_B,
            //? >1.21.8 {
            KeyMapping.Category.register(
                    /*? >=1.21.11 { */
                    Identifier
                    /*? } else { */
                    /*ResourceLocation*/
                    /*? } */
                            .fromNamespaceAndPath(
                    AfkAutoSleep.MOD_ID, "keybinds"
            ))
            //?} else {
            /*"category." + AfkAutoSleep.MOD_ID + ".keybinds"*/
            //?}
    );

}
