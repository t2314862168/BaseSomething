package com.tangxb.basic.something.mvp.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.basic.something.R;
import com.tangxb.basic.something.bean.PurchaseUserBean;
import com.tangxb.basic.something.decoration.MDividerItemDecoration;
import com.tangxb.basic.something.mvp.presenter.BasePresenter;
import com.tangxb.basic.something.mvp.presenter.TaskFragmentPresenter;
import com.tangxb.basic.something.mvp.ui.activity.AssignTaskActivity;
import com.tangxb.basic.something.mvp.ui.activity.HomeActivity;
import com.tangxb.basic.something.mvp.view.TaskFragmentView;
import com.tangxb.basic.something.util.ConstUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 分任务主界面<br>
 * Created by Administrator on 2017/12/24 0024.
 */
public class TaskFragment extends BaseFragment implements TaskFragmentView {
    private static final String FRAGMENT_NAME = "fragmentName";
    @BindView(R.id.ll_container)
    LinearLayout mLinearLayout;
    @BindView(R.id.recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private String fragmentName;
    private List<PurchaseUserBean> mDataList = new ArrayList<>();
    private CommonAdapter<PurchaseUserBean> commonAdapter;
    private TaskFragmentPresenter presenter;
    /**
     * 注意这里mPageNum是从1开始的不是从0开始的
     */
    private int mPageNum = 1;
    private final int mPageSize = ConstUtils.PAGE_SIZE;
    private RecyclerAdapterWithHF mAdapter;

    public static TaskFragment getInstance(String name) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_simple;
    }

    @Override
    protected BasePresenter createPresenter() {
        presenter = new TaskFragmentPresenter(this);
        return presenter;
    }

    @Override
    protected void receiveBundleFromActivity(Bundle arg) {
        fragmentName = arg.getString(FRAGMENT_NAME);
    }

    @Override
    protected void initData() {
        commonAdapter = new CommonAdapter<PurchaseUserBean>(mActivity, R.layout.item_rv_task, mDataList) {
            @Override
            protected void convert(ViewHolder holder, PurchaseUserBean bean, final int position) {
                ImageView imageView = holder.getView(R.id.iv);
                String imageUrl = "http://p1.so.qhimgs1.com/t01587cbad79fdeaee1.jpg";
                mApplication.getImageLoaderFactory().loadCircleOrReboundImgByUrl(mFragment, imageUrl, imageView);
                holder.setOnClickListener(R.id.rl_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemOnClick(position);
                    }
                });
                holder.setText(R.id.tv_name, bean.getNickname());
                holder.setOnClickListener(R.id.iv_call_phone, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mobileOnClick(position);
                    }
                });
            }
        };
        mAdapter = new RecyclerAdapterWithHF((MultiItemTypeAdapter) commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayout.VERTICAL, R.drawable.item_for_divider));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mApplication.getImageLoaderFactory().resumeRequests(mFragment);
                } else {
                    mApplication.getImageLoaderFactory().pauseRequests(mFragment);
                }
            }
        });
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandlerEx() {
            @Override
            public void onRefreshBegin(PtrFrameLayoutEx frame) {
                refreshGetData();
            }
        });
        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                loadMoreGetData();
            }
        });
        ptrClassicFrameLayout.autoRefresh(true);
    }

    /**
     * 跳转到打电话界面
     *
     * @param position
     */
    public void mobileOnClick(int position) {
        try {
            String phoneNumber = mDataList.get(position).getMobile();
            Intent Intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + phoneNumber));
            startActivity(Intent);
        } catch (Exception e) {
            // 如果用户之前没有授予打电话权限,请重新授予
            if (mActivity instanceof HomeActivity) {
                ((HomeActivity) mActivity).storageTask();
            }
        }
    }

    /**
     * 跳转到分配任务界面
     *
     * @param position
     */
    public void itemOnClick(int position) {
        Intent intent = new Intent(mActivity, AssignTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void stopLoadData() {
        if (mPageNum == 1) { // 下拉刷新
            ptrClassicFrameLayout.refreshComplete();
            ptrClassicFrameLayout.setLoadMoreEnable(false);
        } else { // 上拉加载
            ptrClassicFrameLayout.loadMoreComplete(false);
        }
    }

    @Override
    public void refreshGetData() {
        resetPageNum();
        presenter.getPurchaseUserList(mApplication, mPageNum, mPageSize);
    }

    @Override
    public void loadMoreGetData() {
        mPageNum++;
        presenter.getPurchaseUserList(mApplication, mPageNum, mPageSize);
    }

    @Override
    public void resetPageNum() {
        mPageNum = 1;
    }

    @Override
    public void operateSuccess(List<PurchaseUserBean> baseBean) {
        if (mPageNum == 1) { // 下拉刷新
            mDataList.clear();
            if (baseBean != null && baseBean.size() > 0) {
                mDataList.addAll(baseBean);
            }
            mAdapter.notifyDataSetChangedHF();
            ptrClassicFrameLayout.refreshComplete();
            ptrClassicFrameLayout.setLoadMoreEnable(true);
            if (baseBean.size() < ConstUtils.PAGE_SIZE) {
                ptrClassicFrameLayout.loadMoreComplete(false);
            }
        } else { // 上拉加载
            int beforeChangeSize = mDataList.size() + mAdapter.getHeadSize() + 1;
            int getDataSize = (baseBean == null ? 0 : baseBean.size());
            if (getDataSize != 0) {
                mDataList.addAll(baseBean);
                mAdapter.notifyItemRangeInsertedHF(beforeChangeSize, getDataSize);
            }
            ptrClassicFrameLayout.loadMoreComplete(false);
        }
    }
}
