package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.bean.WelfareBean;
import com.tangxb.basic.something.mvp.model.BaseModel;
import com.tangxb.basic.something.mvp.model.BaseModelImpl;
import com.tangxb.basic.something.mvp.view.BaseFragmentView;

import java.util.List;

import rx.Observable;

/**
 * Created by Tangxb on 2016/12/13.
 */
public class BaseFragmentPresenter extends BasePresenter<BaseFragmentView<WelfareBean>> {
    private BaseModel<WelfareBean> model;

    public BaseFragmentPresenter(BaseFragmentView<WelfareBean> iBaseFragmentView) {
        attachView(iBaseFragmentView);
        model = new BaseModelImpl();
    }

    @Override
    public Observable<List<WelfareBean>> createObservable(String category, int pageSize, int pageNum) {
        return model.getCommonList(category, pageSize, pageNum);
    }
}
