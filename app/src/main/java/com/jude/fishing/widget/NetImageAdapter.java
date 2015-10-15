package com.jude.fishing.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.fishing.model.ImageModel;

import java.util.Collection;

/**
 * Created by Mr.Jude on 2015/9/16.
 */
public class NetImageAdapter extends ArrayAdapter<String> {

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
        draweeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        draweeView.setImageURI(ImageModel.getInstance().getSmallImage(getItem(position)));
        return draweeView;
    }
}
