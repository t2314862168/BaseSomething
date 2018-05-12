package com.tangxb.basic.something.mvp.view;

import com.tangxb.basic.something.bean.PermissionBean;

import java.util.List;

/**
 * Created by Taxngb on 2017/12/25.
 */

public interface AssignPermissionActivityView extends BaseActivityView {
    void showDialog();

    void operateSuccess(List<PermissionBean> baseBean);

    /**
     * 提交成功
     */
    void commitSuccess();

    /**
     * 提交失败
     */
    void commitFailed();
}
