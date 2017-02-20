package com.tangxb.basic.something.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tangxb.basic.something.MApplication;
import com.tangxb.basic.something.mvp.presenter.BasePresenter;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by Tangxb on 2016/12/13.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected P mvpPresenter;
    /**
     * 在<code>onCreateView</code>的时候注册,需要在<code>onDestroy</code>取消注册
     */
    private boolean isNeedOnCreateRegister = false;
    /**
     * 在<code>OnResume</code>的时候注册,需要在<code>OnPause</code>取消注册
     */
    private boolean isNeedOnResumeRegister = false;
    protected MApplication mApplication;
    protected Activity mActivity;
    protected String mClassName;
    protected Resources mResources;

    protected abstract P createPresenter();

    protected abstract int getLayoutResId();

    /**
     * 请在{@link #initData()}里面调用
     */
    protected void setNeedOnCreateRegister() {
        isNeedOnCreateRegister = true;
    }

    /**
     * 请在{@link #initData()}里面调用
     */
    protected void setNeedOnResumeRegister() {
        isNeedOnResumeRegister = true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        init();
    }

    protected void bindButterKnife() {
        ButterKnife.bind(this);
    }

    protected void init() {
        mActivity = this;
        mApplication = (MApplication) getApplication();
        mClassName = getClass().getSimpleName();
        mResources = getResources();
        bindButterKnife();
        initData();
        initListener();
        receivePassDataIfNeed(getIntent());
        if (isNeedOnCreateRegister) {
            mApplication.registerEventBus(this);
        }
    }

    protected void initData() {

    }

    protected void initListener() {

    }

    protected void receivePassDataIfNeed(Intent intent) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(mClassName);
        MobclickAgent.onResume(this);
        if (isNeedOnResumeRegister) {
            mApplication.registerEventBus(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mClassName);
        MobclickAgent.onPause(this);
        if (isNeedOnResumeRegister) {
            mApplication.registerEventBus(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
        if (isNeedOnCreateRegister) {
            mApplication.unregisterEventBus(this);
        }
    }
}
