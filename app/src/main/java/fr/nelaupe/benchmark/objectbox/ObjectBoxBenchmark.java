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

public class ObjectBoxBenchmark implements BenchmarkExecutor {

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
                    Box<PersonObjectBox> box = boxStore.boxFor(PersonObjectBox.class);
                    PersonObjectBox person = new PersonObjectBox();
                    person.setEmail(dataFactory.getEmailAddress());
                    box.put(person);
                }
            }
        });
        return BenchmarkUtil.getElapsedTime(start);
    }

    @Override
    public long runQuery(String query) {
        long start = BenchmarkUtil.getCurrentTime();
        Box<PersonObjectBox> box = boxStore.boxFor(PersonObjectBox.class);
        box.query().contains(PersonObjectBox_.email, query).build().count();
        return BenchmarkUtil.getElapsedTime(start);
    }

    @Override
    public void tearDown() {
        boxStore.close();
        boxStore.deleteAllFiles();
    }

}
