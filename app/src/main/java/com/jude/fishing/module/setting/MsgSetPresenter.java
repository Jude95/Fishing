package com.jude.fishing.module.setting;


import android.os.Bundle;
import android.widget.CompoundButton;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.fishing.model.CommonModel;
import com.jude.fishing.model.entities.PushSetting;
import com.jude.fishing.model.service.ServiceResponse;

/**
 * Created by Mr.Jude on 2015/8/13.
 */
public class MsgSetPresenter extends BeamDataActivityPresenter<MsgSetActivity,PushSetting> implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(MsgSetActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        CommonModel.getInstance().getPushSetting().subscribe(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        PushSetting setting = new PushSetting();
        setting.setIsCareNotify(getView().switchCare.isChecked());
        setting.setIsCommentNotify(getView().switchComment.isChecked());
        setting.setIsPlaceNotify(getView().switchAddress.isChecked());
        setting.setIsPraiseNotify(getView().switchPraise.isChecked());
        CommonModel.getInstance().uploadPushSetting(setting).subscribe(new ServiceResponse<Object>());
    }
}
