package com.jude.fishing.module.article;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.Article;
import com.jude.fishing.widget.JSHTMLCleaner;
import com.jude.swipbackhelper.SwipeBackHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/12/26.
 */
@RequiresPresenter(ArticleWebPresenter.class)
public class ArticleWebActivity extends BeamDataActivity<ArticleWebPresenter,Article> {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.webview)
    WebView webview;
    JSHTMLCleaner mCleaner;

    @InjectView(R.id.praise_count)
    TextView praiseCount;
    @InjectView(R.id.praise_image)
    ImageView praiseImage;
    @InjectView(R.id.collect_img)
    ImageView collectImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity_web);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.inject(this);
        Article data = (Article) getIntent().getSerializableExtra("data");
        webview.loadUrl(data.getUrl());
        getExpansion().showProgressPage();
        webview.getSettings().setJavaScriptEnabled(true);
        mCleaner = new JSHTMLCleaner();
        mCleaner.addCleaner(new ChongDiaoWangHtmlCleaner());
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                getExpansion().showProgressPage();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl(mCleaner.createCreanJS(url));
                getExpansion().dismissProgressPage();
            }
        });
        praiseCount.setOnClickListener(v -> getPresenter().praise());
        praiseImage.setOnClickListener(v -> getPresenter().praise());
        collectImg.setOnClickListener(v -> getPresenter().collect());
    }

    @Override
    public void setData(Article data) {
        praiseCount.setText(data.getPraiseCount() + "");
        praiseCount.setTextColor(getResources().getColor(data.isPraised() ? R.color.red : R.color.white));
        praiseImage.setImageResource(data.isPraised() ? R.drawable.ic_praise_red : R.drawable.ic_praise_white);
        collectImg.setImageResource(data.isCollected() ? R.drawable.ic_collect_red : R.drawable.ic_collect_white);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
