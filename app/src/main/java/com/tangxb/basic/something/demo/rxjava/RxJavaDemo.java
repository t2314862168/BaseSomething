package com.tangxb.basic.something.demo.rxjava;

import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Taxngb on 2017/4/24.
 */

public class RxJavaDemo {
    private static final String TAG = RxJavaDemo.class.getSimpleName();

    public void testSomething() {
        test4();
    }

    public void test1() {
        final String methodName = "####test1()";
        Observable.just("username")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Log.d(TAG + methodName, "map Current Thread===" + Thread.currentThread().getName());
                        return "我是编号后的###" + s;
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Log.d(TAG + methodName, "map02 Current Thread===" + Thread.currentThread().getName());
                        return "我是编号后的###" + s;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG + methodName, "doOnNext Current Thread===" + Thread.currentThread().getName());
                        Log.d(TAG + methodName, "doOnNext s===" + s);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG + methodName, "subscribe Current Thread===" + Thread.currentThread().getName());
                        Log.d(TAG + methodName, "subscribe s===" + s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    public void test2() {
        final String methodName = "####test2()";
        //Observer/Subscriber (观察者)的创建
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "老师说了：" + s);
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "哈哈！下课啦~");
            }

            @Override
            public void onError(Throwable e) {
                //onError之前没提到，可以把它想象成老师被校长紧急召唤去开会了之类的不能继续把课上完的情况。
                //注意：onError一旦触发，onCompleted就不会再触发了。反之亦然~
                Log.e(TAG, "什么?老师有事没法完成这节课?咱们继续来斗地主吧，哈哈~");
            }
        };

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                //同Observer
            }

            @Override
            public void onCompleted() {
                //同Observer
            }

            @Override
            public void onError(Throwable e) {
                //同Observer
            }
        };

        // Observable (被观察者)的创建
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("上课！");
                subscriber.onNext("打开课本第19页");
                subscriber.onNext("今天的课后作业是……");
                subscriber.onCompleted();//这个是下课的指令
            }
        }).subscribe(subscriber);
    }

    public void test3() {
        final String methodName = "####test3()";
        Observable.just("username")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        Log.d(TAG + methodName, "map Current Thread===" + Thread.currentThread().getName());
                        Log.d(TAG + methodName, "map s===" + s);
                        try {
                            Thread.sleep(3000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return new Integer(1000);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG + methodName, "subscribe Current Thread===" + Thread.currentThread().getName());
                        Log.d(TAG + methodName, "subscribe integer===" + integer);
                    }
                });
    }

    public void test4() {
        final String methodName = "####test4()";
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.d(TAG + methodName, "call Current Thread===" + Thread.currentThread().getName());
                int i = 100 / 0;
                subscriber.onNext(2000);
                subscriber.onNext(3000);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG + methodName, "subscribe Current Thread===" + Thread.currentThread().getName());
                        Log.d(TAG + methodName, "subscribe integer===" + integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG + methodName, "call throwable===" + throwable.getMessage());
                    }
                });
    }
}
