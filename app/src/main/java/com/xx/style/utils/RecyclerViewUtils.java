package com.xx.style.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by xiaoxiao on 2018/5/15.
 */

public class RecyclerViewUtils {

    private volatile static RecyclerViewUtils mInstance;

    public static RecyclerViewUtils getInstance() {
        if (null == mInstance) {
            synchronized (RecyclerViewUtils.class) {
                if (null == mInstance) {
                    mInstance = new RecyclerViewUtils();
                }
            }
        }
        return mInstance;
    }

    public void setCantScrollGridLayoutManager(RecyclerView recyclerView, Activity activity, int count) {
        GridLayoutManager manager = new GridLayoutManager(activity, count) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);
    }

    public void setGridLayoutManager(RecyclerView recyclerView, Activity activity, int count) {
        GridLayoutManager manager = new GridLayoutManager(activity, count);

        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);
    }

    public void setLinearLayoutManager(RecyclerView recyclerView, Context activity) {
        LinearLayoutManager manager = new LinearLayoutManager(activity);

        manager.setOrientation(LinearLayoutManager.VERTICAL);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(manager);
        }
    }

    public void setHorizontalLayoutManager(RecyclerView recyclerView, Activity activity) {
        LinearLayoutManager manager = new LinearLayoutManager(activity);

        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(manager);
    }

    public void setCantScrollLinearLayoutManager(RecyclerView recyclerView, Context activity) {
        LinearLayoutManager manager = new LinearLayoutManager(activity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);
    }

}
