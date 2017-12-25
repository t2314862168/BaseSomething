package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.mvp.view.BaseFragmentView;


/**
 * Created by Tangxb on 2016/12/13.
 */
public class BaseFragmentPresenter extends BasePresenter<BaseFragmentView> {
    public BaseFragmentPresenter(BaseFragmentView baseFragmentView) {
        attachView(baseFragmentView);
    }
}
