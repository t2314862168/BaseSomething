package com.tangxb.basic.something.api;

import com.tangxb.basic.something.bean.MBaseBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Taxngb on 2017/12/22.
 */

public interface TaskRxAPI {
    @GET("back/user/getUserList")
    Observable<MBaseBean<String>> getUserList(@Header("token") String token, @Header("signatrue") String signatrue,
                                              @Header("timestamp") String timestamp, @Query("page") int page,
                                              @Query("rows") int rows);
}
