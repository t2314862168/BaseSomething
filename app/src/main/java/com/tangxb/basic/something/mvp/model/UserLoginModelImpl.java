package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.RetrofitRxClient;
import com.tangxb.basic.something.api.UserRxAPI;
import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.UserLoginResultBean;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/22.
 */

public class UserLoginModelImpl implements UserLoginModel {
    @Override
    public Observable<MBaseBean<UserLoginResultBean>> loginUser(String username, String password) {
        return RetrofitRxClient.INSTANCE.getRetrofit().create(UserRxAPI.class).loginUser(username, password);
    }

    @Override
    public Observable<MBaseBean<String>> getCategory(String token, String signatrue, String timestamp, int page, int isList, Long categoryId, String keyword) {
        return RetrofitRxClient.INSTANCE.getRetrofit().create(UserRxAPI.class).getCategory(token, signatrue, timestamp, page, isList, categoryId, keyword);
    }
}
