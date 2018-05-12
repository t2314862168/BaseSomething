package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.MApplication;
import com.tangxb.basic.something.api.DefaultConsumer;
import com.tangxb.basic.something.api.DefaultConsumerThrowable;
import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.RoleBean;
import com.tangxb.basic.something.mvp.model.PermissionFragmentModel;
import com.tangxb.basic.something.mvp.model.PermissionFragmentModelImpl;
import com.tangxb.basic.something.mvp.view.PermissionFragmentView;
import com.tangxb.basic.something.tools.encrypt.MessageDigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class PermissionFragmentPresenter extends BaseFragmentPresenter {
    private PermissionFragmentView fragmentView;
    private PermissionFragmentModel fragmentModel;

    public PermissionFragmentPresenter(PermissionFragmentView fragmentView) {
        super(fragmentView);
        this.fragmentView = fragmentView;
        fragmentModel = new PermissionFragmentModelImpl();
    }

    /**
     * 获取采购人
     *
     * @param mApplication
     * @param page
     * @param rows
     */
    public void getRolerList(final MApplication mApplication, int page, int rows) {
        Map<String, String> data = new HashMap<>();
        data.put("page", String.valueOf(page));
        data.put("rows", String.valueOf(rows));
        String token = mApplication.getUserLoginResultBean().getUser().getToken();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String sign = MessageDigestUtils.getSign(data, token, timestamp);
        addSubscription(getRolerList(token, sign, timestamp, page, rows), new DefaultConsumer<List<RoleBean>>(mApplication) {
            @Override
            public void operateSuccess(MBaseBean<List<RoleBean>> baseBean) {
                fragmentView.operateSuccess(baseBean.getData());
            }

            @Override
            public void tokenError(String message) {
                super.tokenError(message);
                fragmentView.stopLoadData();
            }

            @Override
            public void operateError(String message) {
                super.operateError(message);
                fragmentView.stopLoadData();
            }
        }, new DefaultConsumerThrowable() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                super.accept(throwable);
                fragmentView.stopLoadData();
            }
        });
    }

    /**
     * 获取所有角色
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param page
     * @param rows
     * @return
     */
    private Observable<MBaseBean<List<RoleBean>>> getRolerList(String token, String signatrue, String timestamp, int page, int rows) {
        return fragmentModel.getRolerList(token, signatrue, timestamp, page, rows);
    }
}
