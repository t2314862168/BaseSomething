package com.tangxb.basic.something.mvp.ui.activity;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayoutEx;
import com.chanven.lib.cptr.PtrDefaultHandlerEx;
import com.chanven.lib.cptr.PtrFrameLayoutEx;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.tangxb.basic.something.R;
import com.tangxb.basic.something.mvp.presenter.AssignTaskActivityPresenter;
import com.tangxb.basic.something.mvp.presenter.BasePresenter;
import com.tangxb.basic.something.mvp.view.AssignTaskActivityView;
import com.tangxb.basic.something.util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
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
     * 提交按钮
     */
    @BindView(R.id.tv_commit)
    TextView mCommitTv;
    private AssignTaskActivityPresenter presenter;
    private List<String> mDataList = new ArrayList<>();
    private CommonAdapter<String> commonAdapter;
    private int i = -1;
    private SweetAlertDialog sweetAlertDialog;
    private Handler handler = new Handler();
    private int pageNum = 0;
    private RecyclerAdapterWithHF mAdapter;

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
    protected void initData() {
        StatusBarCompat.setStatusBarColor(mActivity, mResources.getColor(R.color.material_red_400));
        commonAdapter = new CommonAdapter<String>(this, R.layout.item_rv_assign_task, mDataList) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                holder.setOnClickListener(R.id.rl_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.t(mApplication, "你点击了的item-" + position);
                    }
                });
            }
        };
        mAdapter = new RecyclerAdapterWithHF((MultiItemTypeAdapter) commonAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);

        ptrClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 200);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandlerEx() {

            @Override
            public void onRefreshBegin(PtrFrameLayoutEx frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNum = 0;
                        mDataList.clear();
                        // 模拟数据
                        for (int i = 0; i < 10; i++) {
                            mDataList.add("str" + i);
                        }
                        // 注意使用notifyItemRangeChangedHF在下拉刷新的时候,由于之前clear会出现数据不同步问题
                        mAdapter.notifyDataSetChangedHF();
                        ptrClassicFrameLayout.refreshComplete();
                        ptrClassicFrameLayout.setLoadMoreEnable(true);
                    }
                }, 3500);
            }
        });
        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        int beforeChangeSize = mDataList.size() + mAdapter.getHeadSize() + 1;
                        int size = 5;
                        for (int i = 0; i < size; i++) {
                            mDataList.add(new String("  RecyclerView item  - add " + pageNum));
                        }
                        mAdapter.notifyItemRangeInsertedHF(beforeChangeSize, size);
                        ptrClassicFrameLayout.loadMoreComplete(false);
                    }
                }, 1000);
            }
        });
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

    @Override
    public void showDialog() {
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("提交数据中...");
        sweetAlertDialog.show();
        sweetAlertDialog.setCancelable(false);
        new CountDownTimer(800 * 3, 800) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i++;
                switch (i) {
                    case 0:
                        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 2:
                        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                        break;
                    case 3:
                        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                        break;
                    case 4:
                        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                        break;
                    case 5:
                        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                        break;
                    case 6:
                        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                        break;
                }
            }

            public void onFinish() {
                i = -1;
                sweetAlertDialog.setTitleText("操作成功")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }
        }.start();
    }

    public void commitSuccess() {
        mCommitTv.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000L);
    }
}
