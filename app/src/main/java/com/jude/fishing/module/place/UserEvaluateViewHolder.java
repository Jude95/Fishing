package com.jude.fishing.module.place;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.exgridview.ExGridView;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.Evaluate;
import com.jude.fishing.widget.NetImageAdapter;
import com.jude.fishing.widget.ScoreView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/22.
 */
public class UserEvaluateViewHolder extends BaseViewHolder<Evaluate> {
    @InjectView(R.id.img_anchor)
    ImageView imgAnchor;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.score)
    ScoreView score;
    @InjectView(R.id.place)
    LinearLayout place;
    @InjectView(R.id.divider)
    View divider;
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

    public UserEvaluateViewHolder(ViewGroup parent) {
        super(parent, R.layout.place_item_evaluate_user);
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
        name.setText(data.getPlaceName());
        score.setScore(data.getScore());
        address.setText(data.getAddress());
        commentCount.setText(data.getCommentCount() + "");
        content.setText(data.getContent());
        adapter.clear();
        if(data.getImages()!=null){
            adapter.addAll(data.getImages());
            image.setColumnCount(Math.min(data.getImages().size(),3));
        }
        adapter.notifyDataSetChanged();
    }
}
