package com.jude.fishing.module.place;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.exgridview.ExGridView;
import com.jude.fishing.R;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.entities.Evaluate;
import com.jude.fishing.utils.RecentDateFormat;
import com.jude.fishing.widget.NetImageAdapter;
import com.jude.fishing.widget.ScoreView;
import com.jude.utils.JTimeTransform;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/10/3.
 */
public class EvaluateViewHolder extends BaseViewHolder<Evaluate> {
    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.score)
    ScoreView score;
    @InjectView(R.id.content)
    TextView content;
    @InjectView(R.id.image)
    ExGridView image;
    @InjectView(R.id.address)
    TextView address;
    @InjectView(R.id.comment_image)
    ImageView commentImage;
    @InjectView(R.id.comment_count)
    TextView commentCount;
    @InjectView(R.id.tool)
    LinearLayout tool;

    NetImageAdapter adapter;

    private int id;

    public EvaluateViewHolder(ViewGroup parent) {
        super(parent, R.layout.place_item_evaluate);
        ButterKnife.inject(this, itemView);
        image.setAdapter(adapter = new NetImageAdapter(parent.getContext()));
        adapter.setNotifyOnChange(false);
        itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), EvaluateCommentActivity.class);
            i.putExtra("id", id);
            v.getContext().startActivity(i);
        });
    }

    @Override
    public void setData(Evaluate data) {
        id = data.getId();
        score.setScore(data.getScore());
        avatar.setImageURI(ImageModel.getInstance().getSmallImage(data.getAuthorAvatar()));
        name.setText(data.getAuthorName());
        time.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
        content.setText(data.getContent());
        commentCount.setText(data.getCommentCount() + "");
        address.setText(data.getAddress());
        adapter.clear();
        if (data.getImages()!=null&&data.getImages().size()!=0){
            image.setColumnCount(Math.min(data.getImages().size(), 3));
            adapter.addAll(data.getImages());
        }
        adapter.notifyDataSetChanged();
    }
}
