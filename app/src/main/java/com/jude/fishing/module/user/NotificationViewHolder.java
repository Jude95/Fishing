package com.jude.fishing.module.user;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.Notification;
import com.jude.fishing.module.blog.BlogDetailActivity;
import com.jude.fishing.module.place.PlaceDetailActivity;
import com.jude.fishing.utils.RecentDateFormat;
import com.jude.tagview.TAGView;
import com.jude.utils.JTimeTransform;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/10/23.
 */
public class NotificationViewHolder extends BaseViewHolder<Notification> {
    @InjectView(R.id.msg)
    TextView msg;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.unread)
    TAGView unread;

    public NotificationViewHolder(ViewGroup parent) {
        super(parent, R.layout.user_item_notification);
        ButterKnife.inject(this, itemView);
    }

    @Override
    public void setData(Notification data) {
        msg.setText(data.getMsg());
        time.setText(new JTimeTransform(data.getTime()).toString(new RecentDateFormat()));
        unread.setVisibility(data.isRead() ? View.GONE:View.VISIBLE);
        itemView.setOnClickListener(v -> {
            switch (data.getType()) {
                case Notification.PLACE_ADD:
                case Notification.PLACE_REFUSE:
                case Notification.PLACE_PASS:
                case Notification.PLACE_COMMENT:
                    Intent place = new Intent(itemView.getContext(), PlaceDetailActivity.class);
                    place.putExtra("id", Integer.parseInt(data.getLink()));
                    itemView.getContext().startActivity(place);
                    break;
                case Notification.BLOG_COMMENT:
                case Notification.BLOG_RECOMMEND:
                case Notification.BLOG_PRAISE:
                    Intent blog = new Intent(itemView.getContext(), BlogDetailActivity.class);
                    blog.putExtra("id", Integer.parseInt(data.getLink()));
                    itemView.getContext().startActivity(blog);
                    break;
                case Notification.USER_ATTENTION:
                    Intent user = new Intent(itemView.getContext(), UserDetailActivity.class);
                    user.putExtra("id", Integer.parseInt(data.getLink()));
                    itemView.getContext().startActivity(user);
                    break;
            }
        });
    }
}
