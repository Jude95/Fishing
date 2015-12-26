package com.jude.fishing.module.article;


import com.jude.fishing.widget.JSHTMLCleaner;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuchenxi on 15/12/19.
 */
public class ChongDiaoWangHtmlCleaner implements JSHTMLCleaner.HtmlCleaner {
    @Override
    public boolean willWork(String url) {
        return url.contains("cqfishing.net");
    }

    @Override
    public List<String> getDirtyElementListByClass() {
        List<String> list = new ArrayList<>();
        list.add("header");//顶部导航
        list.add("a_cn");//顶部广告
        list.add("bottom_nav");//底部导航
        list.add("footer");//底部登录注册电脑版etc
        list.add("cl");//回复
        list.add("footer-bg");//不知道是啥不过占位置
        list.add("titleline");//不知道是啥不过占位置
        list.add("morebox");//不知道是啥不过占位置
        return list;
    }

    @Override
    public List<String> getDirtyElementListById() {
        List<String> list = new ArrayList<>();
        list.add("J_bottomSection");//顶部导航
        list.add("dzlab_apptip");//下载APP
        return list;
    }

    @Override
    public String getJSExtension() {
        return  "var elements =getElementsByClassName(\"plc cl\");\n" +
                "for(var i = 1;i < elements.length;i++){\n" +
                "   removeElement(elements[i]);\n" +
                "}\n";
    }
}
