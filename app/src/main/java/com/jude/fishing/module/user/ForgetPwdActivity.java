package com.jude.fishing.module.user;

import android.os.Bundle;
import android.widget.EditText;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.smssdk_mob.TimeListener;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

@RequiresPresenter(ForgetPwdPresenter.class)
public class ForgetPwdActivity extends BeamBaseActivity<ForgetPwdPresenter> implements TimeListener {

    @InjectView(R.id.et_phone)
    EditText phone;
    @InjectView(R.id.et_code)
    EditText code;
    @InjectView(R.id.tg_retry)
    TAGView retry;
    @InjectView(R.id.tg_ok)
    TAGView ok;
    @InjectView(R.id.et_password)
    EditText password;
    boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_forget_pwd);
        ButterKnife.inject(this);

        retry.setOnClickListener(v -> retry());
        ok.setOnClickListener(v -> forgetPwd());
    }

    private void forgetPwd() {
        if (code.getText().toString().isEmpty()) {
            JUtils.Toast("请输入验证码");
            return;
        }
        if (password.getText().toString().length() < 6 || password.getText().toString().length() > 12) {
            JUtils.Toast("请输入6-12位密码");
            return;
        }
        getPresenter().resetPass(code.getText().toString(),password.getText().toString());
    }

    // 重新获取验证码
    private void retry() {
        if (phone.getText().toString().trim().length() != 11) {
            JUtils.Toast("请输入正确手机号");
            return;
        }
        getPresenter().reSendMessage(phone.getText().toString().trim());
    }

    @Override
    public void onLastTimeNotify(int lastSecond) {
        if (lastSecond > 0) {
            isFirst = false;
            retry.setText(lastSecond + "秒后重新发送");
        } else if (!isFirst) {
            retry.setText("重新发送");
        }
    }

    @Override
    public void onAbleNotify(boolean valuable) {
        retry.setEnabled(valuable);
        retry.setBackgroundColor(getResources().getColor(valuable ? R.color.blue : R.color.gray));
    }
}
