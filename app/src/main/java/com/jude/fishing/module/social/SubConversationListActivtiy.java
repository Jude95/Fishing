package com.jude.fishing.module.social;

import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;

@RequiresPresenter(SubConversationListPresenter.class)
public class SubConversationListActivtiy extends BeamBaseActivity<SubConversationListPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_activity_subconversationlist);
      }
}