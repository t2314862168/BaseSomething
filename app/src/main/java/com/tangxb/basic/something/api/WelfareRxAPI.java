package com.tangxb.basic.something.api;

import com.tangxb.basic.something.bean.BaseBean;
import com.tangxb.basic.something.bean.WelfareBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Tangxb on 2016/11/4.
 */

public interface WelfareRxAPI {
    @GET("data/{welfare}/{pageSize}/{pageNum}")
    Observable<BaseBean<List<WelfareBean>>> getExternalBean(@Path("welfare") String welfare, @Path("pageSize") int pageSize, @Path("pageNum") int pageNum);
}
