/**
 * Copyright
 */
package fr.nelaupe.benchmark.realm;

import android.content.Context;

import org.fluttercode.datafactory.impl.DataFactory;

import fr.nelaupe.benchmark.BenchmarkExecutor;
import fr.nelaupe.benchmark.BenchmarkUtil;
import io.realm.Case;
import io.realm.Realm;

/**
 * Created with IntelliJ
 * Created by lucas
 * Date 26/03/15
 */
public class RealmBenchmarkIndexed implements BenchmarkExecutor {

    private Realm realm;

    @Override
    public void setup(Context context) {
        Realm.init(context);
        realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm transaction) {
                transaction.deleteAll();
            }
        });

    }

    @Override
    public long runInsertion(final int iteration) {
        final DataFactory dataFactory = new DataFactory();

        long start = BenchmarkUtil.getCurrentTime();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm transaction) {
                for (int i = 0; i < iteration; i++) {
                    RealmPersonIndexed person = new RealmPersonIndexed();
                    person.setId(i);
                    person.setEmail(dataFactory.getEmailAddress());
                    transaction.insert(person);
                }
            }
        });

        return BenchmarkUtil.getElapsedTime(start);
    }

    @Override
    public long runQuery(String query) {
        long start = BenchmarkUtil.getCurrentTime();
        realm.where(RealmPerson.class).contains("email", query, Case.SENSITIVE).findAll().size();
        return BenchmarkUtil.getElapsedTime(start);
    }

    @Override
    public void tearDown() {
        realm.close();
    }
}
