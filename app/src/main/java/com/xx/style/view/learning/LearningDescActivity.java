package com.xx.style.view.learning;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.xx.style.R;
import com.xx.style.adapter.PrattleAdapter;
import com.xx.style.base.BaseActivity;
import com.xx.style.bean.PrattleBean;
import com.xx.style.utils.FileUtils;
import com.xx.style.utils.PixeUtils;
import com.xx.style.utils.RecyclerViewUtils;
import com.xx.style.weiget.decoration.PrattleItemDecoration;

import butterknife.BindView;

/**
 * Created by XX on 2018/8/11.
 */

public class LearningDescActivity extends BaseActivity {
    @BindView(R.id.rv_prattle)
    RecyclerView mRvPrattle;
    private PrattleBean mPrattleBean;

    @Override
    public void setStatusBarColor() {
        super.setStatusBarColor();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected Toolbar getToolBar() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_learning_desc;
    }

    @Override
    protected void initView() {
        String json = FileUtils.getJson("prattle.json", this);

        Gson gson = new Gson();
        mPrattleBean = gson.fromJson(json, PrattleBean.class);

        RecyclerViewUtils.getInstance().setLinearLayoutManager(mRvPrattle, this);
        PrattleItemDecoration prattleItemDecoration = new PrattleItemDecoration(PixeUtils.dip2px(20));
        mRvPrattle.addItemDecoration(prattleItemDecoration);
    }

    @Override
    protected void initData() {
        PrattleAdapter prattleAdapter = new PrattleAdapter(R.layout.item_prattle, mPrattleBean.data);
        mRvPrattle.setAdapter(prattleAdapter);

        prattleAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
    }

}
