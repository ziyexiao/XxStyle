package com.xx.style.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xx.style.MainActivity;
import com.xx.style.R;

import java.util.List;

/**
 * Created by XX on 2018/8/8.
 */

public class MainRvAdapter extends BaseQuickAdapter<MainActivity.Item, BaseViewHolder>{
    public MainRvAdapter(int layoutResId, @Nullable List<MainActivity.Item> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainActivity.Item item) {
        helper.setText(R.id.tv_item_name, item.name)
                .setGone(R.id.tv_item_desc, false);
    }
}
