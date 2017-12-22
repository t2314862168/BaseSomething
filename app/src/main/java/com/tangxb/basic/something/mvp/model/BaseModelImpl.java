package com.tangxb.basic.something.mvp.model;

import com.tangxb.basic.something.RetrofitRxClient;
import com.tangxb.basic.something.api.WelfareRxAPI;
import com.tangxb.basic.something.bean.BaseBean;
import com.tangxb.basic.something.bean.WelfareBean;
import com.tangxb.basic.something.util.DateUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;


/**
 * Created by Tangxb on 2016/12/13.
 */

public class BaseModelImpl implements BaseModel<WelfareBean> {
    @Override
    public Observable<List<WelfareBean>> getCommonList(String category, int pageSize, int pageNum) {
        return RetrofitRxClient.INSTANCE.getRetrofit().create(WelfareRxAPI.class).getExternalBean(category, pageSize, pageNum)
                .filter(new Predicate<BaseBean<List<WelfareBean>>>() {
                    @Override
                    public boolean test(@NonNull BaseBean<List<WelfareBean>> listBaseBean) throws Exception {
                        return !listBaseBean.isError();
                    }

                })
                .flatMap(new Function<BaseBean<List<WelfareBean>>, Observable<List<WelfareBean>>>() {
                    @Override
                    public Observable<List<WelfareBean>> apply(@NonNull BaseBean<List<WelfareBean>> listBaseBean) throws Exception {
                        return Observable.just(listBaseBean.getResults());
                    }
                })
                .doOnNext(new Consumer<List<WelfareBean>>() {
                    @Override
                    public void accept(List<WelfareBean> resultBeen) throws Exception {
                        for (WelfareBean resultBean : resultBeen) {
                            resultBean.setCreatedAt(DateUtils.friendlyTime(resultBean.getCreatedAt()));
                            resultBean.setPublishedAt(DateUtils.friendlyTime(resultBean.getPublishedAt()));
                        }
                    }
                });
    }
}
