package dev.spagurder.afkautosleep.core;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class Util {

    static void messageClient(Minecraft client, String message, boolean success) {
    //? if >=26.2 {
    client.gui.hud.getChat().addClientSystemMessage(
    //?} else {
    /*client.gui.getChat().addMessage(
    *///?}
                Component.literal(message).withStyle(success ? ChatFormatting.GREEN : ChatFormatting.RED)
        );
    }

    static void sendChat(Minecraft client, String message) {
        if (client.player == null) return;
        client.player.connection.sendChat(message);
    }

}
