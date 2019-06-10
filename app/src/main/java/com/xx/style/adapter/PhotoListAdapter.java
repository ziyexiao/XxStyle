package com.xx.style.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xx.style.R;
import com.xx.style.bean.CameraBean;
import com.xx.style.manager.GlideManager;

import java.util.List;

/**
 * Created by XX on 2018/8/15.
 */

public class PhotoListAdapter extends BaseQuickAdapter<CameraBean.DataBean, BaseViewHolder>{
    public PhotoListAdapter(int layoutResId, @Nullable List<CameraBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CameraBean.DataBean item) {
        GlideManager.loadImgCenter(item.url, (ImageView) helper.getView(R.id.iv_photo_list));
        helper.setText(R.id.tv_photo_list_desc, item.poetry);
    }
}
