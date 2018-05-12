package com.tangxb.basic.something.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.basic.something.R;
import com.tangxb.basic.something.bean.PurchaseGroupBean;
import com.tangxb.basic.something.decoration.MDividerItemDecoration;
import com.tangxb.basic.something.mvp.presenter.AssignTaskActivityPresenter;
import com.tangxb.basic.something.mvp.presenter.BasePresenter;
import com.tangxb.basic.something.mvp.view.AssignTaskActivityView;
import com.tangxb.basic.something.util.ConstUtils;
import com.tangxb.basic.something.view.AlertProgressDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class AssignTaskActivity extends BaseActivity implements AssignTaskActivityView {
    @BindView(R.id.recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    /**
     * 标题文字
     */
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    /**
     * 全选按钮
     */
    @BindView(R.id.iv_check_all)
    ImageView mCheckAllIv;
    /**
     * 全选文字
     */
    @BindView(R.id.tv_check_tip)
    TextView mCheckAllTv;
    /**
     * 提交按钮
     */
    @BindView(R.id.tv_commit)
    TextView mCommitTv;
    private AssignTaskActivityPresenter presenter;
    private List<PurchaseGroupBean> mDataList = new ArrayList<>();
    private CommonAdapter<PurchaseGroupBean> commonAdapter;
    private RecyclerAdapterWithHF mAdapter;
    /**
     * 注意这里mPageNum是从1开始的不是从0开始的
     */
    private int mPageNum = 1;
    private final int mPageSize = ConstUtils.PAGE_SIZE;
    private long userId;
    private AlertDialog mAlertDialog;

    @Override
    protected BasePresenter createPresenter() {
        presenter = new AssignTaskActivityPresenter(this);
        return presenter;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_assign_task;
    }

    @Override
    protected void receivePassDataIfNeed(Intent intent) {
        userId = intent.getLongExtra("userId", -1L);
    }

    @Override
    protected void initData() {
        mTitleTv.setText(R.string.choose_purchase_group);
        int resId = R.drawable.ic_check_box_outline_blank_grey_700_24dp;
        mCheckAllIv.setImageResource(resId);
        StatusBarCompat.setStatusBarColor(mActivity, mResources.getColor(R.color.material_red_400));
        commonAdapter = new CommonAdapter<PurchaseGroupBean>(this, R.layout.item_rv_assign_task, mDataList) {
            @Override
            protected void convert(ViewHolder holder, PurchaseGroupBean bean, final int position) {
                holder.setText(R.id.tv_name, bean.getNickname());
                int resId = bean.isCheck() ? R.drawable.ic_check_box_red_400_24dp : R.drawable.ic_check_box_outline_blank_grey_700_24dp;
                holder.setImageResource(R.id.iv_check, resId);
                holder.setOnClickListener(R.id.rl_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickItem(position);
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

    /**
     * 点击item
     *
     * @param position
     */
    public void clickItem(int position) {
        boolean check = mDataList.get(position).isCheck();
        mDataList.get(position).setCheck(!check);
        commonAdapter.notifyItemChanged(position);
        changeCheckAllStatus();
    }

    /**
     * 点击全选
     *
     * @param view
     */
    @OnClick(R.id.iv_check_all)
    public void clickChooseAll(View view) {
        // 默认选中了全部
        boolean hasCheckAll = true;
        for (PurchaseGroupBean bean : mDataList) {
            if (!bean.isCheck()) {
                hasCheckAll = false;
                break;
            }
        }
        hasCheckAll = !hasCheckAll;
        int resId = hasCheckAll ? R.drawable.ic_check_box_red_400_24dp : R.drawable.ic_check_box_outline_blank_grey_700_24dp;
        mCheckAllIv.setImageResource(resId);
        mCheckAllTv.setText(hasCheckAll ? "全不选" : "全选");

        for (PurchaseGroupBean bean : mDataList) {
            bean.setCheck(hasCheckAll);
        }
        mAdapter.notifyDataSetChangedHF();
    }

    /**
     * 改变全选状态
     *
     * @return
     */
    private void changeCheckAllStatus() {
        boolean hasCheckAll = true;
        for (PurchaseGroupBean bean : mDataList) {
            if (!bean.isCheck()) {
                hasCheckAll = false;
                break;
            }
        }
        int resId = hasCheckAll ? R.drawable.ic_check_box_red_400_24dp : R.drawable.ic_check_box_outline_blank_grey_700_24dp;
        mCheckAllIv.setImageResource(resId);
        mCheckAllTv.setText(hasCheckAll ? "全不选" : "全选");
    }

    /**
     * 点击提交
     *
     * @param view
     */
    @OnClick(R.id.tv_commit)
    public void clickCommit(View view) {
        presenter.clickCommit();
    }

    /**
     * 点击取消
     *
     * @param view
     */
    @OnClick(R.id.tv_cancel)
    public void clickCancel(View view) {
        finish();
    }

    @Override
    public void showDialog() {
        mAlertDialog = new AlertProgressDialog.Builder(mActivity)
                .setView(R.layout.layout_alert_dialog)
                .setCancelable(false)
                .setMessage(R.string.commit_data_ing)
                .show();
        ArrayList<PurchaseGroupBean> tempList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        JSONObject object;
        try {
            for (PurchaseGroupBean bean : mDataList) {
                if (bean.isCheck()) {
                    object = new JSONObject();
                    object.put("id", bean.getId());
                    jsonArray.put(object);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String groups = jsonArray.toString();
        presenter.commitData(mApplication, userId + "", groups);
    }

    @Override
    public void commitSuccess() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
        finish();
    }

    @Override
    public void commitFailed() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
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
        presenter.getPurchaseGroupList(mApplication, userId, mPageNum, mPageSize);
    }

    @Override
    public void loadMoreGetData() {
        mPageNum++;
        presenter.getPurchaseGroupList(mApplication, userId, mPageNum, mPageSize);
    }

    @Override
    public void resetPageNum() {
        mPageNum = 1;
    }

    @Override
    public void operateSuccess(List<PurchaseGroupBean> baseBean) {
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
        changeCheckAllStatus();
    }
}
