package com.jude.fishing.module.user;

import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.tagview.TAGView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by heqiang on 2015/9/23.
 */
@RequiresPresenter(UserDataPresenter.class)
public class UserDataActivity extends BeamBaseActivity<UserDataPresenter> {

    @InjectView(R.id.tg_next)
    TAGView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_data);
        ButterKnife.inject(this);

        next.setOnClickListener(v -> startActivity(new Intent(this, UserData2Activity.class)));
    }
}
