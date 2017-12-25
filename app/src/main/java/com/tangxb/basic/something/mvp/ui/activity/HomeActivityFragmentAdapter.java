package com.tangxb.basic.something.mvp.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tangxb.basic.something.mvp.ui.fragment.PermissionFragment;
import com.tangxb.basic.something.mvp.ui.fragment.TaskFragment;

/**
 * Created by Administrator on 2017/12/24 0024.
 */

public class HomeActivityFragmentAdapter extends FragmentPagerAdapter {
    private static final int FRAGMENT_SIZE = 2;

    public HomeActivityFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = "分任务";
                break;
            case 1:
                title = "分权限";
                break;
        }
        return title;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = TaskFragment.getInstance("分任务");
                break;
            case 1:
                fragment = PermissionFragment.getInstance("分权限");
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return FRAGMENT_SIZE;
    }
}
