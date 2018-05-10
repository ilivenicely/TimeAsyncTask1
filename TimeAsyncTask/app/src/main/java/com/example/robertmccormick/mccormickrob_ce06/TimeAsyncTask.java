package com.example.robertmccormick.mccormickrob_ce06;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.Toast;

public class TimeAsyncTask extends AsyncTask<Long, Long, Void> {

    @SuppressLint("StaticFieldLeak")
    private MainActivity ctx;

    TimeAsyncTask(MainActivity ctx) {
        this.ctx = ctx;
    }

    @Override
    protected Void doInBackground(Long... time) {

        while (time[0] >= 0) {
            if(isCancelled())
                return null;
            publishProgress(time[0]);
            time[0]--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(ctx, "Timer Started", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ctx.finishTimer();
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        ctx.updateUI(values[0]);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        ctx.stopCount();
    }
}
