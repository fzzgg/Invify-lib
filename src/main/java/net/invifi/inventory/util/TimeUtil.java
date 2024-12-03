package net.invifi.inventory.util;

import java.util.concurrent.TimeUnit;

public final class TimeUtil {

    private TimeUtil() {
    }

    public static int toTicks(long time, TimeUnit unit) {
        if (time == 0L) {
            return 0;
        }

        return (int) (unit.toMillis(time) / 50L);
    }

}