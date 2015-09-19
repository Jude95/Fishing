package com.jude.fishing.module.user;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.fishing.R;
import com.jude.fishing.model.bean.PersonDetail;
import com.jude.fishing.widget.LinearWrapContentRecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/18.
 */
public class UserDetailActivity extends BeamDataActivity<UserDetailPresenter, PersonDetail> {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.background)
    SimpleDraweeView background;
    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.blog)
    TextView blog;
    @InjectView(R.id.container_blog)
    LinearLayout containerBlog;
    @InjectView(R.id.attention)
    TextView attention;
    @InjectView(R.id.container_attention)
    LinearLayout containerAttention;
    @InjectView(R.id.fans)
    TextView fans;
    @InjectView(R.id.container_fans)
    LinearLayout containerFans;
    @InjectView(R.id.address)
    TextView address;
    @InjectView(R.id.age)
    TextView age;
    @InjectView(R.id.skill)
    TextView skill;
    @InjectView(R.id.blog_list)
    LinearWrapContentRecyclerView blogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_detail);
        ButterKnife.inject(this);
    }

    @Override
    public void setData(PersonDetail data) {
        if (data==null)getExpansion().showErrorPage();
        if (data.getBackground()==null)
            background.setImageURI(Uri.parse(data.getBackground()));
        name.setText(data.getName());
        avatar.setImageURI(Uri.parse(data.getAvatar()));
        attention.setText(data.getAttentionCount()+"");
        fans.setText(data.getFansCount()+"");
        blog.setText(data.getBlogCount()+"");
        address.setText(data.getAddress());
        age.setText(data.getAge()+"年");
        skill.setText(data.getSkill());
    }
}
