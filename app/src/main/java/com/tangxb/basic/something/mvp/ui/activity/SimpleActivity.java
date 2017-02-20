package com.tangxb.basic.something.mvp.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.basic.something.R;
import com.tangxb.basic.something.api.TSubscriber;
import com.tangxb.basic.something.bean.WelfareBean;
import com.tangxb.basic.something.mvp.presenter.BasePresenter;
import com.tangxb.basic.something.mvp.presenter.SimpleActivityPresenter;
import com.tangxb.basic.something.mvp.view.BaseActivityView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Tangxb on 2017/2/15.
 */

public class SimpleActivity extends BaseActivity implements BaseActivityView {
    private TSubscriber tSubscriber;
    private final String category = "福利";
    private final int pageSize = 20;
    private int pageNum = 1;
    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.test_recycler_view)
    RecyclerView mRecyclerView;
    private List<WelfareBean> mData = new ArrayList<>();
    private RecyclerAdapterWithHF mAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return new SimpleActivityPresenter(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_simple;
    }

    @Override
    protected void initData() {
        CommonAdapter<WelfareBean> commonAdapter = new CommonAdapter<WelfareBean>(mActivity, R.layout.layout_recylerview_item, mData) {
            @Override
            protected void convert(ViewHolder holder, WelfareBean welfareBean, int position) {
                ImageView imageView = holder.getView(R.id.iv);
                mApplication.getImageLoaderFactory().loadCommonImgByUrl(mActivity, welfareBean.getUrl(), imageView);
                holder.setText(R.id.tv, welfareBean.getCreatedAt());
            }
        };
        mAdapter = new RecyclerAdapterWithHF((MultiItemTypeAdapter) commonAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mApplication.getImageLoaderFactory().resumeRequests(mActivity);
                } else {
                    mApplication.getImageLoaderFactory().pauseRequests(mActivity);
                }
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mRecyclerView.setAdapter(mAdapter);
        ptrClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandlerEx() {

            @Override
            public void onRefreshBegin(PtrFrameLayoutEx frame) {
                pageNum = 1;
                loadData();
            }
        });
        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                pageNum++;
                loadData();
            }
        });
    }

    /**
     * 注意每次的Observable和Subscriber都需要新生成
     */
    private void loadData() {
        initTSubscriber();
        mvpPresenter.addSubscription(mvpPresenter.createObservable(category, pageSize, pageNum), tSubscriber);
    }

    protected void initTSubscriber() {
        tSubscriber = new TSubscriber() {
            @Override
            public void onCompleted() {
                System.out.println();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println();
            }

            @Override
            public void onNext(List<WelfareBean> listBean) {
                if (pageNum == 1) {
                    mData.clear();
                    mData.addAll(listBean);
                    // 注意使用notifyItemRangeChangedHF在下拉刷新的时候,由于之前clear会出现数据不同步问题
                    mAdapter.notifyDataSetChangedHF();
                    ptrClassicFrameLayout.refreshComplete();
                    ptrClassicFrameLayout.setLoadMoreEnable(true);
                } else {
                    int beforeChangeSize = mData.size() + mAdapter.getHeadSize() + 1;
                    mData.addAll(listBean);
                    mAdapter.notifyItemRangeInsertedHF(beforeChangeSize, listBean.size());
                    ptrClassicFrameLayout.loadMoreComplete(true);
                }
            }
        };
    }
}
