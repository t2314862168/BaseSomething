package com.tangxb.basic.something.api;

import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.PermissionBean;
import com.tangxb.basic.something.bean.RoleBean;

import java.util.List;

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

public interface RoleRxAPI {
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
    @GET("rolePermission/getRoles")
    Observable<MBaseBean<List<RoleBean>>> getRolerList(@Header("token") String token, @Header("signatrue") String signatrue,
                                                       @Header("timestamp") String timestamp, @Query("page") int page,
                                                       @Query("rows") int rows);

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
    @GET("rolePermission/getMyPermissions")
    Observable<MBaseBean<List<PermissionBean>>> getPermissionGroupList(@Header("token") String token, @Header("signatrue") String signatrue,
                                                                       @Header("timestamp") String timestamp, @Query("role_id") long roleId, @Query("page") int page,
                                                                       @Query("rows") int rows);

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
    @FormUrlEncoded
    @POST("rolePermission/updateRole")
    Observable<MBaseBean<Boolean>> commitPermissionGroupList(@Header("token") String token, @Header("signatrue") String signatrue
            , @Header("timestamp") String timestamp, @Field("role_id") String roleId
            , @Field("permissions") String permissions);
}
