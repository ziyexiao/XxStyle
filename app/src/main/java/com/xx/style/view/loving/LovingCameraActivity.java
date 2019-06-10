package com.xx.style.view.loving;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.xx.style.R;
import com.xx.style.adapter.CameraAdapter;
import com.xx.style.base.BaseActivity;
import com.xx.style.bean.CameraBean;
import com.xx.style.utils.FileUtils;
import com.xx.style.utils.PixeUtils;
import com.xx.style.weiget.decoration.CameraItemDecoration;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 52862 on 2018/8/13.
 * 相册
 */

public class LovingCameraActivity extends BaseActivity {
    @BindView(R.id.rv_loving_camera)
    RecyclerView mRvLovingCamera;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    private CameraBean mCameraBean;


    @Override
    protected Toolbar getToolBar() {
        return mToolBar;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_loving_camera;
    }

    @Override
    protected void initView() {
        mToolBar.setTitle(R.string.photo_list);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String jsonName = intent.getStringExtra("jsonName");
        String json = FileUtils.getJson(jsonName, this);
        mCameraBean = new Gson().fromJson(json, CameraBean.class);

        //设置layoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRvLovingCamera.setLayoutManager(gridLayoutManager);

        //设置分割线
        CameraItemDecoration cameraItemDecoration = new CameraItemDecoration(PixeUtils.dip2px(5));
        mRvLovingCamera.addItemDecoration(cameraItemDecoration);
    }

    @Override
    protected void initData() {
        CameraAdapter cameraAdapter = new CameraAdapter(R.layout.item_camera, mCameraBean.data);
        mRvLovingCamera.setAdapter(cameraAdapter);

        cameraAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(LovingCameraActivity.this, PhotoListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (Serializable) mCameraBean.data);
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
