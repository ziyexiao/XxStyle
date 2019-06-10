package com.xx.style.view.loving;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.xx.style.R;
import com.xx.style.adapter.PhotoListAdapter;
import com.xx.style.base.BaseActivity;
import com.xx.style.bean.CameraBean;
import com.xx.style.conf.ItemConfig;
import com.xx.style.listener.OnSlideListener;
import com.xx.style.weiget.layoutmanager.ItemTouchHelperCallback;
import com.xx.style.weiget.layoutmanager.SlideLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by XX on 2018/8/15.
 */

public class PhotoListActivity extends BaseActivity {
    @BindView(R.id.rv_photo_list)
    RecyclerView mRvPhotoList;
    private ItemTouchHelperCallback mItemTouchHelperCallback;
    private List<CameraBean.DataBean> mDataBeans;
    private List<CameraBean.DataBean> mShowData = new ArrayList<>();
    private PhotoListAdapter mPhotoListAdapter;


    @Override
    protected Toolbar getToolBar() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_list;
    }

    @Override
    protected void initView() {

        int position = getIntent().getIntExtra("position", 0);

        mDataBeans = (List<CameraBean.DataBean>) getIntent().getSerializableExtra("data");
        for (int i = 0; i < mDataBeans.size(); i++) {
            if (i >= position) {
                mShowData.add(mDataBeans.get(i));
            }
        }

        if (mShowData.size() < 3) {
            mShowData.addAll(mDataBeans);
        }

        mPhotoListAdapter = new PhotoListAdapter(R.layout.item_photo_list, mShowData);

        mRvPhotoList.setAdapter(mPhotoListAdapter);

        mItemTouchHelperCallback = new ItemTouchHelperCallback(mRvPhotoList.getAdapter(), mShowData);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mItemTouchHelperCallback);
        SlideLayoutManager slideLayoutManager = new SlideLayoutManager(mRvPhotoList, itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mRvPhotoList);
        mRvPhotoList.setLayoutManager(slideLayoutManager);
    }

    @Override
    protected void initData() {
        mItemTouchHelperCallback.setOnSlideListener(new OnSlideListener() {
            @Override
            public void onSliding(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {

            }

            @Override
            public void onSlided(RecyclerView.ViewHolder viewHolder, Object o, int direction) {
                if (direction == ItemConfig.SLIDING_LEFT) {
//                    LogUtils.i("左");
                } else if (direction == ItemConfig.SLIDING_RIGHT) {
//                    LogUtils.i("右");
                }


                if (viewHolder.getAdapterPosition() <= mShowData.size() - 3) {
                    mShowData.addAll(mDataBeans);

                    mPhotoListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onClear() {
            }
        });
    }

}
