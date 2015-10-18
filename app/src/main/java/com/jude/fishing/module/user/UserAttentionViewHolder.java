package com.jude.fishing.module.user;

import android.content.Intent;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.SocialModel;
import com.jude.fishing.model.entities.PersonBrief;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.tagview.TAGView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/9/20.
 */
public class UserAttentionViewHolder extends BaseViewHolder<PersonBrief> {
    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.sign)
    TextView sign;
    @InjectView(R.id.attention)
    TAGView attention;

    int id;
    PersonBrief personBrief;

    public UserAttentionViewHolder(ViewGroup parent) {
        super(parent, R.layout.user_item_attention);
        ButterKnife.inject(this, itemView);
        itemView.setOnClickListener(v->{
            Intent i = new Intent(itemView.getContext(),UserDetailActivity.class);
            i.putExtra("id",id);
            itemView.getContext().startActivity(i);
        });
    }

    @Override
    public void setData(PersonBrief data) {
        personBrief = data;
        id = data.getUID();
        avatar.setImageURI(Uri.parse(data.getAvatar()));
        name.setText(data.getName());
        sign.setText(data.getSign());
        attention.setOnClickListener(v -> attention(data.getRelation()));
        changeAttention(data.getRelation());
    }

    private void attention(boolean isAttention) {
        if (isAttention)
            SocialModel.getInstance().unAttention(id).subscribe(new ServiceResponse<Object>() {
                @Override
                public void onNext(Object o) {
                    personBrief.setRelation(false);
                    changeAttention(personBrief.getRelation());
                }
            });
        else
            SocialModel.getInstance().attention(id).subscribe(new ServiceResponse<Object>() {
                @Override
                public void onNext(Object o) {
                    personBrief.setRelation(true);
                    changeAttention(personBrief.getRelation());
                }
            });
    }

    private void changeAttention(boolean isAttetion){
        attention.setText(isAttetion ? "已关注" : "关注");
        attention.setTextColor(itemView.getResources().getColor(isAttetion ? R.color.gray_deep : R.color.green));
        attention.setBackgroundColor(itemView.getResources().getColor(isAttetion ? R.color.gray_deep : R.color.green));
    }
}
