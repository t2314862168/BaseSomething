package com.tangxb.basic.something.api;

import io.reactivex.functions.Consumer;

/**
 * 默认的异常处理<br>
 * Created by Taxngb on 2017/12/22.
 */

public class DefaultConsumerThrowable implements Consumer<Throwable> {
    @Override
    public void accept(Throwable throwable) throws Exception {

    }
}
