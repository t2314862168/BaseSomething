package com.tangxb.basic.something.mvp.view;

import com.tangxb.basic.something.bean.PurchaseGroupBean;

import java.util.List;

/**
 * Created by Taxngb on 2017/12/25.
 */

public interface AssignTaskActivityView extends BaseActivityView {
    void showDialog();

    void operateSuccess(List<PurchaseGroupBean> baseBean);

    /**
     * 提交成功
     */
    void commitSuccess();

    /**
     * 提交失败
     */
    void commitFailed();
}
