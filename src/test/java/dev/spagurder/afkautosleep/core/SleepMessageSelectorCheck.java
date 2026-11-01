package dev.spagurder.afkautosleep.core;

import java.util.List;

public final class SleepMessageSelectorCheck {

    private SleepMessageSelectorCheck() {
    }

    public static void main(String[] arguments) {
        List<String> messages = List.of("Good night", "Zzzzz");
        for (int attempt = 0; attempt < 100; attempt++) {
            String selected = SleepMessageSelector.select(messages, false, false).orElseThrow();
            require(messages.contains(selected), "selected message must come from the configured list");
        }
        require(
                SleepMessageSelector.select(messages, false, true).isEmpty(),
                "online-only messages must be suppressed when no other player is online"
        );
        require(
                SleepMessageSelector.select(messages, true, true).filter(messages::contains).isPresent(),
                "an online player must allow a configured message"
        );
        require(
                SleepMessageSelector.select(List.of(), true, false).isEmpty(),
                "an empty message list must not send chat"
        );
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

}