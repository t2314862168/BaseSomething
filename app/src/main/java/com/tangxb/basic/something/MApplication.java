package com.tangxb.basic.something;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tangxb.basic.something.imageloader.GlideLoaderFactory;
import com.tangxb.basic.something.imageloader.ImageLoaderFactory;
import com.tangxb.basic.something.okhttp.CacheUtils;
import com.tangxb.basic.something.okhttp.OkHttpUtils;
import com.tangxb.basic.something.util.NetworkUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Tangxb on 2017/2/14.
 */

public class MApplication extends Application {
    private ImageLoaderFactory imageLoaderFactory;
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initDebugSth();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**
     * 在{@link MDebugApplication#initDebugSth()}调试的时候使用
     */
    public void initDebugSth() {

    }

    private void init() {
        NetworkUtils.setContext(this);
        CacheUtils.setContext(this);
        initOkHttpUtils();
        initUMeng();
        initImageLoaderFactory();
        initRefWatcher();
        initBugly();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void initOkHttpUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10 * 1000L, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000L, TimeUnit.MILLISECONDS)
                .writeTimeout(60 * 1000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private void initUMeng() {
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setCatchUncaughtExceptions(true);
    }

    private void initRefWatcher() {
        mRefWatcher = LeakCanary.install(this);
    }

    private void initImageLoaderFactory() {
        imageLoaderFactory = new GlideLoaderFactory();
    }

    public ImageLoaderFactory getImageLoaderFactory() {
        return imageLoaderFactory;
    }

    public static RefWatcher getRefWatcher(Context context) {
        MApplication application = (MApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    public void registerEventBus(Object object) {
        try {
            EventBus.getDefault().register(object);
        } catch (Exception e) {
        }
    }

    public void unregisterEventBus(Object object) {
        try {
            EventBus.getDefault().unregister(object);
        } catch (Exception e) {
        }
    }

    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "62156d7612", BuildConfig.DEBUG, strategy);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
