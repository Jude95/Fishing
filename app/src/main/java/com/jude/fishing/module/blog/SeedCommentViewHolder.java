package com.jude.fishing.module.blog;

import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.SeedComment;
import com.jude.fishing.module.user.UserDetailActivity;
import com.jude.fishing.utils.RecentDateFormat;
import com.jude.fishing.utils.UserClickableSpan;
import com.jude.fishing.widget.LinearWrapContentRecyclerView;
import com.jude.utils.JTimeTransform;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/9/27.
 */
public class SeedCommentViewHolder extends BaseViewHolder<SeedComment> {
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

    BlogDetailActivity mActivity;
    int id;
    public SeedCommentViewHolder(ViewGroup parent,BlogDetailActivity activity) {
        super(parent, R.layout.blog_item_comment);
        ButterKnife.inject(this, itemView);
        mActivity = activity;
        child.setOrientation(LinearLayout.VERTICAL);
        avatar.setOnClickListener(v->{
            Intent i = new Intent(v.getContext(), UserDetailActivity.class);
            i.putExtra("id",id);
            v.getContext().startActivity(i);
        });
    }

    @Override
    public void setData(SeedComment data) {
        id = data.getAuthorId();
        itemView.setOnClickListener(v -> mActivity.showCommentEdit(data.getId(), data.getAuthorName()));
        if (!TextUtils.isEmpty(data.getAuthorAvatar()))
        avatar.setImageURI(Uri.parse(data.getAuthorAvatar()));
        name.setText(data.getAuthorName());
        time.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat("MM-dd hh:mm")));
        content.setText(data.getContent());
        child.removeAllViews();
        createTextView(child, dealChildArray(data));
    }

    private void createTextView(ViewGroup parent,ArrayList<SeedComment> mTempArrayList){
        for (SeedComment seedComment : mTempArrayList) {
            String authorName = seedComment.getAuthorName();
            SpannableString spannableInfo;
            SeedComment originalComment = findCommentById(mTempArrayList,seedComment.getOriginalId());
            if (originalComment==null){
                spannableInfo = new SpannableString(authorName + " : " + seedComment.getContent());
                spannableInfo.setSpan(new UserClickableSpan(parent.getContext(), seedComment.getAuthorId()), 0, authorName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }else{
                String originalName = originalComment.getAuthorName();
                spannableInfo = new SpannableString(authorName +"回复"+originalName+ " : " + seedComment.getContent());
                spannableInfo.setSpan(new UserClickableSpan(parent.getContext(), seedComment.getAuthorId()), 0, authorName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableInfo.setSpan(new UserClickableSpan(parent.getContext(), originalComment.getAuthorId()), authorName.length()+2, authorName.length()+2+originalName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            TextView textView  = new TextView(parent.getContext());
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setPadding(0,JUtils.dip2px(2),0,0);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText(spannableInfo);
            textView.setOnClickListener(v -> mActivity.showCommentEdit(seedComment.getId(), seedComment.getAuthorName()));
            parent.addView(textView);
        }
    }


    private SeedComment findCommentById(ArrayList<SeedComment> mTempArrayList,int id){
        for (SeedComment seedComment : mTempArrayList) {
            if (seedComment.getId()==id)return seedComment;
        }
        return null;
    }

    ArrayList<SeedComment> mTempArrayList;
    private ArrayList<SeedComment> dealChildArray(SeedComment data){
        mTempArrayList = new ArrayList<>();
        for (SeedComment seedComment : data.getChild()) {
            addSeedComment(seedComment);
        }
        Collections.sort(mTempArrayList, (comment, t1) -> (int) (comment.getTime()-t1.getTime()));
        return mTempArrayList;
    }

    private void addSeedComment(SeedComment comment){
        mTempArrayList.add(comment);
        if (comment.getChild()==null||comment.getChild().length==0){
            return;
        }
        for (SeedComment seedComment : comment.getChild()) {
            addSeedComment(seedComment);
        }
    }
}
