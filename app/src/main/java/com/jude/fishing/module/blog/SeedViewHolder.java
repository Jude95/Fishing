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
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.BlogModel;
import com.jude.fishing.model.entities.Seed;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.fishing.module.user.LoginActivity;
import com.jude.fishing.module.user.UserDetailActivity;
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

    private int id;
    private int authorId;
    public SeedViewHolder(ViewGroup parent) {
        super(parent, R.layout.blog_item_main);
        ButterKnife.inject(this, itemView);
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
        authorId = data.getAuthorId();
        avatar.setImageURI(Uri.parse(data.getAuthorAvatar()));
        avatar.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), UserDetailActivity.class);
            i.putExtra("id", data.getAuthorId());
            v.getContext().startActivity(i);
        });
        name.setText(data.getAuthorName());
        time.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
        content.setText(data.getContent());
        address.setText(data.getAddress());
        praiseContainer.setOnClickListener(v -> {
            if (AccountModel.getInstance().getAccount()==null){
                praiseContainer.getContext().startActivity(new Intent(praiseContainer.getContext(), LoginActivity.class));
                return;
            }
            data.setPraiseStatus(!data.getPraiseStatus());
            data.setPraiseCount(data.getPraiseCount() + (data.getPraiseStatus() ? 1 : -1));
            praiseCount.setText(data.getPraiseCount() + "");

            praiseImage.setImageResource(data.getPraiseStatus() ? R.drawable.ic_collect_focus : R.drawable.ic_collect_unfocus);
            if (data.getPraiseStatus())
                BlogModel.getInstance().blogPraise(id)
                        .subscribe(new ServiceResponse<>());
            else
                BlogModel.getInstance().blogUnPraise(id)
                        .subscribe(new ServiceResponse<>());
        });
        praiseImage.setImageResource(data.getPraiseStatus() ? R.drawable.ic_collect_focus : R.drawable.ic_collect_unfocus);
        praiseCount.setText(data.getPraiseCount() + "");
        commentCount.setText(data.getCommentCount() + "");
        adapter.clear();
        if (data.getImages()!=null&&data.getImages().length!=0){
            image.setColumnCount(Math.min(data.getImages().length,3));
        }
        adapter.addAll(data.getImages());
        adapter.notifyDataSetChanged();
    }
}
