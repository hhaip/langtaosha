package cn.lts.common.util;

import java.time.Instant;

/**
 * 计算剩余天数
 * author: huanghaiping
 * created on 16-7-13.
 */
public class DateRemain {
    public static long daysBetween(Instant end, Instant now) {
        long todayMs = now.toEpochMilli();
        long returnMs = end.toEpochMilli();
        long intervalMs = returnMs - todayMs;
        return intervalMs;
    }

    private static int millisecondsToDays(long intervalMs) {
        return (int) (intervalMs / (1000 * 86400));
    }

    private static int millisecondsToHours(long intervalMs) {
        return (int) (intervalMs / (1000 * 3600));
    }

    public static int remainDays(long intervalMs) {
        return millisecondsToDays(intervalMs);
    }

    public static int remainHours(long intervalMs) {
        return (int) (intervalMs / (1000 * 3600));
    }

    public static int remainMinutes(long intervalMs) {
        int hours = millisecondsToHours(intervalMs);
        return (int) (intervalMs / (1000 * 60)) - (hours * 60);
    }
}