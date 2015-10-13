package com.jude.fishing.module.user;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
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
@RequiresPresenter(UserDataPresenter.class)
public class UserDataActivity extends BeamBaseActivity<UserDataPresenter> {

    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.tg_add)
    TAGView addPhoto;
    @InjectView(R.id.et_name)
    EditText nickName;
    @InjectView(R.id.et_region)
    EditText region;
    @InjectView(R.id.rg_sex)
    RadioGroup sexGroup;
    @InjectView(R.id.rb_male)
    RadioButton rbMale;
    @InjectView(R.id.rb_female)
    RadioButton rbFemale;
    @InjectView(R.id.et_age)
    EditText age;
    @InjectView(R.id.et_good_at)
    EditText skill;
    @InjectView(R.id.et_sign)
    EditText sign;
    @InjectView(R.id.tg_done)
    TAGView done;
    Uri avatarUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_data);
        ButterKnife.inject(this);

        addPhoto.setOnClickListener(v -> showSelectorDialog());
        done.setOnClickListener(v -> checkInput());
    }

    private void checkInput() {
        if (avatarUri == null) {
            JUtils.Toast("请添加一张头像");
            return;
        }
        if (nickName.getText().toString().trim().isEmpty()) {
            JUtils.Toast("大虾，请来一个昵称");
            return;
        }
        if (region.getText().toString().trim().isEmpty()) {
            JUtils.Toast("请选择所在地区");
            return;
        }
        if (age.getText().toString().trim().isEmpty()) {
            JUtils.Toast("请输入您的钓龄");
            return;
        }
        int ageNum = Integer.valueOf(age.getText().toString().trim());
        if (ageNum < 0 || ageNum > 100) {
            JUtils.Toast("请输入正确的钓龄");
            return;
        }
        if (skill.getText().toString().trim().isEmpty()) {
            JUtils.Toast("请输入您擅长的项目");
            return;
        }
        if (sign.getText().toString().trim().isEmpty() && sign.getText().toString().trim().length() < 150) {
            JUtils.Toast("请输入150字以内的签名");
            return;
        }
        getPresenter().sendUserData(nickName.getText().toString().trim(),
                region.getText().toString().trim(),
                rbMale.isChecked() ? 1 : 0,
                ageNum,
                skill.getText().toString().trim(),
                sign.getText().toString().trim());
    }

    public void showSelectorDialog() {
        new MaterialDialog.Builder(this)
                .title("选择图片来源")
                .items(new String[]{"拍照", "相册", "网络"})
                .itemsCallback((materialDialog, view, i, charSequence) -> getPresenter().editFace(i)).show();
    }

    public void setAvatar(Uri uri) {
        avatarUri = uri;
        avatar.setImageURI(uri);
    }
}
