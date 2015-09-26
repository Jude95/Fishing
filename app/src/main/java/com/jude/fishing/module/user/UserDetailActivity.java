package com.jude.fishing.module.user;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.fishing.R;
import com.jude.fishing.model.bean.PersonDetail;
import com.jude.fishing.model.bean.Seed;
import com.jude.fishing.utils.RecentDateFormat;
import com.jude.fishing.widget.LinearWrapContentRecyclerView;
import com.jude.fishing.widget.ObservableScrollView;
import com.jude.tagview.TAGView;
import com.jude.utils.JTimeTransform;

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

    private boolean isAttended;
    private Drawable mActionbarDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_detail);
//        ButterKnife.inject(this);
//        attention.setOnClickListener(v -> {
//            isAttended = !isAttended;
//            attention.setText(isAttended ? "已关注" : "关注");
//        });
        getExpansion().showProgressPage();
//        mActionbarDrawable = new ColorDrawable(getResources().getColor(R.color.blue));
//        getToolbar().setBackgroundDrawable(mActionbarDrawable);
//        setToolbarAlpha(0);
//        scrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
//            @Override
//            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
//                if (y > head.getHeight() - toolbar.getHeight()) {
//                    setToolbarAlpha(1);
//                } else {
//                    setToolbarAlpha(0);
//                }
//            }
//        });
//        containerAttention.setOnClickListener(v -> startActivity(new Intent(this, AttentionActivity.class)));
//        containerBlog.setOnClickListener(v -> startActivity(new Intent(this, UserBlogActivity.class)));
//        containerFans.setOnClickListener(v -> startActivity(new Intent(this, FansActivity.class)));
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
            background.setImageURI(Uri.parse(data.getBackground()));
        name.setText(data.getName());
        avatar.setImageURI(Uri.parse(data.getAvatar()));
        attention.setText(data.getAttentionCount() + "");
        sign.setText(data.getSign());
        fans.setText(data.getFansCount() + "");
        blog.setText(data.getBlogCount() + "");
        address.setText(data.getAddress());
        age.setText(data.getAge() + "年");
        skill.setText(data.getSkill());
        blogList.setAdapter(new BlogSimpleAdapter(this, data.getSeeds()));
        if (data.getRelation() == -1) {
            operation.setVisibility(View.GONE);
        } else {
            isAttended = data.getRelation() == 0;
            attention.setText(isAttended ? "已关注" : "关注");
        }
    }

    class BlogSimpleAdapter extends RecyclerArrayAdapter<Seed> {

        public BlogSimpleAdapter(Context context, Seed[] objects) {
            super(context, objects);
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
        @InjectView(R.id.praise_image)
        ImageView praiseImage;
        @InjectView(R.id.praise_count)
        TextView praiseCount;
        @InjectView(R.id.comment_image)
        ImageView commentImage;
        @InjectView(R.id.comment_count)
        TextView commentCount;
        @InjectView(R.id.tool)
        LinearLayout tool;

        public BlogImageViewHolder(ViewGroup parent) {
            super(parent, R.layout.blog_item_simple_image);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void setData(Seed data) {
            preview.setImageURI(Uri.parse(data.getImages()[0]));
            content.setText(data.getContent());
            address.setText(data.getAddress());
            date.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
            praiseCount.setText(data.getPraiseCount() + "");
            commentCount.setText(data.getCommentCount() + "");
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
        @InjectView(R.id.praise_image)
        ImageView praiseImage;
        @InjectView(R.id.praise_count)
        TextView praiseCount;
        @InjectView(R.id.comment_image)
        ImageView commentImage;
        @InjectView(R.id.comment_count)
        TextView commentCount;
        @InjectView(R.id.tool)
        LinearLayout tool;

        public BlogTextViewHolder(ViewGroup parent) {
            super(parent, R.layout.blog_item_simple_image);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void setData(Seed data) {
            preview.setText(data.getContent().charAt(0) + "");
            content.setText(data.getContent());
            address.setText(data.getAddress());
            date.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
            praiseCount.setText(data.getPraiseCount() + "");
            commentCount.setText(data.getCommentCount() + "");
        }
    }
}
