package com.tangxb.basic.something.mvp.view;

import java.util.List;

/**
 * Created by Tangxb on 2016/12/13.
 */

public interface BaseFragmentView<T> extends BaseView {
    void showLoading();

    void hideLoading();

    void getDataSuccess(List<T> listBean);

    void getDataFail(String msg);
}
