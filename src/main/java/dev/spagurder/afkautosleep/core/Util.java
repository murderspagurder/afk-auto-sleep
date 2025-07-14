package dev.spagurder.afkautosleep.core;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class Util {

    static void messageClient(Minecraft client, String message, boolean success) {
        client.gui.getChat().addMessage(
                Component.literal(message).withStyle(success ? ChatFormatting.GREEN : ChatFormatting.RED)
        );
    }

}
