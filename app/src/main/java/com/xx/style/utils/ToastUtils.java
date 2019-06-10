package com.xx.style.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xx.style.R;


/**
 * Created by XX on 2017/5/10.
 */

public class ToastUtils {
    private static Toast sToast;
    private static Toast centerToast;

    private static View view;
    private static TextView tvName;

    public static void showToast(Context context, String msg) {
        if (sToast == null) {
            //用ApplicationContext防止内存泄露
            sToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        //如果这个Toast已经在显示了，那么这里会立即改变Toast的文本
        sToast.setText(msg);
        sToast.show();
    }

    public static void showLongToast(Context context, String msg) {
        if (sToast == null) {
            //用ApplicationContext防止内存泄露
            sToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        }
        //如果这个Toast已经在显示了，那么这里会立即改变Toast的文本
        sToast.setText(msg);
        sToast.show();
    }

}
