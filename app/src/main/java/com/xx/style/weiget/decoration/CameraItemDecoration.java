package com.xx.style.weiget.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xx.style.utils.PixeUtils;

/**
 * Created by XX on 2018/8/11.
 *
 */

public class CameraItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public CameraItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.bottom = space;

        if (parent.getChildLayoutPosition(view) % 2 == 1) {
            outRect.right = space;
        }

        if (parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1) {
            outRect.top = space;
        }
    }
}
