package com.jude.fishing.module.setting;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

@RequiresPresenter(FeedPresenter.class)
public class FeedActivity extends BeamBaseActivity<FeedPresenter> {

    @InjectView(R.id.et_feed)
    EditText feed;
//    @InjectView(R.id.et_contact)
//    EditText contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_feed);
        ButterKnife.inject(this);
    }

    private void checkInput() {
        if (feed.getText().toString().trim().isEmpty()) {
            JUtils.Toast("请积极批评我们吧");
            return;
        }
//        if (contact.getText().toString().trim().isEmpty()){
//            JUtils.Toast("大侠，留个名吧");
//            return;
//        }
        getPresenter().feedback(feed.getText().toString().trim());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feed, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.done) {
            checkInput();
        }
        return super.onOptionsItemSelected(item);
    }
}
