package com.xx.style.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xx.style.R;


/**
 * Created by xiaoxiao.
 */

public class DialogUtils {
    private volatile static DialogUtils mInstance;
    private AlertDialog mDialog;

    public static DialogUtils getInstance() {
        if (null == mInstance) {
            synchronized (DialogUtils.class) {
                if (null == mInstance) {
                    mInstance = new DialogUtils();
                }
            }
        }
        return mInstance;
    }

    public void showLoginDialog(Context context, String desc) {
        //显示和登陆界面差不多的dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AudioDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        builder.setView(view);
        TextView tvDesc = view.findViewById(R.id.tv_desc);
        tvDesc.setText(desc);
        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public void dismissDialog() {

        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

}
