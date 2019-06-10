package com.xx.style.view.learning;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.xx.style.R;
import com.xx.style.adapter.LearningListAdapter;
import com.xx.style.base.BaseActivity;
import com.xx.style.bean.NormalLearningBean;
import com.xx.style.utils.FileUtils;
import com.xx.style.utils.LogUtils;
import com.xx.style.utils.RecyclerViewUtils;

import butterknife.BindView;

/**
 * Created by XX on 2018/8/10.
 */

public class LearningListActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.rv_boke)
    RecyclerView mRvBoke;
    private NormalLearningBean mNormalLearningBean;

    @Override
    protected Toolbar getToolBar() {
        return mToolBar;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_learning_list;
    }

    @Override
    protected void initView() {

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String jsonName = intent.getStringExtra("jsonName");

        mToolBar.setTitle(title);

        String json = FileUtils.getJson(jsonName, this);

        Gson gson = new Gson();
        mNormalLearningBean = gson.fromJson(json, NormalLearningBean.class);

    }

    @Override
    protected void initData() {
        RecyclerViewUtils.getInstance().setLinearLayoutManager(mRvBoke, this);

        LearningListAdapter learningListAdapter = new LearningListAdapter(R.layout.item_main, mNormalLearningBean.data);

        mRvBoke.setAdapter(learningListAdapter);

        learningListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(LearningListActivity.this, LearningWebActivity.class);
                intent.putExtra("url", mNormalLearningBean.data.get(position).url);
                intent.putExtra("title", mNormalLearningBean.data.get(position).title);
                startActivity(intent);
            }
        });
    }
}
