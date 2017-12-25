package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.mvp.model.AssignPermissionActivityModel;
import com.tangxb.basic.something.mvp.model.AssignPermissionActivityModelImpl;
import com.tangxb.basic.something.mvp.view.AssignPermissionActivityView;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class AssignPermissionActivityPresenter extends BaseActivityPresenter {
    private AssignPermissionActivityView activityView;
    private AssignPermissionActivityModel activityModel;

    public AssignPermissionActivityPresenter(AssignPermissionActivityView activityView) {
        super(activityView);
        this.activityView = activityView;
        activityModel = new AssignPermissionActivityModelImpl();
    }
}
