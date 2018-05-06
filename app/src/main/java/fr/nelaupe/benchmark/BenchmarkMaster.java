/**
 * Copyright
 */
package fr.nelaupe.benchmark;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.nelaupe.benchmark.greendao.GreenDaoBenchmark;
import fr.nelaupe.benchmark.loop.LoopBenchmark;
import fr.nelaupe.benchmark.loop.LoopOptBenchmark;
import fr.nelaupe.benchmark.objectbox.ObjectBoxBenchmark;
import fr.nelaupe.benchmark.objectbox.ObjectBoxBenchmarkIndexed;
import fr.nelaupe.benchmark.realm.RealmBenchmark;
import fr.nelaupe.benchmark.realm.RealmBenchmarkIndexed;
import timber.log.Timber;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
public class BenchmarkMaster {

    private static final int ITERATIONS = 10;
    private static final int NUMBER_ENTITIES = 20000;
    private Context context;
    private ArrayList<BenchmarkResult> mBenchmarkResult = new ArrayList<>();

    public BenchmarkMaster(Context context) {
        this.context = context;
    }

    private static long sum(List<Long> data) {
        long sum = 0;

        for (int i = 0, size = data.size(); i < size; i++) {
            sum += data.get(i);
        }
        return sum;
    }

    public void run() {
        runBenchmark(new GreenDaoBenchmark());
        runBenchmark(new LoopBenchmark());
        runBenchmark(new LoopOptBenchmark());
        runBenchmark(new RealmBenchmark());
        runBenchmark(new ObjectBoxBenchmark());
        runBenchmark(new RealmBenchmarkIndexed());
        runBenchmark(new ObjectBoxBenchmarkIndexed());
        printBenchmarkResult();
    }

    private void printBenchmarkResult() {
        Collections.sort(mBenchmarkResult, new BenchmarkResultComparator());

        long totalElapsedTime = 0;
        long elapsedTime;

        for (int i = 0, length = mBenchmarkResult.size(); i < length; i++) {
            BenchmarkResult benchmarkResult = mBenchmarkResult.get(i);

            elapsedTime = benchmarkResult.getElapsedTime();
            totalElapsedTime += elapsedTime;

            Timber.wtf("%s", benchmarkResult);
        }
        Timber.wtf("Total Elapsed Time: %s(ms)", totalElapsedTime);
    }

    public void runBenchmark(BenchmarkExecutor benchmark) {
        List<Long> resultsInsertion = new ArrayList<>(ITERATIONS);

        for (int i = 0; i < ITERATIONS; i++) {
            benchmark.setup(context);
            resultsInsertion.add(benchmark.runInsertion(NUMBER_ENTITIES));
            benchmark.tearDown();
        }

        List<Long> resultsSearch = new ArrayList<>();

        benchmark.setup(context);
        benchmark.runInsertion(NUMBER_ENTITIES);
        for (int i = 0; i < ITERATIONS; i++) {
            resultsSearch.add(benchmark.runQuery("@gma"));
        }
        benchmark.tearDown();

        BenchmarkResult benchmarkResultInsert = new BenchmarkResult(
                benchmark.getClass().getSimpleName(),
                DatabaseOperation.INSERT,
                BenchmarkMaster.sum(resultsInsertion)
        );

        BenchmarkResult benchmarkResultSearch = new BenchmarkResult(
                benchmark.getClass().getSimpleName(),
                DatabaseOperation.SEARCH,
                BenchmarkMaster.sum(resultsSearch)
        );

        mBenchmarkResult.add(benchmarkResultInsert);
        mBenchmarkResult.add(benchmarkResultSearch);
    }

    private static class BenchmarkResultComparator implements Comparator<BenchmarkResult> {

        private static final int RESULT_EQUALS = 0;

        @Override
        public int compare(BenchmarkResult a, BenchmarkResult b) {
            int comparisonResult = a.getOperation().toLowerCase().compareTo(b.getOperation().toLowerCase());

            if (comparisonResult == 0) {
                comparisonResult = (int) (a.getElapsedTime() - b.getElapsedTime());
            }

            return comparisonResult == RESULT_EQUALS
                    ? a.getClassName().toLowerCase().compareTo(b.getClassName().toLowerCase())
                    : comparisonResult;
        }
    }
}
