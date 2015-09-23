package com.jude.fishing.module.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.smssdk.gui.TimeListener;

/**
 * Created by Mr.Jude on 2015/9/13.
 */
@RequiresPresenter(RegisterVerifyPresenter.class)
public class RegisterVerifyActivity extends BeamBaseActivity<RegisterVerifyPresenter> implements TimeListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.code)
    EditText code;
    @InjectView(R.id.retry)
    TAGView retry;
    @InjectView(R.id.register)
    TAGView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_verifyreg);
        ButterKnife.inject(this);
        register.setOnClickListener(v -> checkInput());
        retry.setOnClickListener(v -> getPresenter().reSendMessage());
    }
    public void checkInput() {
        if (code.getText().toString().isEmpty()){
            JUtils.Toast("请输入验证码");
            return;
        }
        getPresenter().send(code.getText().toString(),"");
    }
    @Override
    public void onLastTimeNotify(int lastSecond) {
        if (lastSecond>0)
            retry.setText(lastSecond + "秒后重新发送");
        else
            retry.setText("重新发送");
    }

    @Override
    public void onAbleNotify(boolean valuable) {
        retry.setEnabled(valuable);
        retry.setBackgroundColor(getResources().getColor(valuable?R.color.blue:R.color.gray));
    }
}
