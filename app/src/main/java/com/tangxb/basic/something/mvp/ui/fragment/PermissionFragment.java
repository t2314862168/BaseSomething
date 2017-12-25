package com.tangxb.basic.something.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tangxb.basic.something.R;
import com.tangxb.basic.something.mvp.presenter.BasePresenter;
import com.tangxb.basic.something.mvp.presenter.PermissionFragmentPresenter;
import com.tangxb.basic.something.mvp.ui.activity.AssignPermissionActivity;
import com.tangxb.basic.something.mvp.view.PermissionFragmentView;
import com.zhy.adapter.recyclerview.CommonAdapter;
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
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private String fragmentName;
    private List<String> mDataList = new ArrayList<>();
    private CommonAdapter<String> commonAdapter;
    private PermissionFragmentPresenter presenter;

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
        commonAdapter = new CommonAdapter<String>(getActivity(), R.layout.item_rv_permission, mDataList) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                holder.setOnClickListener(R.id.rl_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemOnClick(position);
                    }
                });
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(commonAdapter);
        // 模拟数据
        for (int i = 0; i < 20; i++) {
            mDataList.add("str" + i);
        }
        commonAdapter.notifyDataSetChanged();
    }

    public void itemOnClick(int position) {
        Intent intent = new Intent(mActivity, AssignPermissionActivity.class);
        startActivity(intent);
    }
}
