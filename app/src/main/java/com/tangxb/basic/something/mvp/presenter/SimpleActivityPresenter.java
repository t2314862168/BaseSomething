package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.bean.WelfareBean;
import com.tangxb.basic.something.mvp.model.BaseModel;
import com.tangxb.basic.something.mvp.model.BaseModelImpl;
import com.tangxb.basic.something.mvp.view.BaseActivityView;

import java.util.List;

import rx.Observable;

/**
 * Created by Tangxb on 2017/2/16.
 */

public class SimpleActivityPresenter extends BaseActivityPresenter {
    BaseModel<WelfareBean> model;

    public SimpleActivityPresenter(BaseActivityView baseActivityView) {
        super(baseActivityView);
        model = new BaseModelImpl();
    }

    public Observable<List<WelfareBean>> createObservable(String category, int pageSize, int pageNum) {
        return model.getCommonList(category, pageSize, pageNum);
    }
}
