package com.xx.style.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xx.style.R;
import com.xx.style.base.BaseActivity;
import com.xx.style.conf.Constants;
import com.xx.style.utils.ToastUtils;
import com.xx.style.view.loving.LovingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by XX on 2018/8/11.
 */

public class LockingActivity extends BaseActivity {
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.tv_1)
    TextView mTv1;
    @BindView(R.id.tv_2)
    TextView mTv2;
    @BindView(R.id.tv_3)
    TextView mTv3;
    @BindView(R.id.tv_4)
    TextView mTv4;
    @BindView(R.id.tv_5)
    TextView mTv5;
    @BindView(R.id.tv_6)
    TextView mTv6;
    @BindView(R.id.tv_7)
    TextView mTv7;
    @BindView(R.id.tv_8)
    TextView mTv8;
    @BindView(R.id.tv_9)
    TextView mTv9;
    @BindView(R.id.tv_0)
    TextView mTv0;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;

    @Override
    protected Toolbar getToolBar() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_locking;
    }

    @Override
    protected void initView() {
        mTvDelete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mEtPwd.setText("");
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        mEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 7 && !mSpUtils.getString("loving").equals(s.toString())) {
                    ToastUtils.showToast(LockingActivity.this, "密码错误，滚！！！");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick({R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6, R.id.tv_7, R.id.tv_8, R.id.tv_9, R.id.tv_0, R.id.tv_delete, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                insertPwdByKey("1");
                break;
            case R.id.tv_2:
                insertPwdByKey("2");
                break;
            case R.id.tv_3:
                insertPwdByKey("3");
                break;
            case R.id.tv_4:
                insertPwdByKey("4");
                break;
            case R.id.tv_5:
                insertPwdByKey("5");
                break;
            case R.id.tv_6:
                insertPwdByKey("6");
                break;
            case R.id.tv_7:
                insertPwdByKey("7");
                break;
            case R.id.tv_8:
                insertPwdByKey("8");
                break;
            case R.id.tv_9:
                insertPwdByKey("9");
                break;
            case R.id.tv_0:
                insertPwdByKey("0");
                break;
            case R.id.tv_delete:
                deletePwdLastKey();
                break;
            case R.id.tv_exit:
                finish();
                overridePendingTransition(R.anim.activity_no_anim, R.anim.activity_bottom_out);
                break;
        }
    }

    /**
     * 删除最后一个Key
     */
    private void deletePwdLastKey() {
        String oldPwd = mEtPwd.getText().toString();
        String newPwd = null;
        if (oldPwd.length() > 0) {
            newPwd = oldPwd.substring(0, oldPwd.length() - 1);
        }

        mEtPwd.setText(newPwd);
    }

    /**
     * 添加一个key
     * @param key
     */
    private void insertPwdByKey(String key) {
        String pwd = mEtPwd.getText().toString() + key;

        mEtPwd.setText(pwd);

        if (mSpUtils.getString("loving").equals(pwd)) {
            Constants.LOCK = true;

            Intent intent = new Intent(LockingActivity.this, LovingActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.activity_top_in, R.anim.activity_no_anim);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_no_anim, R.anim.activity_bottom_out);
    }
}
