package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.PurchaseGroupBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/25.
 */

public interface AssignTaskActivityModel {
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
    Observable<MBaseBean<List<PurchaseGroupBean>>> getPurchaseGroupList(String token, String signatrue, String timestamp
            , long userId, int page, int rows);

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
    Observable<MBaseBean<Boolean>> commitPurchaseGroupList(String token, String signatrue, String timestamp
            , String purchaseId, String groups);

}

