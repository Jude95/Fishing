package com.jude.fishing.module.place;

import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.EvaluateComment;
import com.jude.fishing.utils.UserClickableSpan;
import com.jude.fishing.widget.LinearWrapContentRecyclerView;
import com.jude.utils.JTimeTransform;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/10/3.
 */
public class EvaluateCommentViewHolder extends BaseViewHolder<EvaluateComment> {
    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.content)
    TextView content;
    @InjectView(R.id.child)
    LinearWrapContentRecyclerView child;

    private EvaluateCommentActivity mActivity;

    public EvaluateCommentViewHolder(ViewGroup parent,EvaluateCommentActivity activity) {
        super(parent, R.layout.blog_item_comment);
        mActivity = activity;
        ButterKnife.inject(this, itemView);
        child.setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    public void setData(EvaluateComment data) {
        avatar.setImageURI(Uri.parse(data.getAuthorAvatar()));
        name.setText(data.getAuthorName());
        time.setText(new JTimeTransform(data.getTime()).toString(new JTimeTransform.RecentDateFormat("MM-dd hh:mm")));
        content.setText(data.getContent());
        itemView.setOnClickListener(v-> mActivity.showCommentEdit(data.getId(),data.getAuthorName()));
        child.removeAllViews();
        createTextView(child, dealChildArray(data));
    }

    private void createTextView(ViewGroup parent,ArrayList<EvaluateComment> mTempArrayList){
        for (EvaluateComment EvaluateComment : mTempArrayList) {
            String authorName = EvaluateComment.getAuthorName();
            SpannableString spannableInfo;
            EvaluateComment originalComment = findCommentById(mTempArrayList,EvaluateComment.getOriginalId());
            if (originalComment==null){
                spannableInfo = new SpannableString(authorName + " : " + EvaluateComment.getContent());
                spannableInfo.setSpan(new UserClickableSpan(parent.getContext(), EvaluateComment.getAuthorId()), 0, authorName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }else{
                String originalName = originalComment.getAuthorName();
                spannableInfo = new SpannableString(authorName +"回复"+originalName+ " : " + EvaluateComment.getContent());
                spannableInfo.setSpan(new UserClickableSpan(parent.getContext(), EvaluateComment.getAuthorId()), 0, authorName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableInfo.setSpan(new UserClickableSpan(parent.getContext(), originalComment.getAuthorId()), authorName.length()+2, authorName.length()+2+originalName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            TextView textView  = new TextView(parent.getContext());
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setPadding(0, JUtils.dip2px(2), 0, 0);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText(spannableInfo);
            textView.setOnClickListener(v->mActivity.showCommentEdit(EvaluateComment.getId(),EvaluateComment.getAuthorName()));
            parent.addView(textView);
        }
    }


    private EvaluateComment findCommentById(ArrayList<EvaluateComment> mTempArrayList,int id){
        for (EvaluateComment EvaluateComment : mTempArrayList) {
            if (EvaluateComment.getId()==id)return EvaluateComment;
        }
        return null;
    }

    ArrayList<EvaluateComment> mTempArrayList;
    private ArrayList<EvaluateComment> dealChildArray(EvaluateComment data){
        mTempArrayList = new ArrayList<>();
        for (EvaluateComment EvaluateComment : data.getChild()) {
            addEvaluateComment(EvaluateComment);
        }
        Collections.sort(mTempArrayList, (comment, t1) -> (int) (comment.getTime() - t1.getTime()));
        return mTempArrayList;
    }

    private void addEvaluateComment(EvaluateComment comment){
        mTempArrayList.add(comment);
        if (comment.getChild()==null||comment.getChild().length==0){
            return;
        }
        for (EvaluateComment EvaluateComment : comment.getChild()) {
            addEvaluateComment(EvaluateComment);
        }
    }
}
