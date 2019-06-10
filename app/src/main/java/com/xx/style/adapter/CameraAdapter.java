package com.xx.style.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xx.style.R;
import com.xx.style.bean.CameraBean;
import com.xx.style.manager.GlideManager;

import java.util.List;

/**
 * Created by XX on 2018/8/13.
 *
 */

public class CameraAdapter extends BaseQuickAdapter<CameraBean.DataBean, BaseViewHolder>{
    public CameraAdapter(int layoutResId, List<CameraBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CameraBean.DataBean item) {
        GlideManager.loadImgCenter(item.url, (ImageView) helper.getView(R.id.iv_camera));
    }
}

