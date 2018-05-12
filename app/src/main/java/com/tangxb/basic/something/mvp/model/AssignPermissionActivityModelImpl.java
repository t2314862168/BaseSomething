package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.RetrofitRxClient;
import com.tangxb.basic.something.api.RoleRxAPI;
import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.PermissionBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class AssignPermissionActivityModelImpl implements AssignPermissionActivityModel {
    @Override
    public Observable<MBaseBean<List<PermissionBean>>> getPermissionGroupList(String token, String signatrue, String timestamp, long roleId, int page, int rows) {
        return RetrofitRxClient.INSTANCE.getRetrofit().create(RoleRxAPI.class).getPermissionGroupList(token, signatrue, timestamp, roleId, page, rows);
    }

    @Override
    public Observable<MBaseBean<Boolean>> commitPermissionGroupList(String token, String signatrue, String timestamp, String roleId, String permissions) {
        return RetrofitRxClient.INSTANCE.getRetrofit().create(RoleRxAPI.class).commitPermissionGroupList(token, signatrue, timestamp, roleId, permissions);
    }
}
