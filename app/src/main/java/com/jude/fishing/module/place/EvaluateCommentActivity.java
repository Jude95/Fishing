package com.jude.fishing.module.place;

import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.exgridview.ExGridView;
import com.jude.fishing.R;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.entities.Evaluate;
import com.jude.fishing.model.entities.EvaluateComment;
import com.jude.fishing.module.user.LoginActivity;
import com.jude.fishing.widget.NetImageAdapter;
import com.jude.fishing.widget.ScoreView;
import com.jude.utils.JTimeTransform;
import com.jude.utils.JUtils;

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
        time.setText(new JTimeTransform(data.getTime()).toString(new JTimeTransform.RecentDateFormat()));
        content.setText(data.getContent());
        commentCount.setText(data.getCommentCount() + "");
        score.setScore(data.getScore());
        if (data.getImages()!=null&&data.getImages().size()!=0){
            pictures.setColumnCount(Math.min(data.getImages().size(), 3));
            pictures.setAdapter(new NetImageAdapter(parent.getContext(), data.getImages()));
        }
        view.setOnClickListener(v-> showCommentEdit(0,data.getAuthorName()));
        return view;
    }


    public void showCommentEdit(int fid,String fname) {
        if (AccountModel.getInstance().getAccount()==null){
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        new MaterialDialog.Builder(this)
                .title("输入对 "+fname+" 的回复")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRange(0, 100)
                .input("", "", (dialog, input) -> {
                    if (input.toString().trim().isEmpty()) {
                        JUtils.Toast("回复不能为空");
                        return;
                    }
                    getPresenter().sentComment(input.toString(),fid);
                }).show();
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup viewGroup, int i) {
        JUtils.Log("getViewHolder");
        return new EvaluateCommentViewHolder(viewGroup,this);
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setNoMoreAble(false);
    }
}
