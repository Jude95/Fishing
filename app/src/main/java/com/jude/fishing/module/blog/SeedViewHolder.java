package com.jude.fishing.module.blog;

import android.content.Intent;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.exgridview.ExGridView;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.Seed;
import com.jude.fishing.utils.RecentDateFormat;
import com.jude.fishing.widget.NetImageAdapter;
import com.jude.utils.JTimeTransform;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class SeedViewHolder extends BaseViewHolder<Seed> {
    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.content)
    TextView content;
    @InjectView(R.id.image)
    ExGridView image;
    @InjectView(R.id.address)
    TextView address;
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
    @InjectView(R.id.ll_praise)
    LinearLayout praiseContainer;
    NetImageAdapter adapter;
    BlogListFragment mFragment;

    private int id;

    public SeedViewHolder(ViewGroup parent,BlogListFragment fragment) {
        super(parent, R.layout.blog_item_main);
        ButterKnife.inject(this, itemView);
        mFragment = fragment;
        image.setAdapter(adapter = new NetImageAdapter(parent.getContext()));
        adapter.setNotifyOnChange(false);
        itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), BlogDetailActivity.class);
            i.putExtra("id", id);
            v.getContext().startActivity(i);
        });
    }

    @Override
    public void setData(Seed data) {
        id = data.getId();
        avatar.setImageURI(Uri.parse(data.getAuthorAvatar()));
        name.setText(data.getAuthorName());
        time.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
        content.setText(data.getContent());
        address.setText(data.getAddress());
        if (mFragment!=null)praiseContainer.setOnClickListener(v -> mFragment.getPresenter().praise(id,data.getPraiseStatus()));
        praiseImage.setImageResource(data.getPraiseStatus() ? R.drawable.ic_collect_focus : R.drawable.ic_collect_unfocus);
        praiseCount.setText(data.getPraiseCount() + "");
        commentCount.setText(data.getCommentCount() + "");
        adapter.clear();
        adapter.addAll(data.getImages());
        adapter.notifyDataSetChanged();
    }
}
