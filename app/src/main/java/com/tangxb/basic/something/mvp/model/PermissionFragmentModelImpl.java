package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.RetrofitRxClient;
import com.tangxb.basic.something.api.RoleRxAPI;
import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.RoleBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/22.
 */

public class PermissionFragmentModelImpl implements PermissionFragmentModel {

    /**
     * 获取所有角色
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Observable<MBaseBean<List<RoleBean>>> getRolerList(String token, String signatrue, String timestamp, int page, int rows) {
        return RetrofitRxClient.INSTANCE.getRetrofit().create(RoleRxAPI.class).getRolerList(token, signatrue, timestamp, page, rows);
    }
}
