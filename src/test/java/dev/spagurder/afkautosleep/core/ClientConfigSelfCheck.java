package dev.spagurder.afkautosleep.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        require(
                ClientConfig.DEFAULT_LOGOUT_MESSAGE.equals(ClientConfig.logoutMessage()),
                "default logout message must be available"
        );
    }

    private static void checkSavedConfiguration(Path gameDirectory) {
        ClientConfig.save(true, "custom message");
        require(ClientConfig.logoutWhenNoBed(), "saved fallback must be live immediately");
        require(
                "custom message".equals(ClientConfig.logoutMessage()),
                "saved message must be live immediately"
        );

        ClientConfig.load(gameDirectory);
        require(ClientConfig.logoutWhenNoBed(), "saved fallback must persist");
        require(
                "custom message".equals(ClientConfig.logoutMessage()),
                "saved message must persist"
        );

        boolean savedFallback = ClientConfig.logoutWhenNoBed();
        String savedMessage = ClientConfig.logoutMessage();
        require(ClientConfig.logoutWhenNoBed() == savedFallback, "cancel must leave fallback unchanged");
        require(ClientConfig.logoutMessage().equals(savedMessage), "cancel must leave message unchanged");

        ClientConfig.save(false, " ");
        require(!ClientConfig.logoutWhenNoBed(), "disabled fallback must be saved");
        require(
                ClientConfig.DEFAULT_LOGOUT_MESSAGE.equals(ClientConfig.logoutMessage()),
                "blank message must use the default"
        );
    }

    private static void checkFallbackBranches() {
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