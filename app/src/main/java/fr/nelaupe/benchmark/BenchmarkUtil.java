package fr.nelaupe.benchmark;

/**
 * Created by jaelson on 05/05/18.
 */

public class BenchmarkUtil {

    private BenchmarkUtil() {
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static long getElapsedTime(final long startTime) {
        return getCurrentTime() - startTime;
    }
}
