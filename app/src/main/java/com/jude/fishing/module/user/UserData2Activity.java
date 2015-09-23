package com.jude.fishing.module.user;

import android.os.Bundle;
import android.widget.EditText;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by heqiang on 2015/9/23.
 */
@RequiresPresenter(UserData2Presenter.class)
public class UserData2Activity extends BeamBaseActivity<UserData2Presenter> {

    @InjectView(R.id.et_age)
    EditText age;
    @InjectView(R.id.et_good_at)
    EditText goodAt;
    @InjectView(R.id.et_sign)
    EditText sign;
    @InjectView(R.id.tg_done)
    TAGView done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_data2);
        ButterKnife.inject(this);

        done.setOnClickListener(v -> checkInput());
    }

    private void checkInput() {
        int ageNum = Integer.valueOf(age.getText().toString().trim());
        if (ageNum < 0 || ageNum > 100) {
            JUtils.Toast("请输入正确的钓龄");
            return;
        }
        // TODO: 2015/9/23 是否为空检查
    }
}