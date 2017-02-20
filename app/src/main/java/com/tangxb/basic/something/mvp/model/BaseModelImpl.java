package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.RetrofitRxClient;
import com.tangxb.basic.something.api.WelfareRxAPI;
import com.tangxb.basic.something.bean.BaseBean;
import com.tangxb.basic.something.bean.WelfareBean;
import com.tangxb.basic.something.util.DateUtils;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Tangxb on 2016/12/13.
 */

public class BaseModelImpl implements BaseModel<WelfareBean> {
    @Override
    public Observable<List<WelfareBean>> getCommonList(String category, int pageSize, int pageNum) {
        return RetrofitRxClient.INSTANCE.getRetrofit().create(WelfareRxAPI.class).getExternalBean(category, pageSize, pageNum)
                .filter(new Func1<BaseBean<List<WelfareBean>>, Boolean>() {
                    @Override
                    public Boolean call(BaseBean<List<WelfareBean>> listBaseBean) {
                        return !listBaseBean.isError();
                    }
                })
                .flatMap(new Func1<BaseBean<List<WelfareBean>>, Observable<List<WelfareBean>>>() {
                    @Override
                    public Observable<List<WelfareBean>> call(BaseBean<List<WelfareBean>> listBaseBean) {
                        return Observable.just(listBaseBean.getResults());
                    }
                })
//                .flatMap(new Func1<List<WelfareBean>, Observable<WelfareBean>>() {
//                    @Override
//                    public Observable<WelfareBean> call(List<WelfareBean> welfareBeen) {
//                        return Observable.from(welfareBeen);
//                    }
//                }).sorted(new Func2<WelfareBean, WelfareBean, Integer>() {
//                    @Override
//                    public Integer call(WelfareBean welfareBean, WelfareBean welfareBean2) {
//                        return welfareBean.getDesc().compareToIgnoreCase(welfareBean2.getDesc());
//                    }
//                }).toList()
                .doOnNext(new Action1<List<WelfareBean>>() {
                    @Override
                    public void call(List<WelfareBean> resultBeen) {
                        for (WelfareBean resultBean : resultBeen) {
                            resultBean.setCreatedAt(DateUtils.friendlyTime(resultBean.getCreatedAt()));
                            resultBean.setPublishedAt(DateUtils.friendlyTime(resultBean.getPublishedAt()));
                        }
                    }
                });
    }
}
