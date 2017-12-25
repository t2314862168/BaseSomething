package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.MApplication;
import com.tangxb.basic.something.R;
import com.tangxb.basic.something.api.DefaultConsumer;
import com.tangxb.basic.something.api.DefaultConsumerThrowable;
import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.UserLoginResultBean;
import com.tangxb.basic.something.mvp.model.UserLoginModelImpl;
import com.tangxb.basic.something.mvp.view.LoginActivityView;
import com.tangxb.basic.something.util.ToastUtils;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class LoginActivityPresenter extends BaseActivityPresenter {
    UserLoginModelImpl userModel;
    LoginActivityView activityView;

    public LoginActivityPresenter(LoginActivityView activityView) {
        super(activityView);
        this.activityView = activityView;
        userModel = new UserLoginModelImpl();
    }

    public void loginUser(final MApplication mApplication, String username, String password) {
        addSubscription(loginUserLogic(username, password), new DefaultConsumer<UserLoginResultBean>() {
            @Override
            public void operateSuccess(MBaseBean<UserLoginResultBean> baseBean) {
                mApplication.setUserLoginResultBean(baseBean.getData());
                activityView.loginSuccess();
            }

            @Override
            public void operateError(String message) {
                ToastUtils.t(mApplication, message);
                activityView.hiddenDialog();
            }
        }, new DefaultConsumerThrowable() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                super.accept(throwable);
                ToastUtils.t(mApplication, mApplication.getResources().getString(R.string.login_error));
                activityView.hiddenDialog();
            }
        });
    }

    /**
     * 网络登录
     *
     * @param username
     * @param password
     * @return
     */
    public Observable<MBaseBean<UserLoginResultBean>> loginUserLogic(String username, String password) {
        return userModel.loginUser(username, password);
    }

//    public Observable<MBaseBean<String>> getCategory(String token, String signatrue, String timestamp, int page,
//                                                     int isList, Long categoryId, String keyword) {
//        return userModel.getCategory(token, signatrue, timestamp, page, isList, categoryId, keyword);
//    }
}
