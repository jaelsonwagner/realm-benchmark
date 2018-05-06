package fr.nelaupe.benchmark;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();
    }

    private void run() {
        new BenchmarkAsyncTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private static class BenchmarkAsyncTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<MainActivity> activityReference;

        BenchmarkAsyncTask(MainActivity activityReference) {
            this.activityReference = new WeakReference<>(activityReference);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Context context = activityReference.get();
            if (context != null) {
                new BenchmarkMaster(context).run();
            }
            return null;
        }
    }
}
