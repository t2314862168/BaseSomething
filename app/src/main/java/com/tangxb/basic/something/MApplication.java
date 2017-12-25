package com.tangxb.basic.something;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tangxb.basic.something.bean.UserLoginResultBean;
import com.tangxb.basic.something.imageloader.GlideLoaderFactory;
import com.tangxb.basic.something.imageloader.ImageLoaderFactory;
import com.tangxb.basic.something.mvp.ui.activity.BaseActivity;
import com.tangxb.basic.something.okhttp.CacheUtils;
import com.tangxb.basic.something.okhttp.OkHttpUtils;
import com.tangxb.basic.something.util.NetworkUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Tangxb on 2017/2/14.
 */

public class MApplication extends Application {
    private ImageLoaderFactory imageLoaderFactory;
    private RefWatcher mRefWatcher;
    private UserLoginResultBean mUserLoginResultBean;
    private Stack<WeakReference<BaseActivity>> mActivityStack;

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
        initActivityStack();
    }

    private void initActivityStack() {
        mActivityStack = new Stack<>();
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

    public UserLoginResultBean getUserLoginResultBean() {
        return mUserLoginResultBean;
    }

    public void setUserLoginResultBean(UserLoginResultBean mUserLoginResultBean) {
        this.mUserLoginResultBean = mUserLoginResultBean;
    }

    /**
     * 添加Activity到ActivityStack里面
     *
     * @param activity
     */
    public void pushActivity(BaseActivity activity) {
        mActivityStack.push(new WeakReference<>(activity));
    }

    /**
     * 移除ActivityStack栈顶元素
     */
    public void removeTopActivity() {
        mActivityStack.pop();
    }

    /**
     * 移除自身之外的其他Activity并且finish
     *
     * @param baseActivity
     */
    public void finishOtherActivity(BaseActivity baseActivity) {
        List<WeakReference<BaseActivity>> weakReferenceList = new ArrayList<>();
        for (WeakReference<BaseActivity> reference : mActivityStack) {
            if (reference != null && reference.get() != null) {
                if (!reference.get().getClass().getName().equals(baseActivity.getClass().getName())) {
                    weakReferenceList.add(reference);
                }
            }
        }
        for (WeakReference<BaseActivity> reference : weakReferenceList) {
            reference.get().finish();
        }
    }

    public void removeActivity(BaseActivity baseActivity) {
        WeakReference<BaseActivity> weak = null;
        for (WeakReference<BaseActivity> reference : mActivityStack) {
            if (reference != null && reference.get() != null) {
                if (reference.get().getClass().getName().equals(baseActivity.getClass().getName())) {
                    weak = reference;
                    break;
                }
            }
        }
        if (weak != null) {
            mActivityStack.remove(weak);
        }
    }

    public void finishActivity(Class<?> cls) {
        WeakReference<BaseActivity> weak = null;
        for (WeakReference<BaseActivity> reference : mActivityStack) {
            if (reference != null && reference.get() != null) {
                if (reference.get().getClass().getName().equals(cls.getName())) {
                    weak = reference;
                    break;
                }
            }
        }
        if (weak != null) {
            weak.get().finish();
        }
    }

    public void removeActivity(Class<?> cls) {
        WeakReference<BaseActivity> weak = null;
        for (WeakReference<BaseActivity> reference : mActivityStack) {
            if (reference != null && reference.get() != null) {
                if (reference.get().getClass().getName().equals(cls.getName())) {
                    weak = reference;
                    break;
                }
            }
        }
        if (weak != null) {
            mActivityStack.remove(weak);
        }
    }

    public void clearActivity() {
        Stack<WeakReference<BaseActivity>> tempStack = new Stack<>();
        tempStack.addAll(mActivityStack);
        for (WeakReference<BaseActivity> reference : tempStack) {
            if (reference != null && reference.get() != null) {
                reference.get().finish();
            }
        }
        mActivityStack.clear();
    }

    public BaseActivity getTopActivity() {
        WeakReference<BaseActivity> weak = null;
        try {
            weak = mActivityStack.peek();
        } catch (Exception e) {
        }
        if (weak != null) {
            return weak.get();
        }
        return null;
    }
}
