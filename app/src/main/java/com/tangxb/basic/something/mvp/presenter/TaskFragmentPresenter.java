package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.mvp.model.TaskFragmentModel;
import com.tangxb.basic.something.mvp.model.TaskFragmentModelImpl;
import com.tangxb.basic.something.mvp.view.TaskFragmentView;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class TaskFragmentPresenter extends BaseFragmentPresenter {
    private TaskFragmentView fragmentView;
    private TaskFragmentModel fragmentModel;

    public TaskFragmentPresenter(TaskFragmentView fragmentView) {
        super(fragmentView);
        this.fragmentView = fragmentView;
        fragmentModel = new TaskFragmentModelImpl();
    }
}
