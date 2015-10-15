package com.jude.fishing.module.place;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.exgridview.ExGridView;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.Evaluate;
import com.jude.fishing.model.entities.EvaluateComment;
import com.jude.fishing.utils.RecentDateFormat;
import com.jude.fishing.widget.NetImageAdapter;
import com.jude.fishing.widget.ScoreView;
import com.jude.utils.JTimeTransform;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/10/3.
 */
@RequiresPresenter(EvaluateCommentPresenter.class)
public class EvaluateCommentActivity extends BeamListActivity<EvaluateCommentPresenter, EvaluateComment> {
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
    @InjectView(R.id.comment_image)
    ImageView commentImage;
    @InjectView(R.id.comment_count)
    TextView commentCount;
    @InjectView(R.id.tool)
    LinearLayout tool;
    @InjectView(R.id.score)
    ScoreView score;


    public View getEvaluateDetailView(Evaluate data, ViewGroup parent) {
        View view = getLayoutInflater().inflate(R.layout.place_item_evaluate_head, parent, false);
        ButterKnife.inject(this, view);
        address.setText(data.getAddress());
        avatar.setImageURI(Uri.parse(data.getAuthorAvatar()));
        name.setText(data.getAuthorName());
        time.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
        content.setText(data.getContent());
        commentCount.setText(data.getCommentCount() + "");
        score.setScore(data.getScore());

        pictures.setAdapter(new NetImageAdapter(parent.getContext(), data.getImages()));

        return view;
    }


    @Override
    protected BaseViewHolder getViewHolder(ViewGroup viewGroup, int i) {
        return new EvaluateCommentViewHolder(viewGroup);
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setNoMoreAble(false);
    }
}
