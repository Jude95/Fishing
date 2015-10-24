package com.jude.fishing.module.setting;


import com.jude.beam.bijection.Presenter;
import com.jude.fishing.module.setting.update.UpdateChecker;
import com.umeng.share.ShareManager;

/**
 * Created by Mr.Jude on 2015/8/13.
 */
public class AboutPresenter extends Presenter<AboutActivity> {

    public void share() {
        ShareManager.getInstance().share(getView(), "空钩APP是一款钓鱼爱好者工具应用,诚邀您的加入", "空钩-钓友贴心朋友", UpdateChecker.getInstance().getDownloadUrl(), "http://7xn7nj.com2.z0.glb.qiniucdn.com/logo_168.png");
    }
}
