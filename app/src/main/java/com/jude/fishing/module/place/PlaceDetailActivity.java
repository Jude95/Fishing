package com.jude.fishing.module.place;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.exgridview.ExGridView;
import com.jude.fishing.R;
import com.jude.fishing.config.Constant;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.entities.PlaceDetail;
import com.jude.fishing.module.user.UserDetailActivity;
import com.jude.fishing.widget.ScoreView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;
import com.umeng.share.ShareManager;

import java.util.ArrayList;
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
    @InjectView(R.id.comment_count)
    TextView commentCount;
    @InjectView(R.id.deep)
    TextView deep;
    @InjectView(R.id.area)
    TextView area;
    @InjectView(R.id.nest)
    TextView nest;
    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;

    PictureAdapter adapter;
    @InjectView(R.id.navigation_image)
    ImageView navigationImage;
    @InjectView(R.id.navigation_text)
    TextView navigationText;


    List<MenuItem> edits = new ArrayList<>();

    private int evaluateCount = -1;
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
            if (!TextUtils.isEmpty(tel.getText())) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel.getText()));
                startActivity(intent);
            }
        });
        navigationImage.setOnClickListener(v -> getPresenter().startNavigation());
        navigationText.setOnClickListener(v->getPresenter().startNavigation());
        address.setOnClickListener(v -> getPresenter().startNavigation());
        collect.setOnClickListener(v -> {
            if (getPresenter().collect()) {
                collect.setStrokeWidth(JUtils.dip2px(3));
                collect.setTextColor(getResources().getColor(R.color.blue));
                collect.setText("取消收藏");
            } else {
                collect.setStrokeWidth(JUtils.dip2px(0));
                collect.setTextColor(getResources().getColor(R.color.white));
                collect.setText("收藏");
            }
        });
        commentCount.setOnClickListener(v -> {
            Intent i = new Intent(this, EvaluateActivity.class);
            i.putExtra("id", getIntent().getIntExtra("id", 0));
            startActivity(i);
        });
    }

    @Override
    public void setError(Throwable e) {
        getExpansion().showErrorPage();
    }

    @Override
    public void setData(PlaceDetail data) {
        getExpansion().dismissProgressPage();
        toolbar.setTitle(data.getName());
        evaluateCount = data.getEvaluateCount();
        commentCount.setText(evaluateCount + "条评价");
        score.setScore(data.getScore());
        scoreText.setText(data.getScore() + "");
        address.setText(data.getAddress());
        tel.setText(TextUtils.isEmpty(data.getTel()) ? "未知" : data.getTel());
        price.setText("人均消费:" + data.getCost() + "元");
        nest.setText(Constant.NestType[data.getNest()]);

        deep.setText("水深" + data.getDeep());
        area.setText("面积" + data.getArea());

        for (MenuItem edit : edits) {
            edit.setVisible(getPresenter().isAuthor());
        }

        name.setText(data.getAuthorName());
        avatar.setImageURI(ImageModel.getInstance().getSmallImage(data.getAuthorAvatar()));
        name.setOnClickListener(v -> {
            Intent i = new Intent(this, UserDetailActivity.class);
            i.putExtra("id", data.getAuthorId());
            startActivity(i);
        });
        if (TextUtils.isEmpty(data.getContent())) {
            content.setText("暂无");
        } else {
            content.setText(data.getContent());
        }
        adapter.setPath(data.getPicture());

        if (data.isCollected()) {
            collect.setStrokeWidth(JUtils.dip2px(3));
            collect.setTextColor(getResources().getColor(R.color.blue));
            collect.setText("取消收藏");
        } else {
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
                } catch (Exception e) {
                }
            }
            fish = fish.substring(0, fish.length() - 1);
        } else {
            fish = "未知";
        }
        fishType.setText(fish);


        if (data.getPoolType() < Constant.PlacePoolType.length)
            server.addView(createServerView(Constant.PlacePoolType[data.getPoolType()],
                    server));

        if (data.getCostType() < Constant.PlaceCostType.length)
            server.addView(createServerView(Constant.PlaceCostType[data.getCostType()],
                    server));

        for (String s : data.getServiceType().split(",")) {
            try {
                int index = Integer.parseInt(s);
                if (index < Constant.PlaceServiceType.length) {
                    server.addView(createServerView(Constant.PlaceServiceType[index],
                            server));
                }
            } catch (Exception e) {

            }
        }
    }

    private View createServerView(String text, ViewGroup parent) {
        View v = getLayoutInflater().inflate(R.layout.place_item_server, parent, false);
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
        menu.findItem(R.id.edit).setVisible(getPresenter().isAuthor());
        edits.add(menu.findItem(R.id.edit));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            ShareManager.getInstance().share(this, "钓点详情", "空钩", "http://www.baidu.com", "http://img4.duitang.com/uploads/item/201503/04/20150304191759_mmEtx.jpeg");
        }
        if (item.getItemId() == R.id.edit){
            getPresenter().startEdit();
        }
        return super.onOptionsItemSelected(item);
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
            simpleDraweeView.setImageURI(ImageModel.getInstance().getSizeImage(path.get(position), 640));
            return simpleDraweeView;
        }

        @Override
        public int getCount() {
            return path == null ? 0 : path.size();
        }
    }
}
