package com.jude.fishing.module.date;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by heqiang on 2015/12/2.
 */
@RequiresPresenter(DateWritePresenter.class)
public class DateWriteActivity extends BeamBaseActivity<DateWritePresenter> {
    @InjectView(R.id.content)
    EditText content;
    @InjectView(R.id.tg_time)
    TAGView tgTime;
    @InjectView(R.id.send)
    TAGView send;
    @InjectView(R.id.et_title)
    EditText title;
    //    @InjectView(R.id.tg_address)
//    TAGView address;
    long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fishing_activity_write);
        ButterKnife.inject(this);
        send.setOnClickListener(v -> checkInput());
    }

    private void checkInput() {
        if (title.getText().toString().trim().isEmpty()){
            JUtils.Toast("请写个标题");
            return;
        }
        if (content.getText().toString().trim().isEmpty()) {
            JUtils.Toast("请描述一下");
            return;
        }
        if (time == 0) {
            JUtils.Toast("请选择一个时间");
            return;
        }
        getPresenter().addDateInfor(title.getText().toString().trim(),"",content.getText().toString().trim(),time);
    }

    @OnClick(R.id.tg_time)
    public void getTime() {
        Calendar calender = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(DateWriteActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calender.set(year, monthOfYear, dayOfMonth);
                tgTime.setText(new SimpleDateFormat("yyyy年MM月dd日").format(calender.getTime()));
                time = calender.getTime().getTime();
            }
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

//    @OnClick(R.id.tg_address)
//    public void getAddress(){
//        Intent intent = new Intent(DateWriteActivity.this,PlaceLocationSelectActivity.class);
//        intent.putExtra("can",true);
//        startActivityForResult(intent,120);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        LatLng point = data.getParcelableExtra("point");
//        data.getStringExtra("briefAddress");
//        data.getStringExtra("address");
//        JUtils.Log(data.getStringExtra("briefAddress")+" "+data.getStringExtra("address"));
//    }
}
