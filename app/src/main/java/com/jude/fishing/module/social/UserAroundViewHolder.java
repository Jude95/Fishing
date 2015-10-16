package com.jude.fishing.module.social;

import android.content.Intent;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.PersonAround;
import com.jude.fishing.module.user.UserDetailActivity;
import com.jude.fishing.utils.DistanceFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/9/20.
 */
public class UserAroundViewHolder extends BaseViewHolder<PersonAround> {
    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.sign)
    TextView sign;
    @InjectView(R.id.distance)
    TextView distance;

    int id;

    public UserAroundViewHolder(ViewGroup parent) {
        super(parent, R.layout.social_item_around);
        ButterKnife.inject(this, itemView);
        itemView.setOnClickListener(v->{
            Intent i = new Intent(itemView.getContext(),UserDetailActivity.class);
            i.putExtra("id",id);
            itemView.getContext().startActivity(i);
        });
    }

    @Override
    public void setData(PersonAround data) {
        id = data.getUID();
        avatar.setImageURI(Uri.parse(data.getAvatar()));
        name.setText(data.getName());
        sign.setText(data.getSign());
        distance.setText(DistanceFormat.parse(data.getDistance()));
    }
}
