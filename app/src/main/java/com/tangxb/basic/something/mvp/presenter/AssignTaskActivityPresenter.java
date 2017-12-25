package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.mvp.model.AssignTaskActivityModel;
import com.tangxb.basic.something.mvp.model.AssignTaskActivityModelImpl;
import com.tangxb.basic.something.mvp.view.AssignTaskActivityView;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class AssignTaskActivityPresenter extends BaseActivityPresenter {
    private AssignTaskActivityView activityView;
    private AssignTaskActivityModel activityModel;

    public AssignTaskActivityPresenter(AssignTaskActivityView activityView) {
        super(activityView);
        this.activityView = activityView;
        activityModel = new AssignTaskActivityModelImpl();
    }

    public void clickCommit() {
        activityView.showDialog();
    }
}
