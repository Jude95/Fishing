package com.jude.fishing.module.setting;


import com.jude.beam.bijection.Presenter;
import com.umeng.share.ShareManager;

/**
 * Created by Mr.Jude on 2015/8/13.
 */
public class AboutPresenter extends Presenter<AboutActivity> {

    public void share() {
        ShareManager.getInstance().share(getView(), "欢迎使用空钩", "空钩", "http://www.baidu.com", "http://img4.duitang.com/uploads/item/201503/04/20150304191759_mmEtx.jpeg");
    }
}
