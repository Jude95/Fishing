package com.jude.fishing.module.bbs;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.bean.Seed;
import com.jude.fishing.utils.RecentDateFormat;
import com.jude.fishing.widget.ImagePieceView;
import com.jude.fishing.widget.PieceViewGroup;
import com.jude.utils.JTimeTransform;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class SeedViewHolder extends BaseViewHolder<Seed> {
    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.content)
    TextView content;
    @InjectView(R.id.image)
    PieceViewGroup image;
    @InjectView(R.id.address)
    TextView address;
    @InjectView(R.id.praise_image)
    ImageView praiseImage;
    @InjectView(R.id.praise_count)
    TextView praiseCount;
    @InjectView(R.id.comment_image)
    ImageView commentImage;
    @InjectView(R.id.comment_count)
    TextView commentCount;
    @InjectView(R.id.tool)
    LinearLayout tool;


    public SeedViewHolder(ViewGroup parent) {
        super(parent, R.layout.bbs_item_main);
        ButterKnife.inject(this, itemView);
    }

    @Override
    public void setData(Seed data) {
        avatar.setImageURI(Uri.parse(data.getAuthorAvatar()));
        name.setText(data.getAuthorName());
        time.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
        content.setText(data.getContent());
        praiseImage.setImageResource(data.getPraiseStatus() ? R.drawable.ic_collect_focus : R.drawable.ic_collect_unfocus);
        praiseCount.setText(data.getPraiseCount()+"");
        commentCount.setText(data.getCommentCount()+"");

        image.clear();
        if (data.getImages() != null) {
            final ArrayList<Uri> arr = new ArrayList<>();
            for (final String img : data.getImages()) {
                arr.add(Uri.parse(img));
            }
            for (final String img : data.getImages()) {
                final int index = image.getCount();
                final ImagePieceView view = new ImagePieceView(itemView.getContext());
                view.setImage(Uri.parse(img));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(getContext(), ImageViewActivity.class);
//                        intent.putExtra(ImageViewActivity.KEY_URIS,arr);
//                        intent.putExtra(ImageViewActivity.KEY_INDEX,index);
//                        getContext().startActivity(intent);
                    }
                });
                image.add(view);
            }
        }

    }
}
