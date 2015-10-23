package com.jude.fishing.module.place;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.jude.fishing.R;
import com.jude.fishing.widget.RadioGroup;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/10/22.
 */
public class FilterDialogView extends LinearLayout {
    @InjectView(R.id.cost_all)
    RadioButton costAll;
    @InjectView(R.id.cost_free)
    RadioButton costFree;
    @InjectView(R.id.cost_weight)
    RadioButton costWeight;
    @InjectView(R.id.cost_time)
    RadioButton costTime;
    @InjectView(R.id.cost_group)
    RadioGroup costGroup;
    @InjectView(R.id.pool_all)
    RadioButton poolAll;
    @InjectView(R.id.pool_pond)
    RadioButton poolPond;
    @InjectView(R.id.pool_reservoir)
    RadioButton poolReservoir;
    @InjectView(R.id.pool_river)
    RadioButton poolRiver;
    @InjectView(R.id.pool_lake)
    RadioButton poolLake;
    @InjectView(R.id.pool_ocean)
    RadioButton poolOcean;
    @InjectView(R.id.pool_group)
    RadioGroup poolGroup;

    private int costType = -1;
    private int poolType = -1;

    public int getCostType() {
        return costType;
    }

    public int getPoolType() {
        return poolType;
    }

    public FilterDialogView(Context context) {
        super(context);
        init();
    }

    public FilterDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FilterDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.place_dialog_filter, this);
        ButterKnife.inject(this, this);
        costAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) costType = -1;
        });
        costFree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)costType=0;
        });
        costWeight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)costType=1;
        });
        costTime.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)costType=2;
        });
        poolAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)poolType=-1;
        });
        poolPond.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)poolType=0;
        });
        poolReservoir.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)poolType=1;
        });
        poolRiver.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)poolType=2;
        });
        poolLake.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) poolType = 3;
        });
        poolOcean.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) poolType = 4;
        });
    }

    public void setCostType(int costType) {
        this.costType = costType;
        switch (costType){
            case -1:costAll.setChecked(true);break;
            case 0:costFree.setChecked(true);break;
            case 1:costWeight.setChecked(true);break;
            case 2:costTime.setChecked(true);break;
        }
    }

    public void setPoolType(int poolType) {
        this.poolType = poolType;
        switch (poolType){
            case -1:poolAll.setChecked(true);break;
            case 0:poolPond.setChecked(true);break;
            case 1:poolReservoir.setChecked(true);break;
            case 2:poolRiver.setChecked(true);break;
            case 3:poolLake.setChecked(true);break;
            case 4:poolOcean.setChecked(true);break;
        }
    }


}
