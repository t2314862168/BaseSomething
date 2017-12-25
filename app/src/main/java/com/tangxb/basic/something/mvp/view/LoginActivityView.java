package com.tangxb.basic.something.mvp.view;

/**
 * Created by Tangxb on 2016/12/14.
 */

public interface LoginActivityView extends BaseActivityView {
    /**
     * 显示登录进度框
     */
    void showDialog();

    /**
     * 隐藏登录进度框
     */
    void hiddenDialog();

    /**
     * 登录成功
     */
    void loginSuccess();
}
