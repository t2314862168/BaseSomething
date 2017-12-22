package com.tangxb.basic.something.mvp.presenter;

import com.tangxb.basic.something.api.DefaultConsumerThrowable;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * <a href="https://github.com/WuXiaolong/AndroidMVPSample/blob/master/app/src/main/java/com/wuxiaolong/androidmvpsample/mvp/other/BasePresenter.java">好的实例</a> <br>
 * Created by Tangxb on 2016/12/13.
 */

public class BasePresenter<V> {
    public V mvpView;
    private CompositeDisposable mCompositeDisposable;
    private DefaultConsumerThrowable mDefaultConsumerThrowable;

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        mDefaultConsumerThrowable = new DefaultConsumerThrowable();
    }

    public void detachView() {
        this.mvpView = null;
        onUnSubscribe();
    }

    /**
     * 取消注册，以避免内存泄露
     */
    public void onUnSubscribe() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
            mCompositeDisposable = null;
        }
    }

    public <T> Observable<List<T>> createObservable(String category, int pageSize, int pageNum) {
        return null;
    }

    public <T> void addSubscription(Observable<T> observable, Consumer<T> consumer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, mDefaultConsumerThrowable));
    }

    public <T> void addSubscription(Observable<T> observable, Consumer<T> consumer, Consumer<Throwable> onError) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, onError));
    }
}
