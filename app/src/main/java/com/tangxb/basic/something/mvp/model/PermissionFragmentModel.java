package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.RoleBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/22.
 */

public interface PermissionFragmentModel extends MBaseModel {
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
    Observable<MBaseBean<List<RoleBean>>> getRolerList(String token, String signatrue, String timestamp, int page, int rows);
}
