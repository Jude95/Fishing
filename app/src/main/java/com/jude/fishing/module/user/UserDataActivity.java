package com.jude.fishing.module.user;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.fishing.R;
import com.jude.fishing.config.Constant;
import com.jude.fishing.model.LocationModel;
import com.jude.fishing.model.entities.Location;
import com.jude.fishing.model.entities.PersonDetail;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by heqiang on 2015/9/23.
 */
@RequiresPresenter(UserDataPresenter.class)
public class UserDataActivity extends BeamDataActivity<UserDataPresenter,PersonDetail> {

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
    TextView age;
    @InjectView(R.id.et_good_at)
    TextView skill;
    @InjectView(R.id.et_sign)
    EditText sign;
    @InjectView(R.id.tg_done)
    TAGView done;
    Uri avatarUri = null;
    String avatarStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_data);
        ButterKnife.inject(this);
        skill.setOnClickListener(v -> showSkillTypeEdit());
        age.setOnClickListener(v->showAgeTypeEdit());
        addPhoto.setOnClickListener(v -> showSelectorDialog());
        done.setOnClickListener(v -> checkInput());

    }

    private void checkInput() {
        if (avatarUri == null&&avatarStr.isEmpty()) {
            JUtils.Toast("请添加一张头像");
            return;
        }
        if (nickName.getText().toString().trim().isEmpty()) {
            JUtils.Toast("大虾，请来一个昵称");
            return;
        }
//        if (region.getText().toString().trim().isEmpty()) {
//            JUtils.Toast("请选择所在地区");
//            return;
//        }

        if (age.getText().toString().trim().isEmpty()) {
            JUtils.Toast("请选择钓龄");
            return;
        }
//        if (skill.getText().toString().trim().isEmpty()) {
//            JUtils.Toast("请输入您擅长的项目");
//            return;
//        }
//        if (sign.getText().toString().trim().isEmpty() && sign.getText().toString().trim().length() < 150) {
//            JUtils.Toast("请输入150字以内的签名");
//            return;
//        }
        getPresenter().sendUserData(nickName.getText().toString(),
                region.getText().toString(),
                rbMale.isChecked() ? 0 : 1,
                age.getText().toString(),
                skill.getText().toString(),
                sign.getText().toString(),
                avatarStr);
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
    public void setData(PersonDetail data) {
        avatarStr = data.getAvatar();
        avatar.setImageURI(Uri.parse(data.getAvatar()));
        nickName.setText(data.getName());
        if (data.getGender()==0)rbMale.setChecked(true);
        else rbFemale.setChecked(true);
        age.setText(data.getAge()+"");
        skill.setText(data.getSkill());
        sign.setText(data.getSign());
        if (TextUtils.isEmpty(data.getAddress())){
            Location location = LocationModel.getInstance().getCurLocation();
            region.setText(location.getCity()+location.getDistrict());
        }else{
            region.setText(data.getAddress());
        }
    }

    private void showSkillTypeEdit() {
        List<Integer> list = new ArrayList<>();
        for (String text:skill.getText().toString().split(","))
            for (int i = 0; i < Constant.SkillType.length; i++) {
                if (Constant.SkillType[i].equals(text)){
                    list.add(i);
                    break;
                }
            }
        new MaterialDialog.Builder(this)
                .title("请选择擅长")
                .items(Constant.SkillType)
                .itemsCallbackMultiChoice(list.toArray(new Integer[list.size()]), new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, Integer[] integers, CharSequence[] charSequences) {
                        if (integers.length > 0) {
                            String skillText = "";
                            for (Integer integer : integers) {
                                skillText += Constant.SkillType[integer]+",";
                            }
                            skillText = skillText.substring(0, skillText.length() - 1);
                            skill.setText(skillText);
                        }
                        return false;
                    }
                })
                .positiveText("确定")
                .show();
    }

    private void showAgeTypeEdit() {
        int index = 0;
        for (int i = 0; i < Constant.AgeType.length; i++) {
            if (Constant.AgeType[i].equals(age.getText())) {
                index = i;
            }
        }
        new MaterialDialog.Builder(this)
                .title("请选择钓龄")
                .items(Constant.AgeType)
                .itemsCallbackSingleChoice(index, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        age.setText(Constant.AgeType[i]);
                        return false;
                    }
                })
                .positiveText("确定")
                .show();
    }
}
