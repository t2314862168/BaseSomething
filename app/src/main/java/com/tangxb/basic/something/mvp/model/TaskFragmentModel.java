package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.bean.MBaseBean;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/22.
 */

public interface TaskFragmentModel extends MBaseModel {
    Observable<MBaseBean<String>> getUserList(String token, String signatrue, String timestamp, int page, int rows);
}
