package com.jude.fishing.module.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.fishing.R;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.entities.PersonDetail;
import com.jude.fishing.model.entities.Seed;
import com.jude.fishing.module.blog.BlogDetailActivity;
import com.jude.fishing.module.blog.UserBlogActivity;
import com.jude.fishing.utils.RecentDateFormat;
import com.jude.fishing.widget.LinearWrapContentRecyclerView;
import com.jude.fishing.widget.ObservableScrollView;
import com.jude.tagview.TAGView;
import com.jude.utils.JTimeTransform;
import com.jude.utils.JUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/18.
 */
@RequiresPresenter(UserDetailPresenter.class)
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
    @InjectView(R.id.sign)
    TextView sign;
    @InjectView(R.id.skill)
    TextView skill;
    @InjectView(R.id.blog_list)
    LinearWrapContentRecyclerView blogList;
    @InjectView(R.id.operation)
    LinearLayout operation;
    @InjectView(R.id.scrollView)
    ObservableScrollView scrollView;
    @InjectView(R.id.head)
    RelativeLayout head;
    @InjectView(R.id.ll_attention)
    LinearLayout attentionOperation;
    @InjectView(R.id.tv_op_attention)
    TextView tvOpAttention;
    @InjectView(R.id.ll_chat)
    LinearLayout chat;

    private boolean isAttended;
    private Drawable mActionbarDrawable;

    int id;
    String userName;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_detail);
        ButterKnife.inject(this);
        attentionOperation.setOnClickListener(v -> getPresenter().changeAttention(isAttended));
        id = getIntent().getIntExtra("id", 0);
        getExpansion().showProgressPage();
        mActionbarDrawable = new ColorDrawable(getResources().getColor(R.color.blue));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            head.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,JUtils.dip2px(285)+JUtils.getStatusBarHeight()));
        }
        getToolbar().setBackgroundDrawable(mActionbarDrawable);
        setToolbarAlpha(0);
        scrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y > head.getHeight() - toolbar.getHeight()) {
                    setToolbarAlpha(1);
                } else {
                    setToolbarAlpha(0);
                }
            }
        });
        containerAttention.setOnClickListener(v -> getPresenter().goToActivityWithLogin(AttentionActivity.class, uid));
        containerBlog.setOnClickListener(v -> getPresenter().goToActivity(UserBlogActivity.class,uid));
        containerFans.setOnClickListener(v -> getPresenter().goToActivityWithLogin(FansActivity.class, uid));
        chat.setOnClickListener(v->getPresenter().chat(userName));
    }

    public void changeAttention() {
        isAttended = !isAttended;
        tvOpAttention.setText(isAttended ? "已关注" : "关注");
    }

    protected void setToolbarAlpha(float alpha) {
        float curalpna = Math.max(Math.min(alpha, 1), 0);
        mActionbarDrawable.setAlpha((int) (curalpna * 255));
    }

    @Override
    public void setData(PersonDetail data) {
        getExpansion().dismissProgressPage();
        if (data == null) getExpansion().showErrorPage();
        if (data.getBackground() != null)
            background.setImageURI(ImageModel.getInstance().getLargeImage(data.getBackground()));
        userName = data.getName();
        uid = data.getUID();
        name.setText(data.getName());
        avatar.setImageURI(Uri.parse(data.getAvatar()));
        attention.setText(data.getCared());
        sign.setText(data.getSign());
        fans.setText(data.getFans());
        blog.setText(data.getBlogCount() + "");
        address.setText(data.getAddress());
        age.setText(data.getAge());
        skill.setText(data.getSkill());
        if (id==0) {
            operation.setVisibility(View.GONE);
            background.setOnClickListener(v -> showSelectorDialog());
        } else {
            isAttended = data.getRelation();
            tvOpAttention.setText(isAttended ? "已关注" : "关注");
        }
        blogList.setAdapter(new BlogSimpleAdapter(this, data.getSeeds()));
    }

    class BlogSimpleAdapter extends RecyclerArrayAdapter<Seed> {

        public BlogSimpleAdapter(Context context, List<Seed> objects) {
            super(context, objects);
            JUtils.Log("SeedList:"+objects.size());
        }


        @Override
        public int getViewType(int position) {
            return getItem(position).getImages().length == 0 ? 1 : 2;
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return viewType == 1 ? new BlogTextViewHolder(parent) : new BlogImageViewHolder(parent);
        }
    }

    class BlogImageViewHolder extends BaseViewHolder<Seed> {

        @InjectView(R.id.preview)
        SimpleDraweeView preview;
        @InjectView(R.id.content)
        TextView content;
        @InjectView(R.id.address)
        TextView address;
        @InjectView(R.id.date)
        TextView date;
        @InjectView(R.id.tool)
        LinearLayout tool;

        private int id;

        public BlogImageViewHolder(ViewGroup parent) {
            super(parent, R.layout.blog_item_simple_image);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(v -> {
                Intent i = new Intent(v.getContext(), BlogDetailActivity.class);
                i.putExtra("id", id);
                v.getContext().startActivity(i);
            });
        }

        @Override
        public void setData(Seed data) {
            id = data.getId();
            preview.setImageURI(ImageModel.getInstance().getSmallImage(data.getImages()[0]));
            content.setText(data.getContent());
            address.setText(data.getAddress());
            date.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
        }
    }

    class BlogTextViewHolder extends BaseViewHolder<Seed> {

        @InjectView(R.id.preview)
        TAGView preview;
        @InjectView(R.id.content)
        TextView content;
        @InjectView(R.id.address)
        TextView address;
        @InjectView(R.id.date)
        TextView date;
        @InjectView(R.id.tool)
        LinearLayout tool;

        private int id;

        public BlogTextViewHolder(ViewGroup parent) {
            super(parent, R.layout.blog_item_simple_text);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(v -> {
                Intent i = new Intent(v.getContext(), BlogDetailActivity.class);
                i.putExtra("id", id);
                v.getContext().startActivity(i);
            });
        }

        @Override
        public void setData(Seed data) {
            id = data.getId();
            preview.setText(data.getContent().charAt(0) + "");
            content.setText(data.getContent());
            address.setText(data.getAddress());
            date.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_user_data, menu);
        if (0 != id) {
            menu.findItem(R.id.edit).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            startActivity(new Intent(UserDetailActivity.this, UserDataActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSelectorDialog() {
        new MaterialDialog.Builder(this)
                .title("选择背景图片")
                .items(new String[]{"拍照", "相册", "网络"})
                .itemsCallback((materialDialog, view, i, charSequence) -> getPresenter().editFace(i)).show();
    }

    public void changeBackground(String bgUri){
        background.setImageURI(Uri.parse(bgUri));
    }
}
