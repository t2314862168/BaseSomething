package com.tangxb.basic.something.mvp.view;

/**
 * Created by Tangxb on 2016/12/13.
 */

public interface BaseFragmentView extends BaseView {
    /**
     * 停止加载数据
     */
    void stopLoadData();

    /**
     * 下拉刷新获取数据
     */
    void refreshGetData();

    /**
     * 上拉加载数据
     */
    void loadMoreGetData();

    /**
     * 重置页数
     */
    void resetPageNum();
}
