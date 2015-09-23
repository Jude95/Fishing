package com.jude.fishing.module.user;

import android.content.Intent;
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
public class UserDataActivity extends BeamBaseActivity<UserDataPresenter> implements RadioGroup.OnCheckedChangeListener {

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
    @InjectView(R.id.tg_next)
    TAGView next;
    Uri avatarUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_data);
        ButterKnife.inject(this);

        addPhoto.setOnClickListener(v -> showSelectorDialog());
        sexGroup.setOnCheckedChangeListener(this);
        next.setOnClickListener(v -> checkInput());
    }

    private void checkInput() {
        if (nickName.getText().toString().trim().isEmpty()) {
            JUtils.Toast("大虾，请来一个昵称");
            return;
        }
        if (region.getText().toString().trim().isEmpty()) {
            JUtils.Toast("请选择所在地区");
            return;
        }
        Intent intent = new Intent(this, UserData2Activity.class);
        intent.putExtra("nick", nickName.getText().toString().trim());
        intent.putExtra("region", region.getText().toString().trim());
        intent.putExtra("isMale", rbMale.isChecked());
        // TODO: 2015/9/23 什么时间做图片上传
        startActivity(intent);
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_male) {
            sexGroup.check(R.id.rb_male);
        } else {
            sexGroup.check(R.id.rb_female);
        }
    }
}
