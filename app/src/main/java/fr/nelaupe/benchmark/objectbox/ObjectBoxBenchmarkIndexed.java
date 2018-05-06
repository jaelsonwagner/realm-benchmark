package fr.nelaupe.benchmark.objectbox;

import android.content.Context;

import org.fluttercode.datafactory.impl.DataFactory;

import fr.nelaupe.benchmark.BenchmarkExecutor;
import fr.nelaupe.benchmark.BenchmarkUtil;
import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */

public class ObjectBoxBenchmarkIndexed implements BenchmarkExecutor {

    private BoxStore boxStore;

    @Override
    public void setup(Context context) {
        boxStore = MyObjectBox.builder().androidContext(context).build();
    }

    @Override
    public long runInsertion(final int iteration) {
        final DataFactory dataFactory = new DataFactory();

        long start = BenchmarkUtil.getCurrentTime();

        boxStore.runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < iteration; i++) {
                    Box<PersonObjectBoxIndexed> box = boxStore.boxFor(PersonObjectBoxIndexed.class);
                    PersonObjectBoxIndexed person = new PersonObjectBoxIndexed();
                    person.setEmail(dataFactory.getEmailAddress());
                    box.put(person);
                }
            }
        });
        return BenchmarkUtil.getElapsedTime(start);
    }

    @Override
    public long runQuery(final String query) {
        long start = BenchmarkUtil.getCurrentTime();
        Box<PersonObjectBoxIndexed> box = boxStore.boxFor(PersonObjectBoxIndexed.class);
        box.query().contains(PersonObjectBoxIndexed_.email, query).build().count();
        return BenchmarkUtil.getElapsedTime(start);
    }

    @Override
    public void tearDown() {
        boxStore.close();
        boxStore.deleteAllFiles();
    }

}
