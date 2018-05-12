package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.MApplication;
import com.tangxb.basic.something.api.DefaultConsumer;
import com.tangxb.basic.something.api.DefaultConsumerThrowable;
import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.PermissionBean;
import com.tangxb.basic.something.mvp.model.AssignPermissionActivityModel;
import com.tangxb.basic.something.mvp.model.AssignPermissionActivityModelImpl;
import com.tangxb.basic.something.mvp.view.AssignPermissionActivityView;
import com.tangxb.basic.something.tools.encrypt.MessageDigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class AssignPermissionActivityPresenter extends BaseActivityPresenter {
    private AssignPermissionActivityView activityView;
    private AssignPermissionActivityModel activityModel;

    public AssignPermissionActivityPresenter(AssignPermissionActivityView activityView) {
        super(activityView);
        this.activityView = activityView;
        activityModel = new AssignPermissionActivityModelImpl();
    }

    /**
     * 点击提交
     */
    public void clickCommit() {
        activityView.showDialog();
    }

    public void commitData(final MApplication mApplication, String roleId, String permissions) {
        Map<String, String> data = new HashMap<>();
        data.put("role_id", roleId);
        data.put("permissions", permissions);
        String token = mApplication.getUserLoginResultBean().getUser().getToken();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String sign = MessageDigestUtils.getSign(data, token, timestamp);
        addSubscription(commitPermissionGroupList(token, sign, timestamp, roleId, permissions), new DefaultConsumer<Boolean>(mApplication) {
            @Override
            public void operateSuccess(MBaseBean<Boolean> baseBean) {
                activityView.commitSuccess();
            }

            @Override
            public void tokenError(String message) {
                super.tokenError(message);
                activityView.commitFailed();
            }

            @Override
            public void operateError(String message) {
                super.operateError(message);
                activityView.commitFailed();
            }
        }, new DefaultConsumerThrowable() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                super.accept(throwable);
                activityView.commitFailed();
            }
        });
    }


    /**
     * 获取采购组
     *
     * @param mApplication
     * @param page
     * @param rows
     */
    public void getPermissionGroupList(final MApplication mApplication, long userId, int page, int rows) {
        Map<String, String> data = new HashMap<>();
        data.put("page", String.valueOf(page));
        data.put("rows", String.valueOf(rows));
        String token = mApplication.getUserLoginResultBean().getUser().getToken();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String sign = MessageDigestUtils.getSign(data, token, timestamp);
        addSubscription(getPermissionGroupList(token, sign, timestamp, userId, page, rows), new DefaultConsumer<List<PermissionBean>>(mApplication) {
            @Override
            public void operateSuccess(MBaseBean<List<PermissionBean>> baseBean) {
                activityView.operateSuccess(baseBean.getData());
            }

            @Override
            public void tokenError(String message) {
                super.tokenError(message);
                activityView.stopLoadData();
            }

            @Override
            public void operateError(String message) {
                super.operateError(message);
                activityView.stopLoadData();
            }
        }, new DefaultConsumerThrowable() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                super.accept(throwable);
                activityView.stopLoadData();
            }
        });
    }

    /**
     * 获取采购组
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param page
     * @param rows
     * @return
     */
    private Observable<MBaseBean<List<PermissionBean>>> getPermissionGroupList(String token, String signatrue, String timestamp, long roleId, int page, int rows) {
        return activityModel.getPermissionGroupList(token, signatrue, timestamp, roleId, page, rows);
    }

    /**
     * 提交采购组数据
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param roleId
     * @param permissions
     * @return
     */
    private Observable<MBaseBean<Boolean>> commitPermissionGroupList(String token, String signatrue, String timestamp
            , String roleId, String permissions) {
        return activityModel.commitPermissionGroupList(token, signatrue, timestamp, roleId, permissions);
    }
}
