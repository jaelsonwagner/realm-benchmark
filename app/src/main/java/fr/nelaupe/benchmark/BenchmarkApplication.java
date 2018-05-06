package fr.nelaupe.benchmark;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by jaelson on 05/05/18.
 */

public class BenchmarkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
