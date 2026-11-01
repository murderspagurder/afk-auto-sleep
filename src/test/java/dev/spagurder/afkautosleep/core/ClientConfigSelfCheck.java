package dev.spagurder.afkautosleep.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class ClientConfigSelfCheck {

    private ClientConfigSelfCheck() {
    }

    public static void main(String[] arguments) throws IOException {
        Path gameDirectory = Files.createTempDirectory("afkautosleep-config-check");
        try {
            checkDefaultConfiguration(gameDirectory);
            checkSavedConfiguration(gameDirectory);
            checkFallbackBranches();
        } finally {
            deleteConfiguration(gameDirectory);
        }
    }

    private static void checkDefaultConfiguration(Path gameDirectory) {
        ClientConfig.load(gameDirectory);
        require(!ClientConfig.logoutWhenNoBed(), "fallback must be disabled by default");
        require(!ClientConfig.onlyWhenOtherPlayersOnline(), "online-only messages must be disabled by default");
        require(
                ClientConfig.DEFAULT_LOGOUT_MESSAGE.equals(ClientConfig.logoutMessage()),
                "default logout message must be available"
        );
        require(
            ClientConfig.DEFAULT_SLEEP_MESSAGES.equals(ClientConfig.sleepMessages()),
            "default sleep messages must be available"
        );
    }

    private static void checkSavedConfiguration(Path gameDirectory) {
        List<String> messages = List.of("Good night", "Zzzzz");
        ClientConfig.save(true, "custom message", messages, true);
        require(ClientConfig.logoutWhenNoBed(), "saved fallback must be live immediately");
        require(ClientConfig.onlyWhenOtherPlayersOnline(), "online-only option must be saved live");
        require(
                "custom message".equals(ClientConfig.logoutMessage()),
                "saved message must be live immediately"
        );
        require(messages.equals(ClientConfig.sleepMessages()), "sleep messages must be saved live");

        ClientConfig.load(gameDirectory);
        require(ClientConfig.logoutWhenNoBed(), "saved fallback must persist");
        require(ClientConfig.onlyWhenOtherPlayersOnline(), "online-only option must persist");
        require(
                "custom message".equals(ClientConfig.logoutMessage()),
                "saved message must persist"
        );
        require(messages.equals(ClientConfig.sleepMessages()), "sleep messages must persist");

        boolean savedFallback = ClientConfig.logoutWhenNoBed();
        String savedMessage = ClientConfig.logoutMessage();
        require(ClientConfig.logoutWhenNoBed() == savedFallback, "cancel must leave fallback unchanged");
        require(ClientConfig.logoutMessage().equals(savedMessage), "cancel must leave message unchanged");

        ClientConfig.save(false, " ", List.of(), false);
        require(!ClientConfig.logoutWhenNoBed(), "disabled fallback must be saved");
        require(!ClientConfig.onlyWhenOtherPlayersOnline(), "online-only option must be disabled");
        require(
                ClientConfig.DEFAULT_LOGOUT_MESSAGE.equals(ClientConfig.logoutMessage()),
                "blank message must use the default"
        );
        require(ClientConfig.sleepMessages().isEmpty(), "an empty sleep message list must disable chat messages");
    }

    private static void checkFallbackBranches() {
        checkSleepMessageSelection();
        require(
                SleepFallbackPolicy.shouldDisconnect(BedUtils.SleepAttemptResult.OUT_OF_RANGE, true, true),
                "enabled multiplayer out-of-range fallback must disconnect"
        );
        require(
                !SleepFallbackPolicy.shouldDisconnect(BedUtils.SleepAttemptResult.OUT_OF_RANGE, false, true),
                "disabled fallback must not disconnect"
        );
        require(
                !SleepFallbackPolicy.shouldDisconnect(BedUtils.SleepAttemptResult.OUT_OF_RANGE, true, false),
                "solo fallback must not disconnect"
        );
        require(
                !SleepFallbackPolicy.shouldDisconnect(BedUtils.SleepAttemptResult.ATTEMPTED, true, true),
                "successful sleep must not disconnect"
        );
    }

        private static void checkSleepMessageSelection() {
        List<String> messages = List.of("Good night", "Zzzzz");
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

    private static void deleteConfiguration(Path gameDirectory) throws IOException {
        Path configDirectory = gameDirectory.resolve("config");
        Files.deleteIfExists(configDirectory.resolve("afkautosleep.properties"));
        Files.deleteIfExists(configDirectory);
        Files.deleteIfExists(gameDirectory);
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

}