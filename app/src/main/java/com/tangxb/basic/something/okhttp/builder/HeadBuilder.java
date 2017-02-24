package com.tangxb.basic.something.okhttp.builder;


import com.tangxb.basic.something.okhttp.OkHttpUtils;
import com.tangxb.basic.something.okhttp.request.OtherRequest;
import com.tangxb.basic.something.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
