package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.RetrofitRxClient;
import com.tangxb.basic.something.api.TaskRxAPI;
import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.PurchaseGroupBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class AssignTaskActivityModelImpl implements AssignTaskActivityModel {
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
    @Override
    public Observable<MBaseBean<List<PurchaseGroupBean>>> getPurchaseGroupList(String token, String signatrue, String timestamp, long userId, int page, int rows) {
        return RetrofitRxClient.INSTANCE.getRetrofit().create(TaskRxAPI.class).getPurchaseGroupList(token, signatrue, timestamp, userId, page, rows);
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
    @Override
    public Observable<MBaseBean<Boolean>> commitPurchaseGroupList(String token, String signatrue, String timestamp
            , String purchaseId, String groups) {
        return RetrofitRxClient.INSTANCE.getRetrofit().create(TaskRxAPI.class).commitPurchaseGroupList(token, signatrue, timestamp, purchaseId, groups);
    }

}
