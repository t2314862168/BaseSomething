package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.PermissionBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/25.
 */

public interface AssignPermissionActivityModel {
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
    Observable<MBaseBean<List<PermissionBean>>> getPermissionGroupList(String token, String signatrue, String timestamp
            , long roleId, int page, int rows);

    /**
     * 提交权限组数据
     *
     * @param token
     * @param signatrue
     * @param timestamp
     * @param roleId
     * @param permissions
     * @return
     */
    Observable<MBaseBean<Boolean>> commitPermissionGroupList(String token, String signatrue, String timestamp
            , String roleId, String permissions);
}
