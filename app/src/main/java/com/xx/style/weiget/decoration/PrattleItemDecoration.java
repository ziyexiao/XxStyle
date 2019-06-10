package com.xx.style.weiget.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xx.style.utils.PixeUtils;

/**
 * Created by XX on 2018/8/11.
 *
 */

public class PrattleItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public PrattleItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = PixeUtils.dip2px(25);
        }
    }
}
