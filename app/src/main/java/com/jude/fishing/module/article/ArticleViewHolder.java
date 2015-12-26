package com.jude.fishing.module.article;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.Article;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/12/24.
 */

public class ArticleViewHolder extends BaseViewHolder<Article> {
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.praise_count)
    TextView praiseCount;
    @InjectView(R.id.container_praise)
    LinearLayout containerPraise;
    @InjectView(R.id.praise_image)
    ImageView praiseImage;

    public ArticleViewHolder(ViewGroup parent) {
        super(parent, R.layout.article_item_main);
        ButterKnife.inject(this, itemView);
    }

    @Override
    public void setData(Article data) {
        title.setText(data.getTitle());
        praiseCount.setText(data.getPraiseCount()+"");
        praiseImage.setImageResource(data.isPraised() ? R.drawable.praise_red : R.drawable.praise_gray);
        itemView.setOnClickListener(v->{
            Intent i = new Intent(getContext(),ArticleWebActivity.class);
            i.putExtra("data",data);
            getContext().startActivity(i);
        });
    }
}
