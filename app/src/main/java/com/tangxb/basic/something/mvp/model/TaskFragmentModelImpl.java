package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.RetrofitRxClient;
import com.tangxb.basic.something.api.TaskRxAPI;
import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.PurchaseUserBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/22.
 */

public class TaskFragmentModelImpl implements TaskFragmentModel {

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
    @Override
    public Observable<MBaseBean<List<PurchaseUserBean>>> getPurchaseUserList(String token, String signatrue, String timestamp, int page, int rows) {
        return RetrofitRxClient.INSTANCE.getRetrofit().create(TaskRxAPI.class).getPurchaseUserList(token, signatrue, timestamp, page, rows);
    }
}
