package com.jude.fishing.module.place;

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
import com.jude.fishing.model.bean.PlaceBrief;
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

    public PlaceViewHolder(ViewGroup parent) {
        super(parent, R.layout.place_item_main);
        ButterKnife.inject(this,itemView);
    }

    @Override
    public void setData(PlaceBrief data) {
        preview.setImageURI(Uri.parse(data.getPreview()));
        name.setText(data.getName());
        score.setText(data.getScore() + "");
        scoreImage.setScore(data.getScore());
        tagContainer.removeAllViews();
        for (int service : data.getServiceType()) {
            if (Constant.PlaceServiceType.length>service)
            tagContainer.addView(createTag(Constant.PlaceServiceType[service]));
        }
        address.setText(data.getAddress());
    }

    View createTag(String content){
        TAGView view = new TAGView(itemView.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(JUtils.dip2px(4),0,JUtils.dip2px(4),0);
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.LTGRAY);
        view.setText(content);
        return view;
    }
}
