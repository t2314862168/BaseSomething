package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.MApplication;
import com.tangxb.basic.something.api.DefaultConsumer;
import com.tangxb.basic.something.api.DefaultConsumerThrowable;
import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.PurchaseGroupBean;
import com.tangxb.basic.something.mvp.model.AssignTaskActivityModel;
import com.tangxb.basic.something.mvp.model.AssignTaskActivityModelImpl;
import com.tangxb.basic.something.mvp.view.AssignTaskActivityView;
import com.tangxb.basic.something.tools.encrypt.MessageDigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class AssignTaskActivityPresenter extends BaseActivityPresenter {
    private AssignTaskActivityView activityView;
    private AssignTaskActivityModel activityModel;

    public AssignTaskActivityPresenter(AssignTaskActivityView activityView) {
        super(activityView);
        this.activityView = activityView;
        activityModel = new AssignTaskActivityModelImpl();
    }

    /**
     * 点击提交
     */
    public void clickCommit() {
        activityView.showDialog();
    }

    public void commitData(final MApplication mApplication, String purchaseId, String groups) {
        Map<String, String> data = new HashMap<>();
        data.put("purchaseId", purchaseId);
        data.put("groups", groups);
        String token = mApplication.getUserLoginResultBean().getUser().getToken();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String sign = MessageDigestUtils.getSign(data, token, timestamp);
        addSubscription(commitPurchaseGroupList(token, sign, timestamp, purchaseId, groups), new DefaultConsumer<Boolean>(mApplication) {
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
    public void getPurchaseGroupList(final MApplication mApplication, long userId, int page, int rows) {
        Map<String, String> data = new HashMap<>();
        data.put("page", String.valueOf(page));
        data.put("rows", String.valueOf(rows));
        String token = mApplication.getUserLoginResultBean().getUser().getToken();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String sign = MessageDigestUtils.getSign(data, token, timestamp);
        addSubscription(getPurchaseGroupList(token, sign, timestamp, userId, page, rows), new DefaultConsumer<List<PurchaseGroupBean>>(mApplication) {
            @Override
            public void operateSuccess(MBaseBean<List<PurchaseGroupBean>> baseBean) {
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
    private Observable<MBaseBean<List<PurchaseGroupBean>>> getPurchaseGroupList(String token, String signatrue, String timestamp, long userId, int page, int rows) {
        return activityModel.getPurchaseGroupList(token, signatrue, timestamp, userId, page, rows);
    }

    /**
     * 提交采购组数据
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param purchaseId
     * @param groups
     * @return
     */
    private Observable<MBaseBean<Boolean>> commitPurchaseGroupList(String token, String signatrue, String timestamp
            , String purchaseId, String groups) {
        return activityModel.commitPurchaseGroupList(token, signatrue, timestamp, purchaseId, groups);
    }
}
