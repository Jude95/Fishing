package com.jude.fishing.module.setting;

import android.os.Bundle;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.fishing.model.CommonModel;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscriber;

/**
 * Created by Mr.Jude on 2015/8/13.
 */
@RequiresPresenter(UpdateLogPresenter.class)
public class UpdateLogActivity extends BeamBaseActivity<UpdateLogPresenter> {

    @InjectView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_updatelog);
        ButterKnife.inject(this);
        getExpansion().showProgressPage();
        CommonModel.getInstance().getUpdateLog().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getExpansion().dismissProgressPage();
                getExpansion().showErrorPage();
            }

            @Override
            public void onNext(String s) {
                getExpansion().dismissProgressPage();
                text.setText(s);
            }
        });

    }
}
