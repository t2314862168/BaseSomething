package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.MApplication;
import com.tangxb.basic.something.api.DefaultConsumer;
import com.tangxb.basic.something.api.DefaultConsumerThrowable;
import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.PurchaseUserBean;
import com.tangxb.basic.something.mvp.model.TaskFragmentModel;
import com.tangxb.basic.something.mvp.model.TaskFragmentModelImpl;
import com.tangxb.basic.something.mvp.view.TaskFragmentView;
import com.tangxb.basic.something.tools.encrypt.MessageDigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class TaskFragmentPresenter extends BaseFragmentPresenter {
    private TaskFragmentView fragmentView;
    private TaskFragmentModel fragmentModel;

    public TaskFragmentPresenter(TaskFragmentView fragmentView) {
        super(fragmentView);
        this.fragmentView = fragmentView;
        fragmentModel = new TaskFragmentModelImpl();
    }

    /**
     * 获取采购人
     *
     * @param mApplication
     * @param page
     * @param rows
     */
    public void getPurchaseUserList(final MApplication mApplication, int page, int rows) {
        Map<String, String> data = new HashMap<>();
        data.put("page", String.valueOf(page));
        data.put("rows", String.valueOf(rows));
        String token = mApplication.getUserLoginResultBean().getUser().getToken();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String sign = MessageDigestUtils.getSign(data, token, timestamp);
        addSubscription(getPurchaseUserList(token, sign, timestamp, page, rows), new DefaultConsumer<List<PurchaseUserBean>>(mApplication) {
            @Override
            public void operateSuccess(MBaseBean<List<PurchaseUserBean>> baseBean) {
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
     * 获取采购人
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param page
     * @param rows
     * @return
     */
    private Observable<MBaseBean<List<PurchaseUserBean>>> getPurchaseUserList(String token, String signatrue, String timestamp, int page, int rows) {
        return fragmentModel.getPurchaseUserList(token, signatrue, timestamp, page, rows);
    }
}
