package com.jude.fishing.module.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataFragment;
import com.jude.fishing.R;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.entities.Account;
import com.jude.fishing.module.blog.UserBlogActivity;
import com.jude.fishing.module.place.CollectionPlaceActivity;
import com.jude.fishing.module.place.UserEvaluateActivity;
import com.jude.fishing.module.place.UserPlaceActivity;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
@RequiresPresenter(UserPresenter.class)
public class UserFragment extends BeamDataFragment<UserPresenter, Account> {


    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.sign)
    TextView sign;
    @InjectView(R.id.head_arrows)
    ImageView headArrows;
    @InjectView(R.id.container_user)
    RelativeLayout containerUser;
    @InjectView(R.id.divider)
    View divider;
    @InjectView(R.id.blog)
    TextView blog;
    @InjectView(R.id.container_blog)
    LinearLayout containerBlog;
    @InjectView(R.id.attention)
    TextView attention;
    @InjectView(R.id.container_attention)
    LinearLayout containerAttention;
    @InjectView(R.id.fans)
    TextView fans;
    @InjectView(R.id.container_fans)
    LinearLayout containerFans;
    @InjectView(R.id.notify)
    LinearLayout notify;
    @InjectView(R.id.collect)
    LinearLayout collect;
    @InjectView(R.id.evaluate)
    LinearLayout evaluate;
    @InjectView(R.id.place)
    LinearLayout place;

    @InjectView(R.id.notification_count)
    TextView notificationCount;

    int uid;
    @InjectView(R.id.tv_score)
    TextView tvScore;
    @InjectView(R.id.score)
    LinearLayout score;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.user_fragment_main2, container, false);
        ButterKnife.inject(this, root);

        containerBlog.setOnClickListener(v -> goToActivityWithId(UserBlogActivity.class));
        containerFans.setOnClickListener(v -> goToActivityWithId(FansActivity.class));
        containerAttention.setOnClickListener(v -> goToActivityWithId(AttentionActivity.class));
        collect.setOnClickListener(v -> startActivity(new Intent(getActivity(), CollectionPlaceActivity.class)));
        evaluate.setOnClickListener(v -> startActivity(new Intent(getActivity(), UserEvaluateActivity.class)));
        place.setOnClickListener(v -> startActivity(new Intent(getActivity(), UserPlaceActivity.class)));
        notify.setOnClickListener(v -> startActivity(new Intent(getActivity(), NotificationActivity.class)));
        score.setOnClickListener(v -> {
            new MaterialDialog.Builder(getContext())
                    .title("积分兑换说明")
                    .content("因为钓友们对于积分系统呼声很高，所以我们推出了积分系统：\n\n每日签到 +2\n每日第一篇渔获 +2\n\n因为刚刚推出，兑换商城还需完善，所以暂时无法兑换，我们将尽快推出良心的兑换系统。\n钓友们的支持是我们进步的动力。")
                    .positiveText("确定")
                    .show();
        });
        return root;
    }

    public void setNotificationCount(int i) {
        JUtils.Log("UI NotificationCount:" + i);
        notificationCount.setVisibility(i == 0 ? View.GONE : View.VISIBLE);
        notificationCount.setText(i + "");
    }

    private void goToActivityWithId(Class clz) {
        Intent intent = new Intent(getActivity(), clz);
        intent.putExtra("id", uid);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void setData(Account data) {
        if (data==null){
            uid = 0;
            avatar.setImageURI(Uri.EMPTY);
            name.setText("");
            sign.setText("");
            blog.setText("0");
            attention.setText("0");
            fans.setText("0");
            tvScore.setText("0");
            containerUser.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), UserDetailActivity.class);
                i.putExtra("id", 0);
                startActivity(i);
            });
        }else{
            uid = data.getUID();
            avatar.setImageURI(ImageModel.getInstance().getSmallImage(data.getAvatar()));
            name.setText(data.getName());
            sign.setText(data.getSign());
            blog.setText(data.getBlogCount() + "");
            attention.setText(data.getCared());
            fans.setText(data.getFans());
            tvScore.setText(data.getScore()+"");
            containerUser.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), UserDetailActivity.class);
                i.putExtra("id", data.getUID());
                startActivity(i);
            });
        }
    }

}
