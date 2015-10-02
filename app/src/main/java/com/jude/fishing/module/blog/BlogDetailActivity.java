package com.jude.fishing.module.blog;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.exgridview.ExGridView;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.PersonBrief;
import com.jude.fishing.model.entities.SeedComment;
import com.jude.fishing.model.entities.SeedDetail;
import com.jude.fishing.utils.RecentDateFormat;
import com.jude.fishing.widget.NetImageAdapter;
import com.jude.utils.JTimeTransform;
import com.jude.utils.JUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/9/27.
 */
@RequiresPresenter(BlogDetailPresenter.class)
public class BlogDetailActivity extends BeamListActivity<BlogDetailPresenter, SeedComment> {

    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.content)
    TextView content;
    @InjectView(R.id.pictures)
    ExGridView pictures;
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
    @InjectView(R.id.praise_member)
    ExGridView praiseMember;

    public View getBlogDetailView(SeedDetail data, ViewGroup parent) {
        View view = getLayoutInflater().inflate(R.layout.blog_item_head, parent, false);
        ButterKnife.inject(this,view);
        avatar.setImageURI(Uri.parse(data.getAuthorAvatar()));
        name.setText(data.getAuthorName());
        time.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
        content.setText(data.getContent());
        praiseImage.setImageResource(data.getPraiseStatus() ? R.drawable.ic_collect_focus : R.drawable.ic_collect_unfocus);
        praiseCount.setText(data.getPraiseCount() + "");
        commentCount.setText(data.getCommentCount() + "");

        ArrayList<Uri> arr = new ArrayList<>();
        for (String img : data.getImages()) {
            arr.add(Uri.parse(img));
        }
        pictures.setAdapter(new NetImageAdapter(parent.getContext(), arr));

        for (PersonBrief personBrief : data.getPraiseMember()) {
            SimpleDraweeView draweeView = new SimpleDraweeView(this);
            draweeView.setLayoutParams(new ViewGroup.LayoutParams(JUtils.dip2px(40),JUtils.dip2px(40)));
            draweeView.setImageURI(Uri.parse(personBrief.getAvatar()));
            draweeView.getHierarchy().setRoundingParams(RoundingParams.asCircle());
            praiseMember.addView(draweeView);
        }

        return view;
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup viewGroup, int i) {
        return new SeedCommentViewHolder(viewGroup);
    }

}
