package fr.nelaupe.benchmark;

/**
 * Created by jaelson on 04/05/18.
 */

public class BenchmarkResult {
    private long elapsedTime;
    private String className;
    @DatabaseOperation
    private String operation;

    public BenchmarkResult(String className, @DatabaseOperation String operation, long elapsedTime) {
        this.className = className;
        this.operation = operation;
        this.elapsedTime = elapsedTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public String getClassName() {
        return className;
    }

    public String getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s", className, operation, elapsedTime);
    }
}


