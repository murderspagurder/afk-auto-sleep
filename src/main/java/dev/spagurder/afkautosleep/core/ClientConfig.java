package dev.spagurder.afkautosleep.core;

import dev.spagurder.afkautosleep.AfkAutoSleep;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public final class ClientConfig {

    public static final String DEFAULT_LOGOUT_MESSAGE =
            "no bed available, logging out, so you will be able to skip the night.";
        public static final List<String> DEFAULT_SLEEP_MESSAGES = List.of(
            "Sleeping like a log...",
            "Zzzzz",
            "Chrrr"
        );

    private static final String FILE_NAME = "afkautosleep.properties";
    private static final String LOGOUT_WHEN_NO_BED = "logoutWhenNoBed";
    private static final String LOGOUT_MESSAGE = "logoutMessage";
        private static final String ONLY_WHEN_OTHER_PLAYERS_ONLINE = "onlyWhenOtherPlayersOnline";
        private static final String SLEEP_MESSAGES = "sleepMessages";

    private static Path configFile;
    private static boolean logoutWhenNoBed;
    private static String logoutMessage = DEFAULT_LOGOUT_MESSAGE;
        private static boolean onlyWhenOtherPlayersOnline;
        private static List<String> sleepMessages = DEFAULT_SLEEP_MESSAGES;

    private ClientConfig() {
    }

    public static void load(Path gameDirectory) {
        configFile = gameDirectory.resolve("config").resolve(FILE_NAME);
        logoutWhenNoBed = false;
        logoutMessage = DEFAULT_LOGOUT_MESSAGE;
        onlyWhenOtherPlayersOnline = false;
        sleepMessages = DEFAULT_SLEEP_MESSAGES;

        if (!Files.exists(configFile)) {
            persist();
            return;
        }

        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(configFile)) {
            properties.load(reader);
            logoutWhenNoBed = readBoolean(properties, LOGOUT_WHEN_NO_BED);
            logoutMessage = normalizeMessage(properties.getProperty(LOGOUT_MESSAGE));
            onlyWhenOtherPlayersOnline = readBoolean(properties, ONLY_WHEN_OTHER_PLAYERS_ONLINE);
            sleepMessages = readSleepMessages(properties);
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

    public static boolean onlyWhenOtherPlayersOnline() {
        return onlyWhenOtherPlayersOnline;
    }

    public static List<String> sleepMessages() {
        return sleepMessages;
    }

    public static List<String> parseSleepMessagesText(String messageText) {
        if (messageText == null || messageText.isBlank()) {
            return List.of();
        }
        return normalizeSleepMessages(Arrays.asList(messageText.split("\\|", -1)));
    }

    public static void save(
            boolean enabled,
            String message,
            List<String> messages,
            boolean sendOnlyWhenOtherPlayersOnline
    ) {
        logoutWhenNoBed = enabled;
        logoutMessage = normalizeMessage(message);
        sleepMessages = normalizeSleepMessages(messages);
        onlyWhenOtherPlayersOnline = sendOnlyWhenOtherPlayersOnline;
        persist();
    }

    private static boolean readBoolean(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            return false;
        }
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        AfkAutoSleep.LOG.warn("Invalid value for {}: {}; using false", key, value);
        return false;
    }

    private static List<String> readSleepMessages(Properties properties) {
        String value = properties.getProperty(SLEEP_MESSAGES);
        if (value == null) {
            return DEFAULT_SLEEP_MESSAGES;
        }
        return normalizeSleepMessages(Arrays.asList(value.split("\\n", -1)));
    }

    private static String normalizeMessage(String message) {
        if (message == null || message.isBlank()) {
            return DEFAULT_LOGOUT_MESSAGE;
        }
        return message;
    }

    private static List<String> normalizeSleepMessages(List<String> messages) {
        if (messages == null) {
            return List.of();
        }
        return messages.stream()
                .filter(message -> message != null)
                .map(String::trim)
                .filter(message -> !message.isEmpty())
                .toList();
    }

    private static void persist() {
        if (configFile == null) {
            return;
        }

        Properties properties = new Properties();
        properties.setProperty(LOGOUT_WHEN_NO_BED, Boolean.toString(logoutWhenNoBed));
        properties.setProperty(LOGOUT_MESSAGE, logoutMessage);
        properties.setProperty(ONLY_WHEN_OTHER_PLAYERS_ONLINE, Boolean.toString(onlyWhenOtherPlayersOnline));
        properties.setProperty(SLEEP_MESSAGES, String.join("\n", sleepMessages));
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