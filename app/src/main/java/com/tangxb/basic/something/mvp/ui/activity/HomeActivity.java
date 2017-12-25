package com.tangxb.basic.something.mvp.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tangxb.basic.something.R;
import com.tangxb.basic.something.mvp.presenter.BasePresenter;
import com.tangxb.basic.something.mvp.presenter.HomeActivityPresenter;
import com.tangxb.basic.something.mvp.view.HomeActivityView;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class HomeActivity extends BaseActivity implements HomeActivityView {
    private HomeActivityPresenter presenter;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    HomeActivityFragmentAdapter homeActivityFragmentAdapter;

    @Override
    protected BasePresenter createPresenter() {
        presenter = new HomeActivityPresenter(this);
        return presenter;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        StatusBarCompat.setStatusBarColor(this,mResources.getColor(R.color.material_red_400));
        homeActivityFragmentAdapter = new HomeActivityFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(homeActivityFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
