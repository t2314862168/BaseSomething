package com.tangxb.basic.something.mvp.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tangxb.basic.something.R;
import com.tangxb.basic.something.mvp.presenter.BasePresenter;
import com.tangxb.basic.something.mvp.presenter.LoginActivityPresenter;
import com.tangxb.basic.something.mvp.view.LoginActivityView;
import com.tangxb.basic.something.util.ConstUtils;
import com.tangxb.basic.something.util.MDrawableUtils;
import com.tangxb.basic.something.util.SPUtils;
import com.tangxb.basic.something.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class LoginActivity extends BaseActivity implements LoginActivityView {
    LoginActivityPresenter presenter;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.til_account)
    TextInputLayout til_account;
    @BindView(R.id.til_password)
    TextInputLayout til_password;
    @BindView(R.id.tv_remember_password)
    TextView mRememberPwdTv;
    private boolean mRememberPwdFlag;
    private int i = -1;
    private SweetAlertDialog mSweetAlertDialog;

    @Override
    protected BasePresenter createPresenter() {
        presenter = new LoginActivityPresenter(this);
        return presenter;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        StatusBarCompat.translucentStatusBar(mActivity);
        boolean contains = SPUtils.contains(mApplication, ConstUtils.ACCOUNT_KEY);
        if (contains) {
            String username = (String) SPUtils.get(mApplication, ConstUtils.ACCOUNT_KEY, "");
            String password = (String) SPUtils.get(mApplication, ConstUtils.PASSWORD_KEY, "");
            til_account.getEditText().setText(username);
            til_password.getEditText().setText(password);
            mRememberPwdFlag = true;
            int resId = mRememberPwdFlag ? R.drawable.ic_check_box_black_24dp : R.drawable.ic_check_box_outline_blank_grey_700_24dp;
            Drawable drawable = MDrawableUtils.getDrawable(mResources, resId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mRememberPwdTv.setCompoundDrawables(drawable, null, null, null);
        }
    }

    /**
     * 点击登录按钮
     *
     * @param view
     */
    @OnClick(R.id.btn_login)
    public void clickLoginBtn(View view) {
        String account = til_account.getEditText().getText().toString();
        String password = til_password.getEditText().getText().toString();
        til_account.setErrorEnabled(false);
        til_password.setErrorEnabled(false);
        //验证用户名和密码
        if (validateAccount(account) && validatePassword(password)) {
            saveAccountLoginInfo(account, password);
            showDialog();
            presenter.loginUser(mApplication, account, password);
        }
    }

    /**
     * 保存用户名和密码到本地
     *
     * @param username
     * @param password
     */
    private void saveAccountLoginInfo(String username, String password) {
        if (!mRememberPwdFlag) return;
        boolean contains = SPUtils.contains(mApplication, ConstUtils.ACCOUNT_KEY);
        if (!contains && mRememberPwdFlag) {
            SPUtils.put(mApplication, ConstUtils.ACCOUNT_KEY, username);
            SPUtils.put(mApplication, ConstUtils.PASSWORD_KEY, password);
        }
    }

    /**
     * 显示登录进度框
     */
    @Override
    public void showDialog() {
        mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(mResources.getString(R.string.login_ing));
        mSweetAlertDialog.setCancelable(false);
        mSweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
        mSweetAlertDialog.show();
    }

    /**
     * 隐藏登录进度框
     */
    @Override
    public void hiddenDialog() {
        if (mSweetAlertDialog != null) {
            mSweetAlertDialog.dismiss();
        }
    }

    /**
     * 登录成功
     */
    @Override
    public void loginSuccess() {
        hiddenDialog();
        ToastUtils.t(mApplication, mResources.getString(R.string.login_success));
        Intent intent = new Intent(mActivity, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * 验证用户名
     *
     * @param account
     * @return
     */
    private boolean validateAccount(String account) {
        if (TextUtils.isEmpty(account)) {
            showError(til_account, "用户名不能为空");
            return false;
        }
        return true;
    }

    /**
     * 验证密码
     *
     * @param password
     * @return
     */
    private boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            showError(til_password, "密码不能为空");
            return false;
        }
        if (password.length() < 2 || password.length() > 18) {
            showError(til_password, "密码长度为2-18位");
            return false;
        }
        return true;
    }

    /**
     * 显示错误提示，并获取焦点
     *
     * @param textInputLayout
     * @param error
     */
    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    @OnClick(R.id.tv_remember_password)
    public void clickRememberPwdTv(View view) {
        mRememberPwdFlag = !mRememberPwdFlag;
        int resId = mRememberPwdFlag ? R.drawable.ic_check_box_black_24dp : R.drawable.ic_check_box_outline_blank_grey_700_24dp;
        Drawable drawable = MDrawableUtils.getDrawable(mResources, resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mRememberPwdTv.setCompoundDrawables(drawable, null, null, null);
    }
}
