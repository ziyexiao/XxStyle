package com.xx.style;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xx.style.adapter.MainRvAdapter;
import com.xx.style.base.BaseActivity;
import com.xx.style.conf.Constants;
import com.xx.style.manager.GlideManager;
import com.xx.style.utils.RecyclerViewUtils;
import com.xx.style.view.learning.LearningActivity;
import com.xx.style.view.loving.LovingActivity;
import com.xx.style.view.user.LockingActivity;
import com.xx.style.view.weiget.WeigetActivity;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_main)
    RecyclerView mRvMain;
    @BindView(R.id.main_toolbar)
    Toolbar mMainToolBar;
    @BindView(R.id.iv_main_bg)
    ImageView mIvMainBg;

    @Override
    protected Toolbar getToolBar() {
        return mMainToolBar;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        setSwipeBackEnable(false);

        GlideManager.loadImgCenter("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535719581489&di=2e583e643ade011b4a50e30420a3c334&imgtype=0&src=http%3A%2F%2Fsrc.leju.com%2Fimp%2Fimp%2Fdeal%2Fdd%2Ffa%2Fb%2F184dccfcf8a39fda6bb7e77c7ce_p24_mk24.jpg", mIvMainBg);

        RecyclerViewUtils.getInstance().setLinearLayoutManager(mRvMain, this);
    }

    @Override
    protected void initData() {
        MainRvAdapter mainRvAdapter = new MainRvAdapter(R.layout.item_main, Arrays.asList(Item.values()));
        mRvMain.setAdapter(mainRvAdapter);

        mainRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Item item = Item.values()[position];
                Intent intent;

                if (item.clazz.equals(LockingActivity.class) && Constants.LOCK) {   //如果未解锁
                    intent = new Intent(MainActivity.this, LovingActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, item.clazz);
                }
                startActivity(intent);

                overridePendingTransition(R.anim.activity_bottom_in, R.anim.activity_no_anim);
            }
        });
    }


    public enum Item {
        Leaning("Learning", LearningActivity.class),
        Loving("Loving", LockingActivity.class),
        Weiget("Weiget", WeigetActivity.class),;

        public String name;
        public Class<?> clazz;

        Item(String name, Class<?> clazz) {
            this.name = name;
            this.clazz = clazz;
        }
    }
}
