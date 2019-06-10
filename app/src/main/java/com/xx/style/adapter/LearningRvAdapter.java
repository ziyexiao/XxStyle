package com.xx.style.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xx.style.R;
import com.xx.style.view.learning.LearningActivity;

import java.util.List;

/**
 * Created by XX on 2018/8/8.
 *
 */

public class LearningRvAdapter extends BaseQuickAdapter<LearningActivity.Item, BaseViewHolder>{
    public LearningRvAdapter(int layoutResId, @Nullable List<LearningActivity.Item> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LearningActivity.Item item) {
        helper.setText(R.id.tv_item_name, item.title)
            .setGone(R.id.tv_item_desc, false);
    }
}
