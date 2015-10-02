package com.jude.fishing.module.place;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.config.Constant;
import com.jude.fishing.model.LocationModel;
import com.jude.fishing.model.entities.PlaceBrief;
import com.jude.fishing.utils.DistanceFormat;
import com.jude.fishing.widget.ScoreView;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class PlaceViewHolder extends BaseViewHolder<PlaceBrief> {
    @InjectView(R.id.preview)
    SimpleDraweeView preview;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.score_image)
    ScoreView scoreImage;
    @InjectView(R.id.score)
    TextView score;
    @InjectView(R.id.tag_container)
    LinearLayout tagContainer;
    @InjectView(R.id.address)
    TextView address;

    @InjectView(R.id.cost_type)
    TAGView costType;
    @InjectView(R.id.pool_type)
    TAGView poolType;
    @InjectView(R.id.container1)
    LinearLayout container1;
    @InjectView(R.id.cost)
    TextView cost;
    @InjectView(R.id.container2)
    LinearLayout container2;
    @InjectView(R.id.distance)
    TextView distance;
    @InjectView(R.id.container3)
    LinearLayout container3;


    int id;

    public PlaceViewHolder(ViewGroup parent) {
        super(parent, R.layout.place_item_main);
        ButterKnife.inject(this, itemView);
        itemView.setOnClickListener(v -> {
            Intent i = new Intent(parent.getContext(), PlaceDetailActivity.class);
            i.putExtra("id", id);
            parent.getContext().startActivity(i);
        });
    }

    @Override
    public void setData(PlaceBrief data) {
        id = data.getId();
        distance.setText(DistanceFormat.parse(LocationModel.getInstance().getDistance(data.getLat(),data.getLng())));
        cost.setText("人均"+data.getCost()+"¥");
        preview.setImageURI(Uri.parse(data.getPreview()));
        name.setText(data.getName());
        score.setText(data.getScore() + "");
        scoreImage.setScore(data.getScore());
        tagContainer.removeAllViews();
        for (String service : data.getServiceType().split(",")) {
            int id = Integer.parseInt(service);
            if (Constant.PlaceServiceType.length > id)
                tagContainer.addView(createTag(Constant.PlaceServiceType[id]));
        }
        address.setText(data.getAddress());
    }

    View createTag(String content) {
        TAGView view = new TAGView(itemView.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(JUtils.dip2px(4), 0, JUtils.dip2px(4), 0);
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.LTGRAY);
        view.setText(content);
        return view;
    }
}
