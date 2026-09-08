package dev.spagurder.afkautosleep.core;

import dev.spagurder.afkautosleep.AfkAutoSleep;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ClientConfig {

    public static final String DEFAULT_LOGOUT_MESSAGE =
            "no bed available, logging out, so you will be able to skip the night.";

    private static final String FILE_NAME = "afkautosleep.properties";
    private static final String LOGOUT_WHEN_NO_BED = "logoutWhenNoBed";
    private static final String LOGOUT_MESSAGE = "logoutMessage";

    private static Path configFile;
    private static boolean logoutWhenNoBed;
    private static String logoutMessage = DEFAULT_LOGOUT_MESSAGE;

    private ClientConfig() {
    }

    public static void load(Path gameDirectory) {
        configFile = gameDirectory.resolve("config").resolve(FILE_NAME);
        logoutWhenNoBed = false;
        logoutMessage = DEFAULT_LOGOUT_MESSAGE;

        if (!Files.exists(configFile)) {
            persist();
            return;
        }

        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(configFile)) {
            properties.load(reader);
            logoutWhenNoBed = readBoolean(properties);
            logoutMessage = normalizeMessage(properties.getProperty(LOGOUT_MESSAGE));
        } catch (IOException exception) {
            AfkAutoSleep.LOG.warn("Could not load client configuration", exception);
        }
    }

    public static boolean logoutWhenNoBed() {
        return logoutWhenNoBed;
    }

    public static String logoutMessage() {
        return logoutMessage;
    }

    public static void save(boolean enabled, String message) {
        logoutWhenNoBed = enabled;
        logoutMessage = normalizeMessage(message);
        persist();
    }

    private static boolean readBoolean(Properties properties) {
        String value = properties.getProperty(LOGOUT_WHEN_NO_BED);
        if (value == null) {
            return false;
        }
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        AfkAutoSleep.LOG.warn("Invalid value for {}: {}; using false", LOGOUT_WHEN_NO_BED, value);
        return false;
    }

    private static String normalizeMessage(String message) {
        if (message == null || message.isBlank()) {
            return DEFAULT_LOGOUT_MESSAGE;
        }
        return message;
    }

    private static void persist() {
        if (configFile == null) {
            return;
        }

        Properties properties = new Properties();
        properties.setProperty(LOGOUT_WHEN_NO_BED, Boolean.toString(logoutWhenNoBed));
        properties.setProperty(LOGOUT_MESSAGE, logoutMessage);
        try {
            Files.createDirectories(configFile.getParent());
            try (Writer writer = Files.newBufferedWriter(configFile)) {
                properties.store(writer, "AFK Auto Sleep client configuration");
            }
        } catch (IOException exception) {
            AfkAutoSleep.LOG.warn("Could not save client configuration", exception);
        }
    }

}