package com.jude.fishing.module.blog;

import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.bean.SeedComment;
import com.jude.fishing.utils.RecentDateFormat;
import com.jude.fishing.utils.UserClickbleSpan;
import com.jude.fishing.widget.LinearWrapContentRecyclerView;
import com.jude.utils.JTimeTransform;

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

    public SeedCommentViewHolder(ViewGroup parent) {
        super(parent, R.layout.blog_item_comment);
        ButterKnife.inject(this, itemView);
    }

    @Override
    public void setData(SeedComment data) {
        avatar.setImageURI(Uri.parse(data.getAuthorAvatar()));
        name.setText(data.getAuthorName());
        time.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat("MM-dd hh:mm")));
        content.setText(data.getContent());
        createTextView(child,dealChildArray(data));
    }

    private void createTextView(ViewGroup parent,ArrayList<SeedComment> mTempArrayList){
        for (SeedComment seedComment : mTempArrayList) {
            String authorName = seedComment.getAuthorName();
            SpannableString spannableInfo;
            SeedComment originalComment = findCommentById(mTempArrayList,seedComment.getOriginalId());
            if (originalComment==null){
                spannableInfo = new SpannableString(authorName + " : " + seedComment.getContent());
                spannableInfo.setSpan(new UserClickbleSpan(parent.getContext(), seedComment.getAuthorId()), 0, authorName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }else{
                String originalName = originalComment.getAuthorName();
                spannableInfo = new SpannableString(authorName +"回复"+originalName+ " : " + seedComment.getContent());
                spannableInfo.setSpan(new UserClickbleSpan(parent.getContext(), seedComment.getAuthorId()), 0, authorName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableInfo.setSpan(new UserClickbleSpan(parent.getContext(), originalComment.getAuthorId()), authorName.length()+2, authorName.length()+2+originalName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            TextView textView  = new TextView(parent.getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText(spannableInfo);
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
        if (comment.getChild().length==0){
            mTempArrayList.add(comment);
            return;
        }
        for (SeedComment seedComment : comment.getChild()) {
            addSeedComment(seedComment);
        }
    }
}
