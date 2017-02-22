package com.tangxb.basic.something;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tangxb.basic.something.imageloader.GlideLoaderFactory;
import com.tangxb.basic.something.imageloader.ImageLoaderFactory;
import com.tangxb.basic.something.okhttp.CacheUtils;
import com.tangxb.basic.something.util.NetworkUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

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
        initUMeng();
        initImageLoaderFactory();
        initRefWatcher();
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
}
