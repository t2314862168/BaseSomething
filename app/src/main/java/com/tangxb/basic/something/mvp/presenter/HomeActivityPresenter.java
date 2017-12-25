package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.mvp.model.HomeActivityModel;
import com.tangxb.basic.something.mvp.model.HomeActivityModelImpl;
import com.tangxb.basic.something.mvp.view.HomeActivityView;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class HomeActivityPresenter extends BaseActivityPresenter {
    private HomeActivityView activityView;
    private HomeActivityModel activityModel;

    public HomeActivityPresenter(HomeActivityView activityView) {
        super(activityView);
        this.activityView = activityView;
        activityModel = new HomeActivityModelImpl();
    }
}
