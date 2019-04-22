package com.android.mb.movie.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**SDK线程池
 * Created by cb on 2018/3/5.
 */
public class MyThreadsPool {
    private static final int DB_THREAD_COUNT = 5;
    private static final int NET_THREAD_COUNT = 10;
    final private static ExecutorService mDbExecutor;
    final private static ExecutorService mNetExecutor;

    static {
        //数据库操作比较快，线程数少一些
        mDbExecutor = Executors.newFixedThreadPool(DB_THREAD_COUNT);
        //网络请求比较慢，线程数多一些
        mNetExecutor = Executors.newFixedThreadPool(NET_THREAD_COUNT);
    }

    /**
     * @return 数据库操作用的线程池
     */
    public static ExecutorService getDbExecutor() {
        return mDbExecutor;
    }

    /**
     * @return 网络请求用的线程池
     */
    public static ExecutorService getNetExecutor() {
        return mNetExecutor;
    }
}
