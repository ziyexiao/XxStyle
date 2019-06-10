package com.xx.style.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xx.style.R;
import com.xx.style.bean.LovingBean;
import com.xx.style.manager.GlideManager;

import java.util.List;

/**
 * Created by 52862 on 2018/8/13.
 */

public class LovingAdapter extends BaseQuickAdapter<LovingBean.DataBean, BaseViewHolder>{
    public LovingAdapter(int layoutResId, @Nullable List<LovingBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LovingBean.DataBean item) {
        helper.setText(R.id.tv_loving, item.title);
        GlideManager.loadImgCenter(item.bgUrl, (ImageView) helper.getView(R.id.iv_loving));
    }
}
