package com.jude.fishing.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.jude.fishing.module.user.UserDetailActivity;

/**
 * Created by zhuchenxi on 15/9/27.
 */
public class UserClickableSpan extends ClickableSpan{
    private int id;
    private Context ctx;

    public UserClickableSpan(Context ctx, int id) {
        this.ctx = ctx;
        this.id = id;
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(ctx, UserDetailActivity.class);
        i.putExtra("id",id);
        ctx.startActivity(i);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
    }
}
