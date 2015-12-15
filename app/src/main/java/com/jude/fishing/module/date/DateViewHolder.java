package com.jude.fishing.module.date;

import android.content.Intent;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.entities.Date;
import com.jude.fishing.module.user.LoginActivity;
import com.jude.fishing.utils.RecentDateFormat;
import com.jude.utils.JTimeTransform;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by heqiang on 2015/12/2.
 */
public class DateViewHolder extends BaseViewHolder<Date>{
    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.count)
    TextView count;
    private String id;
    public DateViewHolder(ViewGroup parent) {
        super(parent, R.layout.date_item);
        ButterKnife.inject(this,itemView);
        itemView.setOnClickListener(v -> {
            if (AccountModel.getInstance().getAccount()==null){
                v.getContext().startActivity(new Intent(v.getContext(), LoginActivity.class));
                return;
            }
            Intent i = new Intent(v.getContext(), DateDetailActivity.class);
            i.putExtra("id", id);
            v.getContext().startActivity(i);
        });
    }

    @Override
    public void setData(Date data) {
        id = data.getId();
        avatar.setImageURI(Uri.parse(data.getAuthorAvatar()));
        name.setText(data.getAuthorName());
        time.setText(new JTimeTransform(data.getAcTime()).toString(new RecentDateFormat("MM月dd日")));
        title.setText(data.getTitle());
        count.setText((data.getEnrollMember()==null?0:data.getEnrollMember().size())+"人已报名");
    }
}
