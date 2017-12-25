package com.tangxb.basic.something.mvp.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tangxb.basic.something.R;
import com.tangxb.basic.something.mvp.presenter.AssignPermissionActivityPresenter;
import com.tangxb.basic.something.mvp.presenter.BasePresenter;
import com.tangxb.basic.something.mvp.view.AssignPermissionActivityView;
import com.tangxb.basic.something.util.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Taxngb on 2017/12/25.
 */

public class AssignPermissionActivity extends BaseActivity implements AssignPermissionActivityView {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private AssignPermissionActivityPresenter presenter;
    private List<String> mDataList = new ArrayList<>();
    private CommonAdapter<String> commonAdapter;

    @Override
    protected BasePresenter createPresenter() {
        presenter = new AssignPermissionActivityPresenter(this);
        return presenter;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_assign_permission;
    }

    @Override
    protected void initData() {
        commonAdapter = new CommonAdapter<String>(this, R.layout.item_rv_assign_permission, mDataList) {
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(commonAdapter);
        // 模拟数据
        for (int i = 0; i < 20; i++) {
            mDataList.add("str" + i);
        }
        commonAdapter.notifyDataSetChanged();
    }
}
