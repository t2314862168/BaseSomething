package com.tangxb.basic.something.api;

import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.PurchaseGroupBean;
import com.tangxb.basic.something.bean.PurchaseUserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Taxngb on 2017/12/22.
 */

public interface TaskRxAPI {
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
    @GET("rolePermission/getPurchase")
    Observable<MBaseBean<List<PurchaseUserBean>>> getPurchaseUserList(@Header("token") String token, @Header("signatrue") String signatrue,
                                                                      @Header("timestamp") String timestamp, @Query("page") int page,
                                                                      @Query("rows") int rows);

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
    @GET("rolePermission/getMyGroup")
    Observable<MBaseBean<List<PurchaseGroupBean>>> getPurchaseGroupList(@Header("token") String token, @Header("signatrue") String signatrue,
                                                                        @Header("timestamp") String timestamp, @Query("user_id") long userId, @Query("page") int page,
                                                                        @Query("rows") int rows);

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
    @FormUrlEncoded
    @POST("rolePermission/updateBuyTask")
    Observable<MBaseBean<Boolean>> commitPurchaseGroupList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @Field("purchaseId") String purchaseId
            , @Field("groups") String groups);
}
