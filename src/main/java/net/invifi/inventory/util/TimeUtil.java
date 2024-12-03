package net.invifi.inventory.util;

import java.util.concurrent.TimeUnit;

public final class TimeUtil {

    private TimeUtil() {
    }

    public static int toTicks(long time, TimeUnit unit) {
        return time == 0L ? 0 : (int) (unit.toMillis(time) / 50L);

    }

}