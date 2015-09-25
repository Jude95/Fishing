package com.jude.fishing.module.place;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.fishing.R;
import com.jude.fishing.config.Constant;
import com.jude.fishing.model.bean.PlaceDetail;
import com.jude.fishing.widget.ScoreView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/23.
 */

@RequiresPresenter(PlaceDetailPresenter.class)
public class PlaceDetailActivity extends BeamDataActivity<PlaceDetailPresenter, PlaceDetail> {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.picture)
    RollPagerView picture;
    @InjectView(R.id.score)
    ScoreView score;
    @InjectView(R.id.score_text)
    TextView scoreText;
    @InjectView(R.id.evaluate)
    TAGView evaluate;
    @InjectView(R.id.address)
    TextView address;
    @InjectView(R.id.fish_type)
    TextView fishType;
    @InjectView(R.id.tel)
    TextView tel;
    @InjectView(R.id.price)
    TextView price;
    @InjectView(R.id.content)
    TextView content;

    PictureAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity_detail);
        getExpansion().showProgressPage();
        ButterKnife.inject(this);
        picture.setAdapter(adapter = new PictureAdapter());
    }

    @Override
    public void setData(PlaceDetail data) {
        getExpansion().dismissProgressPage();
        score.setScore(data.getScore());
        scoreText.setText(data.getScore()+"");
        address.setText(data.getAddress());
        fishType.setText(data.getFishType());
        tel.setText(data.getTel());
        price.setText("人均消费:"+data.getCost()+"元");

        content.setText(data.getContent());
        adapter.setPath(data.getPicture());
    }

    class PictureAdapter extends StaticPagerAdapter{
        private String[] path;

        public void setPath(String[] path) {
            this.path = path;
            notifyDataSetChanged();
            JUtils.Log("setPath");
        }

        @Override
        public View getView(ViewGroup container, int position) {
            JUtils.Log("getView"+position);
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(container.getContext());
            simpleDraweeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            simpleDraweeView.setImageURI(Uri.parse(path[position]));
            return simpleDraweeView;
        }

        @Override
        public int getCount() {
            JUtils.Log("getCount()"+(path==null?0:path.length));
            return path==null?0:path.length;
        }
    }
}
