package com.jude.fishing.model;

import android.content.Context;

import com.jude.beam.model.AbsModel;
import com.jude.utils.JUtils;
import com.umeng.message.PushAgent;

/**
 * Created by Mr.Jude on 2015/12/13.
 */
public class PushModel extends AbsModel {

    @Override
    protected void onAppCreateOnBackThread(Context ctx) {
        super.onAppCreateOnBackThread(ctx);
        AccountModel.getInstance().getAccountUpdateSubject().subscribe(account -> {
            try {
                PushAgent.getInstance(ctx).addAlias(account.getUID() + "", "UID");
                JUtils.Log("Has Set Alias "+account.getUID());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
