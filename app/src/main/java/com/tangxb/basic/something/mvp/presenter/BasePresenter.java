package com.tangxb.basic.something.mvp.presenter;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * <a href="https://github.com/WuXiaolong/AndroidMVPSample/blob/master/app/src/main/java/com/wuxiaolong/androidmvpsample/mvp/other/BasePresenter.java">好的实例</a> <br>
 * Created by Tangxb on 2016/12/13.
 */

public class BasePresenter<V> {
    public V mvpView;
    private CompositeSubscription mCompositeSubscription;

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    public void detachView() {
        this.mvpView = null;
        onUnsubscribe();
    }

    /**
     * 取消注册，以避免内存泄露
     */
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription = null;
        }
    }

    public <T> Observable<List<T>> createObservable(String category, int pageSize, int pageNum) {
        return null;
    }

    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }
}
