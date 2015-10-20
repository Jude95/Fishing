package com.jude.fishing.module.social;

import android.content.Intent;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.SocialModel;
import com.jude.fishing.model.entities.Contact;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.fishing.module.user.UserDetailActivity;
import com.jude.tagview.TAGView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by heqiang on 15/10/20.
 */
public class ContactAttentionViewHolder extends BaseViewHolder<Contact> {
    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.sign)
    TextView sign;
    @InjectView(R.id.attention)
    TAGView attention;
    @InjectView(R.id.tv_contact)
    TextView contact;

    int id;
    Contact personBrief;

    public ContactAttentionViewHolder(ViewGroup parent) {
        super(parent, R.layout.social_item_attention);
        ButterKnife.inject(this, itemView);
        itemView.setOnClickListener(v -> {
            Intent i = new Intent(itemView.getContext(), UserDetailActivity.class);
            i.putExtra("id", id);
            itemView.getContext().startActivity(i);
        });
    }

    @Override
    public void setData(Contact data) {
        personBrief = data;
        id = data.getUID();
        avatar.setImageURI(Uri.parse(data.getAvatar()));
        name.setText(data.getName());
        sign.setText(data.getSign());
        contact.setText(data.getContactName());
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

    private void changeAttention(boolean isAttention) {
        attention.setText(isAttention ? "已关注" : "关注");
        attention.setTextColor(itemView.getResources().getColor(isAttention ? R.color.gray_deep : R.color.green));
        attention.setBackgroundColor(itemView.getResources().getColor(isAttention ? R.color.gray_deep : R.color.green));
    }
}
