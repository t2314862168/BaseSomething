package com.tangxb.basic.something.mvp.view;

import com.tangxb.basic.something.bean.RoleBean;

import java.util.List;

/**
 * Created by Tangxb on 2016/12/13.
 */

public interface PermissionFragmentView extends BaseFragmentView {
    void operateSuccess(List<RoleBean> baseBean);
}
