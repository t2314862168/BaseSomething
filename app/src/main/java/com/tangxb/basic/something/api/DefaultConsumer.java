package com.tangxb.basic.something.api;

import com.tangxb.basic.something.bean.MBaseBean;
import com.tangxb.basic.something.util.MLogUtils;

import io.reactivex.functions.Consumer;

/**
 * 默认的请求返回处理<br>
 * Created by Taxngb on 2017/12/22.
 */

public abstract class DefaultConsumer<T> implements Consumer<MBaseBean<T>> {
    private static final String TAG = DefaultConsumer.class.getSimpleName();
    /**
     * 服务器操作成功的状态码
     */
    private final int successCode = 1;

    /**
     * 后台返回操作成功调用
     *
     * @param baseBean
     */
    public abstract void operateSuccess(MBaseBean<T> baseBean);

    /**
     * 请在需要的时候打印数据
     *
     * @param message
     */
    public void operateError(String message) {

    }

    @Override
    public void accept(MBaseBean<T> baseBean) throws Exception {
        if (baseBean.getCode() == successCode) {
            operateSuccess(baseBean);
        } else {
            operateError(baseBean.getMessage());
            MLogUtils.d(TAG, baseBean.getMessage());
        }
    }
}
