package com.xx.style.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xx.style.R;
import com.xx.style.bean.PrattleBean;

import java.util.List;

/**
 * Created by XX on 2018/8/11.
 */

public class PrattleAdapter extends BaseQuickAdapter<PrattleBean.DataBean, BaseViewHolder>{
    public PrattleAdapter(int layoutResId, @Nullable List<PrattleBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PrattleBean.DataBean item) {


        helper
                .setText(R.id.tv_parttle, item.desc);
    }
}
