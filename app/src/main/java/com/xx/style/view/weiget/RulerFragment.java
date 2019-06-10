package com.xx.style.view.weiget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xx.style.R;
import com.xx.style.base.BaseFragment;
import com.xx.style.weiget.view.rullerview.RulerValuePicker;
import com.xx.style.weiget.view.rullerview.RulerValuePickerListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by XX on 2018/9/3.
 */

public class RulerFragment extends BaseFragment {
    @BindView(R.id.rvp_weiget)
    RulerValuePicker mRvpWeiget;
    @BindView(R.id.tv_num)
    TextView mTvNum;

    @Override
    protected void initView() {
        mRvpWeiget.selectValue(160);

        mRvpWeiget.setIndicatorHeight(0.4f, 0.2f);

        mRvpWeiget.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {

            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                mTvNum.setText(String.format("%scm", String.valueOf(selectedValue)));
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ruler;
    }

}
