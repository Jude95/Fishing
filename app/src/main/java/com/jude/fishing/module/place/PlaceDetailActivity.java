package com.jude.fishing.module.place;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.exgridview.ExGridView;
import com.jude.fishing.R;
import com.jude.fishing.config.Constant;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.entities.PlaceDetail;
import com.jude.fishing.widget.ScoreView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import java.util.List;

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
    @InjectView(R.id.collect)
    TAGView collect;
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
    @InjectView(R.id.server)
    ExGridView server;

    TAGView commentCount;
    PictureAdapter adapter;

    private int evaluateCount;
    public static final String[] SERVER_COLORS = {
            "#fda76d", "#708be0", "#66c96a", "#f67f7f", "#d6a0d6", "#87bfff"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity_detail);
        getExpansion().showProgressPage();
        ButterKnife.inject(this);
        picture.setAdapter(adapter = new PictureAdapter());
        tel.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel.getText()));
            startActivity(intent);
        });
        collect.setOnClickListener(v -> {
            if (getPresenter().collect()) {
                collect.setStrokeWidth(JUtils.dip2px(3));
                collect.setTextColor(getResources().getColor(R.color.blue));
                collect.setText("取消收藏");
                JUtils.Toast("已收藏");
            } else {
                collect.setStrokeWidth(JUtils.dip2px(0));
                collect.setTextColor(getResources().getColor(R.color.white));
                collect.setText("收藏");
                JUtils.Toast("已取消收藏");
            }
        });

    }

    @Override
    public void setError(Throwable e) {
        JUtils.Log("Fucker"+e.getLocalizedMessage());
        getExpansion().showErrorPage();
    }

    @Override
    public void setData(PlaceDetail data) {
        getExpansion().dismissProgressPage();
        getSupportActionBar().setTitle(data.getName());
        evaluateCount = data.getEvaluateCount();
        JUtils.Log("evaluateCount:"+evaluateCount);
        if (commentCount!=null)
            commentCount.setText(data.getEvaluateCount() + "");
        score.setScore(data.getScore());
        scoreText.setText(data.getScore() + "");
        address.setText(data.getAddress());
        tel.setText(data.getTel());
        price.setText("人均消费:" + data.getCost() + "元");

        if (TextUtils.isEmpty(data.getContent())){
            content.setText("暂无");
        }else{
            content.setText(data.getContent());
        }
        adapter.setPath(data.getPicture());

        if (data.isCollected()){
            collect.setStrokeWidth(JUtils.dip2px(3));
            collect.setTextColor(getResources().getColor(R.color.blue));
            collect.setText("取消收藏");
        }else {
            collect.setStrokeWidth(JUtils.dip2px(0));
            collect.setTextColor(getResources().getColor(R.color.white));
            collect.setText("收藏");
        }

        String fish = "";
        if (!TextUtils.isEmpty(data.getFishType())) {
            for (String s : data.getFishType().split(",")) {
                try {
                    if (Integer.parseInt(s) < Constant.FishType.length) {
                        fish += Constant.FishType[Integer.parseInt(s)] + ",";
                    }
                }catch (Exception e){}
            }
            fish = fish.substring(0, fish.length() - 1);
        } else {
            fish = "未知";
        }
        fishType.setText(fish);



        if (data.getPoolType()< Constant.PlacePoolType.length)
            server.addView(createServerView(Constant.PlacePoolType[data.getPoolType()],
                    server));

        if (data.getCostType()< Constant.PlaceCostType.length)
            server.addView(createServerView(Constant.PlaceCostType[data.getCostType()],
                    server));

        for (String s : data.getServiceType().split(",")) {
            try{
                int index = Integer.parseInt(s);
                if (index<Constant.PlaceServiceType.length){
                    server.addView(createServerView(Constant.PlaceServiceType[index],
                            server));
                }
            }catch (Exception e){

            }
        }
    }

    private View createServerView(String text,ViewGroup parent){
        View v = getLayoutInflater().inflate(R.layout.place_item_server,parent,false);
        TAGView tagView = (TAGView) v.findViewById(R.id.server_icon);
        tagView.setText(text.charAt(0) + "");
        tagView.setBackgroundColor(Color.parseColor(SERVER_COLORS[parent.getChildCount()]));
        tagView.setTextColor(Color.parseColor(SERVER_COLORS[parent.getChildCount()]));

        TextView textView = (TextView) v.findViewById(R.id.server_text);
        textView.setText(text);
        return v;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_place, menu);
        commentCount = (TAGView) menu.findItem(R.id.comment).getActionView();
        commentCount.setBackgroundColor(Color.TRANSPARENT);
        commentCount.setIcon(R.drawable.ic_comment);
        commentCount.setImageWidth(JUtils.dip2px(24));

        commentCount.setText(evaluateCount+"");
        commentCount.setOnClickListener(v->{
            Intent i = new Intent(this,EvaluateActivity.class);
            i.putExtra("id",getIntent().getIntExtra("id",0));
            startActivity(i);
        });
        return true;
    }



    class PictureAdapter extends StaticPagerAdapter {
        private List<String> path;

        public void setPath(List<String> path) {
            this.path = path;
            notifyDataSetChanged();
        }

        @Override
        public View getView(ViewGroup container, int position) {
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(container.getContext());
            simpleDraweeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            simpleDraweeView.setImageURI(ImageModel.getInstance().getSizeImage(path.get(position),640));
            return simpleDraweeView;
        }

        @Override
        public int getCount() {
            return path == null ? 0 : path.size();
        }
    }
}
