package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.MApplication;
import com.tangxb.basic.something.api.DefaultConsumer;
import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.mvp.model.TaskFragmentModel;
import com.tangxb.basic.something.mvp.model.TaskFragmentModelImpl;
import com.tangxb.basic.something.mvp.view.TaskFragmentView;

import io.reactivex.Observable;

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

    public void getUserList(final MApplication mApplication, String token, String signatrue, String timestamp, int page, int rows) {
        addSubscription(getUserList(token, signatrue, timestamp, page, rows), new DefaultConsumer<String>() {
            @Override
            public void operateSuccess(MBaseBean<String> baseBean) {
                System.out.println();
            }
        });
    }

    private Observable<MBaseBean<String>> getUserList(String token, String signatrue, String timestamp, int page, int rows) {
        return fragmentModel.getUserList(token, signatrue, timestamp, page, rows);
    }
}
