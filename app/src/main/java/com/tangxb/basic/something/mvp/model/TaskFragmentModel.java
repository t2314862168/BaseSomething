package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.bean.PurchaseUserBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Taxngb on 2017/12/22.
 */

public interface TaskFragmentModel extends MBaseModel {
    Observable<MBaseBean<List<PurchaseUserBean>>> getPurchaseUserList(String token, String signatrue, String timestamp, int page, int rows);
}
