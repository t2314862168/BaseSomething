package com.tangxb.basic.something.api;

import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.UserLoginResultBean;

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

public interface UserRxAPI {
    @FormUrlEncoded
    @POST("user/login")
    Observable<MBaseBean<UserLoginResultBean>> loginUser(@Field("username") String username, @Field("password") String password);

    @GET("back/product/getProductPySupplier")
    Observable<MBaseBean<String>> getCategory(@Header("token") String token, @Header("signatrue") String signatrue,
                                              @Header("timestamp") String timestamp, @Query("page") int page,
                                              @Query("isList") int isList, @Query("categoryId") Long categoryId,
                                              String keyword);
}
