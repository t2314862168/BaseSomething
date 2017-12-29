package com.tangxb.basic.something.mvp.view;

import com.tangxb.basic.something.bean.PurchaseUserBean;

import java.util.List;

/**
 * Created by Tangxb on 2016/12/13.
 */

public interface TaskFragmentView extends BaseFragmentView {
    void operateSuccess(List<PurchaseUserBean> baseBean);
}
