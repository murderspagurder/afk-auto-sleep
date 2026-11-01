package dev.spagurder.afkautosleep.core;

public final class SleepFallbackPolicy {

    private SleepFallbackPolicy() {
    }

    public static boolean shouldDisconnect(
            BedUtils.SleepAttemptResult result,
            boolean logoutWhenNoBed,
            boolean otherPlayerOnline
    ) {
        return result == BedUtils.SleepAttemptResult.OUT_OF_RANGE
                && logoutWhenNoBed
                && otherPlayerOnline;
    }

}