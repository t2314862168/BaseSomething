package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.UserLoginResultBean;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/22.
 */

public interface UserLoginModel extends MBaseModel {
    Observable<MBaseBean<UserLoginResultBean>> loginUser(String username, String password);

    Observable<MBaseBean<String>> getCategory(String token, String signatrue, String timestamp, int page,
                                              int isList, Long categoryId, String keyword);
}
