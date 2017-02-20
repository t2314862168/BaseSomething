package com.tangxb.basic.something.adapter;

import com.tangxb.basic.something.R;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by Tangxb on 2016/12/3.
 */

public class MsgComingItemDelagate implements ItemViewDelegate<String> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.listitem_layout;
    }

    @Override
    public boolean isForViewType(String item, int position) {
        return position % 2 == 0;
    }

    @Override
    public void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.text1, s);
        holder.setTextColorRes(R.id.text1, R.color.colorPrimary);
    }
}
