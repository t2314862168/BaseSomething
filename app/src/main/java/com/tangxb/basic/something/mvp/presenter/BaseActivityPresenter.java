package com.tangxb.basic.something.mvp.presenter;


import com.tangxb.basic.something.mvp.view.BaseActivityView;

/**
 * Created by Tangxb on 2016/12/13.
 */
public class BaseActivityPresenter extends BasePresenter<BaseActivityView> {

    public BaseActivityPresenter(BaseActivityView baseActivityView) {
        attachView(baseActivityView);
    }
}
