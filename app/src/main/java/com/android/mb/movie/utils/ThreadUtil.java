package com.android.mb.movie.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {

    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    private static final ExecutorService mThreadPool = Executors.newCachedThreadPool();

    public static final ExecutorService getExecutorService(){
        return mThreadPool;
    }

    public static final void runBackground(Runnable run){
        mThreadPool.submit(run);
    }

    public static final void runBackgroundDelayed(final Runnable run, long delayed){
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mThreadPool.submit(run);
            }
        }, delayed);
    }

    public static final void cancel(final Runnable run){
        sHandler.removeCallbacks(run);
    }

    public static final void runOnMainThread(Runnable run){
        sHandler.post(run);
    }
}
