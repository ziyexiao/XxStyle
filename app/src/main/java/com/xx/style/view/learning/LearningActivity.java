package com.xx.style.view.learning;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xx.style.R;
import com.xx.style.adapter.LearningRvAdapter;
import com.xx.style.base.BaseActivity;
import com.xx.style.utils.RecyclerViewUtils;

import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by XX on 2018/8/8.
 *
 */

public class LearningActivity extends BaseActivity {
    @BindView(R.id.abl_leaning)
    AppBarLayout mAblLearning;
    @BindView(R.id.rv_leaning)
    RecyclerView mRvLearning;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.iv_leaning_bg)
    ImageView mIvLearningBg;
    @BindView(R.id.toolbar_leaning)
    Toolbar mToolbarLearning;
    private boolean changeBig;
    private Animation mSmallerAnimation;
    private Animation mBiggerAnimation;

    @Override
    protected Toolbar getToolBar() {
        return mToolbarLearning;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_leaning;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbarLearning);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mToolbarLearning.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningActivity.this, LearningDescActivity.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Pair pair = new Pair<>(mIvLearningBg, "learning");
                    ActivityOptionsCompat aoptions =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(LearningActivity.this, pair);
                    ActivityCompat.startActivity(LearningActivity.this, intent, aoptions.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

        //anim文件Animator
        animAnimation();

        //ObjectAnimator
//        startObjectAnimator();


        mAblLearning.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRange = appBarLayout.getTotalScrollRange();       //获取最大可滑动距离

                float fraction = 1f * (scrollRange + verticalOffset) / scrollRange;     //获取当前滑动的占比


                if (fraction < 0.2f  && !changeBig) {
//                    mFab.animate().scaleX(0).scaleY(0);     //将FloatingActionButton缩小为0
//                    objectAnimator.start();
//                    objectAnimator1.start();
                    mFab.startAnimation(mSmallerAnimation);
                    changeBig = true;
                } else if (fraction > 0.8f && changeBig) {
//                    mFab.animate().scaleX(1).scaleY(1);     //将FloatingActionButton重新变大
//                    mObjectAnimator2.start();
//                    mObjectAnimator3.start();
                    mFab.startAnimation(mBiggerAnimation);
                    changeBig = false;
                }
            }
        });
    }

    private void animAnimation() {
        mSmallerAnimation = AnimationUtils.loadAnimation(this, R.anim.view_scale_smaller);
        mSmallerAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFab.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mBiggerAnimation = AnimationUtils.loadAnimation(this, R.anim.view_scale_bigger);
        mBiggerAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mFab.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void startObjectAnimator() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mFab, "scaleX", 1.0f, 0f);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFab.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.setDuration(500);

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mFab, "scaleY", 1.0f, 0f);
        objectAnimator1.setDuration(500);

        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mFab, "scaleX", 0f, 1.0f);
        objectAnimator2.setDuration(500);
        objectAnimator2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mFab.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(mFab, "scaleY", 0f, 1.0f);
        objectAnimator3.setDuration(500);
    }

    @Override
    protected void initData() {
        RecyclerViewUtils.getInstance().setLinearLayoutManager(mRvLearning, this);

        LearningRvAdapter learningRvAdapter = new LearningRvAdapter(R.layout.item_main, Arrays.asList(Item.values()));
        mRvLearning.setAdapter(learningRvAdapter);

        learningRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Item item = Item.values()[position];

                Intent intent = new Intent(LearningActivity.this, LearningListActivity.class);
                intent.putExtra("jsonName", item.jsonName);
                intent.putExtra("title", item.title);
                startActivity(intent);

            }
        });

    }

    public enum Item {
        BoZhu("博主", "bozhu.json"),
        Boke("博客", "boke.json"),
        Github("Github", "github.json");

        public String title;
        public String jsonName;

        Item(String name, String jsonName) {
            this.title = name;
            this.jsonName = jsonName;
        }
    }



}
