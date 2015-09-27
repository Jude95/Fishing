package com.jude.fishing.widget;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Collection;

/**
 * Created by Mr.Jude on 2015/9/16.
 */
public class NetImageAdapter extends ArrayAdapter<Uri> {

    public NetImageAdapter(Context context) {
        super(context, 0);
    }

    public NetImageAdapter(Context context, Uri[] objects) {
        super(context, 0, objects);
    }

    public NetImageAdapter(Context context, Collection<Uri> objects) {
        super(context, 0, objects.toArray(new Uri[0]));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SimpleDraweeView draweeView = new SimpleDraweeView(getContext());
        draweeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        draweeView.setImageURI(getItem(position));
        return draweeView;
    }
}
