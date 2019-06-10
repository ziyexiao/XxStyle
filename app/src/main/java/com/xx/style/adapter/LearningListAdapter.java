package com.xx.style.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xx.style.R;
import com.xx.style.bean.NormalLearningBean;

import java.util.List;

/**
 * Created by XX on 2018/8/10.
 *
 */

public class LearningListAdapter extends BaseQuickAdapter<NormalLearningBean.DataBean, BaseViewHolder>{
    public LearningListAdapter(int layoutResId, @Nullable List<NormalLearningBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NormalLearningBean.DataBean item) {
        helper.setText(R.id.tv_item_name, item.title);
        if (item.desc != null) {
            helper.setText(R.id.tv_item_desc, item.desc);
        } else {
            helper.setGone(R.id.tv_item_desc, false);
        }
    }
}
