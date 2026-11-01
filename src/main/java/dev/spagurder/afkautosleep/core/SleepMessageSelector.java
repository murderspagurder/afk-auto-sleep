package dev.spagurder.afkautosleep.core;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public final class SleepMessageSelector {

    private SleepMessageSelector() {
    }

    public static Optional<String> select(
            List<String> messages,
            boolean otherPlayersOnline,
            boolean onlyWhenOtherPlayersOnline
    ) {
        if (messages.isEmpty() || (onlyWhenOtherPlayersOnline && !otherPlayersOnline)) {
            return Optional.empty();
        }
        return Optional.of(messages.get(ThreadLocalRandom.current().nextInt(messages.size())));
    }

}