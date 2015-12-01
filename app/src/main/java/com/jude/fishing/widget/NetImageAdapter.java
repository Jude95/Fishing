package com.jude.fishing.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.module.main.ImageViewActivity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Mr.Jude on 2015/9/16.
 */
public class NetImageAdapter extends ArrayAdapter<String> implements View.OnClickListener{

    public NetImageAdapter(Context context) {
        super(context, 0);
    }

    public NetImageAdapter(Context context, String[] objects) {
        super(context, 0, objects);
    }

    public NetImageAdapter(Context context, Collection<String> objects) {
        super(context, 0, objects.toArray(new String[objects.size()]));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleDraweeView draweeView = new SimpleDraweeView(getContext());
        draweeView.getHierarchy().setPlaceholderImage(new ColorDrawable(Color.rgb(252,242,230)));
        draweeView.getHierarchy().setFadeDuration(300);
        draweeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        draweeView.setImageURI(ImageModel.getInstance().getSmallImage(getItem(position)));
        draweeView.setTag(position);
        draweeView.setOnClickListener(this);
        return draweeView;
    }

    @Override
    public void onClick(View v) {
        ArrayList<Uri> uris = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            uris.add(ImageModel.getInstance().getLargeImage(getItem(i)));
        }
        Intent i = new Intent(v.getContext(), ImageViewActivity.class);
        i.putParcelableArrayListExtra(ImageViewActivity.KEY_URIS,uris);
        i.putExtra(ImageViewActivity.KEY_INDEX, (int) v.getTag());
        v.getContext().startActivity(i);
    }
}
