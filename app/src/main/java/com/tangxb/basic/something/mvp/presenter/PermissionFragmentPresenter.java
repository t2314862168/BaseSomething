package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.mvp.model.PermissionFragmentModel;
import com.tangxb.basic.something.mvp.model.PermissionFragmentModelImpl;
import com.tangxb.basic.something.mvp.view.PermissionFragmentView;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class PermissionFragmentPresenter extends BaseFragmentPresenter {
    private PermissionFragmentView fragmentView;
    private PermissionFragmentModel fragmentModel;

    public PermissionFragmentPresenter(PermissionFragmentView fragmentView) {
        super(fragmentView);
        this.fragmentView = fragmentView;
        fragmentModel = new PermissionFragmentModelImpl();
    }
}
