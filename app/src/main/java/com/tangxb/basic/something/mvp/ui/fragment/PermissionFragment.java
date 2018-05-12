package com.tangxb.basic.something.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.basic.something.R;
import com.tangxb.basic.something.bean.RoleBean;
import com.tangxb.basic.something.decoration.MDividerItemDecoration;
import com.tangxb.basic.something.mvp.presenter.BasePresenter;
import com.tangxb.basic.something.mvp.presenter.PermissionFragmentPresenter;
import com.tangxb.basic.something.mvp.ui.activity.AssignPermissionActivity;
import com.tangxb.basic.something.mvp.view.PermissionFragmentView;
import com.tangxb.basic.something.util.ConstUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 分权限主界面<br>
 * Created by Administrator on 2017/12/24 0024.
 */

public class PermissionFragment extends BaseFragment implements PermissionFragmentView {
    private static final String FRAGMENT_NAME = "fragmentName";
    @BindView(R.id.recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private String fragmentName;
    private List<RoleBean> mDataList = new ArrayList<>();
    private CommonAdapter<RoleBean> commonAdapter;
    private PermissionFragmentPresenter presenter;
    /**
     * 注意这里mPageNum是从1开始的不是从0开始的
     */
    private int mPageNum = 1;
    private final int mPageSize = ConstUtils.PAGE_SIZE;
    private RecyclerAdapterWithHF mAdapter;

    public static PermissionFragment getInstance(String name) {
        PermissionFragment fragment = new PermissionFragment();
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
        presenter = new PermissionFragmentPresenter(this);
        return presenter;
    }

    @Override
    protected void receiveBundleFromActivity(Bundle arg) {
        if (arg != null) {
            fragmentName = arg.getString(FRAGMENT_NAME);
        }
    }

    @Override
    protected void initData() {
        commonAdapter = new CommonAdapter<RoleBean>(mActivity, R.layout.item_rv_permission, mDataList) {
            @Override
            protected void convert(ViewHolder holder, RoleBean bean, final int position) {
                holder.setText(R.id.tv_name, bean.getName());
                holder.setOnClickListener(R.id.rl_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemOnClick(position);
                    }
                });
            }
        };
        mAdapter = new RecyclerAdapterWithHF((MultiItemTypeAdapter) commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new MDividerItemDecoration(mActivity, LinearLayout.VERTICAL, R.drawable.item_for_divider));
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

    public void itemOnClick(int position) {
        Intent intent = new Intent(mActivity, AssignPermissionActivity.class);
        intent.putExtra("roleId", mDataList.get(position).getId());
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
        presenter.getRolerList(mApplication, mPageNum, mPageSize);
    }

    @Override
    public void loadMoreGetData() {
        mPageNum++;
        presenter.getRolerList(mApplication, mPageNum, mPageSize);
    }

    @Override
    public void resetPageNum() {
        mPageNum = 1;
    }

    @Override
    public void operateSuccess(List<RoleBean> baseBean) {
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
