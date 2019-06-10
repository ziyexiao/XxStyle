package com.xx.style.view.weiget;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xx.style.R;
import com.xx.style.adapter.WeigetVpAdapter;
import com.xx.style.base.BaseActivity;
import com.xx.style.base.BaseFragment;
import com.xx.style.utils.PixeUtils;
import com.xx.style.utils.TablayoutWidthUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by XX on 2018/8/31.
 */

public class WeigetActivity extends BaseActivity {

    @BindView(R.id.toolbar_weiget)
    Toolbar mToolbarWeiget;
    @BindView(R.id.tl_weiget)
    TabLayout mTlWeiget;
    @BindView(R.id.vp_weiget)
    ViewPager mVpWeiget;
  ;

    @Override
    protected Toolbar getToolBar() {
        return null;
    }

    @Override
    public void setStatusBarColor() {
        super.setStatusBarColor();
        mImmersionBar.statusBarView(R.id.view_weiget).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_weiget;
    }

    @Override
    protected void initView() {
        mToolbarWeiget.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TablayoutWidthUtils.reflex(mTlWeiget, PixeUtils.dip2px(20), PixeUtils.dip2px(12), PixeUtils.dip2px(0));


        String[] titles = new String[]{"RoundProgressBar", "RulerView", "RadarView"};

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new RoundProgressFragment());
        fragments.add(new RulerFragment());
        fragments.add(new RadarFragment());


        //给viewpager设置适配器
        WeigetVpAdapter adapter = new WeigetVpAdapter(getSupportFragmentManager(), titles, fragments);
        mVpWeiget.setAdapter(adapter);

        mVpWeiget.setOffscreenPageLimit(fragments.size());

        mTlWeiget.setupWithViewPager(mVpWeiget);
    }

    @Override
    protected void initData() {

    }

}
