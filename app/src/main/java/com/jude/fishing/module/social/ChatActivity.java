package com.jude.fishing.module.social;

import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;

import io.rong.imkit.fragment.ConversationFragment;

/**
 * Created by zhuchenxi on 15/7/21.
 */
@RequiresPresenter(ChatPresenter.class)
public class ChatActivity extends BeamBaseActivity<ChatPresenter> {
    String id;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_activity_chat);
        id = getIntent().getData().getQueryParameter("targetId");
        title = getIntent().getData().getQueryParameter("title");
        ConversationFragment fragment =  (ConversationFragment)getSupportFragmentManager().findFragmentById(R.id.conversation);
        getSupportActionBar().setTitle(title);
        if (fragment !=null) fragment.setUri(getIntent().getData());
        else{
            finish();
        }
    }
}
