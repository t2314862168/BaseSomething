package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.RetrofitRxClient;
import com.tangxb.basic.something.api.TaskRxAPI;
import com.tangxb.basic.something.bean.MBaseBean;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/22.
 */

public class TaskFragmentModelImpl implements TaskFragmentModel {

    @Override
    public Observable<MBaseBean<String>> getUserList(String token, String signatrue, String timestamp, int page, int rows) {
        return RetrofitRxClient.INSTANCE.getRetrofit().create(TaskRxAPI.class).getUserList(token, signatrue, timestamp, page, rows);
    }
}
