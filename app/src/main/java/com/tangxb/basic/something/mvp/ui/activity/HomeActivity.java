package com.tangxb.basic.something.mvp.ui.activity;

import android.Manifest;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.tangxb.basic.something.R;
import com.tangxb.basic.something.mvp.presenter.BasePresenter;
import com.tangxb.basic.something.mvp.presenter.HomeActivityPresenter;
import com.tangxb.basic.something.mvp.view.HomeActivityView;
import com.tangxb.basic.something.util.MLogUtils;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class HomeActivity extends BaseActivity implements HomeActivityView, EasyPermissions.PermissionCallbacks {
    private HomeActivityPresenter presenter;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    HomeActivityFragmentAdapter homeActivityFragmentAdapter;
    final int RC_STORAGE_PERM = 121;
    /**
     * 是否有拨打电话的权限
     */
    boolean hasDialPermission;

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
        storageTask();
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(mActivity, R.color.material_red_400));
        homeActivityFragmentAdapter = new HomeActivityFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(homeActivityFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @AfterPermissionGranted(RC_STORAGE_PERM)
    public void storageTask() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            MLogUtils.d(mClassName, "hasPermissions true");
        } else {
            EasyPermissions.requestPermissions(this, mResources.getString(R.string.apply_permission_tips), RC_STORAGE_PERM, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发给EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (perms.contains(Manifest.permission.CALL_PHONE)) {
            hasDialPermission = true;
        }
        MLogUtils.d(mClassName, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        MLogUtils.d(mClassName, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
