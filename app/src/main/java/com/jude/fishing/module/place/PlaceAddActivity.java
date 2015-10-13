package com.jude.fishing.module.place;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.fishing.R;
import com.jude.fishing.config.Constant;
import com.jude.fishing.model.entities.PlaceDetail;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/22.
 */
@RequiresPresenter(PlaceAddPresenter.class)
public class PlaceAddActivity extends BeamDataActivity<PlaceAddPresenter, PlaceDetail> {


    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.view_address)
    LinearLayout viewAddress;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.view_name)
    LinearLayout viewName;
    @InjectView(R.id.tv_picture_count)
    TextView tvPictureCount;
    @InjectView(R.id.view_picture_count)
    LinearLayout viewPictureCount;
    @InjectView(R.id.tv_cost_type)
    TextView tvCostType;
    @InjectView(R.id.view_cost_type)
    LinearLayout viewCostType;
    @InjectView(R.id.tv_cost_avg)
    TextView tvCostAvg;
    @InjectView(R.id.view_cost_avg)
    LinearLayout viewCostAvg;
    @InjectView(R.id.tv_fish)
    TextView tvFish;
    @InjectView(R.id.view_fish)
    LinearLayout viewFish;
    @InjectView(R.id.tv_pool)
    TextView tvPool;
    @InjectView(R.id.view_pool)
    LinearLayout viewPool;
    @InjectView(R.id.tv_server)
    TextView tvServer;
    @InjectView(R.id.view_server)
    LinearLayout viewServer;
    @InjectView(R.id.tv_contact)
    TextView tvContact;
    @InjectView(R.id.view_contact)
    LinearLayout viewContact;
    @InjectView(R.id.view_content)
    LinearLayout viewContent;
    @InjectView(R.id.tv_content)
    TextView tvContent;
    @InjectView(R.id.submit)
    Button submit;
    @InjectView(R.id.divider_cost)
    View dividerCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity_add);
        ButterKnife.inject(this);
        viewName.setOnClickListener(v -> showNameEdit());
        viewContact.setOnClickListener(v -> showContactEdit());
        viewContent.setOnClickListener(v -> showContentEdit());
        tvContent.setOnClickListener(v -> showContentEdit());
        viewFish.setOnClickListener(v -> showFishTypeEdit());
        viewCostAvg.setOnClickListener(v -> showCostAvgEdit());
        viewCostType.setOnClickListener(v -> showCostTypeEdit());
        viewPool.setOnClickListener(v -> showPoolTypeEdit());
        viewServer.setOnClickListener(v -> showServerTypeEdit());
        viewAddress.setOnClickListener(v->getPresenter().startPlaceSelect());
        viewPictureCount.setOnClickListener(v->getPresenter().startPhotoSelect());
        submit.setOnClickListener(v->getPresenter().submit());
    }


    public void setPictureCount(int count){
        tvPictureCount.setText(count + "张");
    }

    @Override
    public void setData(PlaceDetail data) {
        tvAddress.setText(TextUtils.isEmpty(data.getAddress()) ? "点击选择地址" : data.getAddress());
        tvName.setText(TextUtils.isEmpty(data.getName()) ? "点击填写名字" : data.getName());
        tvContact.setText(TextUtils.isEmpty(data.getTel()) ? "点击填写联系电话" : data.getTel());
        tvContent.setText(TextUtils.isEmpty(data.getContent()) ? "点击填写钓点介绍" : data.getContent());

        if (data.getCostType() == 0) {
            dividerCost.setVisibility(View.GONE);
            viewCostAvg.setVisibility(View.GONE);
        } else {
            dividerCost.setVisibility(View.VISIBLE);
            viewCostAvg.setVisibility(View.VISIBLE);
        }

        tvCostAvg.setText(data.getCost() + "元");

        if (data.getCostType() < Constant.PlaceCostType.length)
            tvCostType.setText(Constant.PlaceCostType[data.getCostType()]);



        if (data.getPoolType() < Constant.PlacePoolType.length)
            tvPool.setText(Constant.PlacePoolType[data.getPoolType()]);

        String fish = "";
        if (!TextUtils.isEmpty(data.getFishType())) {
            for (String s : data.getFishType().split(",")) {
                try{
                    if (Integer.parseInt(s) < Constant.FishType.length) {
                        fish += Constant.FishType[Integer.parseInt(s)] + ",";
                    }
                }catch (Exception e){}
            }
            fish = fish.substring(0, fish.length() - 1);
        } else {
            fish = "请选择鱼种";
        }
        tvFish.setText(fish);

        String server = "";
        if (!TextUtils.isEmpty(data.getServiceType())) {
            for (String s : data.getServiceType().split(",")) {
                if (Integer.parseInt(s) < Constant.PlaceServiceType.length) {
                    server += Constant.PlaceServiceType[Integer.parseInt(s)] + ",";
                }
            }
            server = server.substring(0, server.length() - 1);
        } else {
            server = "没有更多服务";
        }

        tvServer.setText(server);

    }

    private void showNameEdit() {
        new MaterialDialog.Builder(this)
                .title("输入名字")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRange(2, 10)
                .input("", getPresenter().getPlaceDetail().getName(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input.toString().trim().isEmpty()) {
                            JUtils.Toast("标题不能为空");
                            return;
                        }
                        getPresenter().setName(input.toString());
                    }
                }).show();
    }



    private void showContactEdit() {
        new MaterialDialog.Builder(this)
                .title("输入联系电话")
                .inputType(InputType.TYPE_CLASS_PHONE)
                .inputRange(8, 11)
                .input("", getPresenter().getPlaceDetail().getTel(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input.toString().trim().isEmpty()) {
                            JUtils.Toast("标题不能为空");
                            return;
                        }
                        getPresenter().setContact(input.toString());
                    }
                }).show();
    }

    private void showCostAvgEdit() {
        new MaterialDialog.Builder(this)
                .title("输入人均消费")
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .inputRange(0, 5)
                .input("", getPresenter().getPlaceDetail().getTel(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        getPresenter().setCostAvg(Integer.parseInt(input.toString()));
                    }
                }).show();
    }

    private void showContentEdit() {
        new MaterialDialog.Builder(this)
                .title("输入钓点介绍")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRange(0, 500)
                .input("", getPresenter().getPlaceDetail().getContent(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        getPresenter().setContent(input.toString());
                    }
                }).show();
    }

    private void showCostTypeEdit() {
        new MaterialDialog.Builder(this)
                .title("请选择消费方式")
                .items(Constant.PlaceCostType)
                .itemsCallbackSingleChoice(getPresenter().getPlaceDetail().getCostType(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        getPresenter().setCostType(which);
                        return true;
                    }
                })
                .positiveText("确定")
                .show();
    }

    private void showPoolTypeEdit() {
        new MaterialDialog.Builder(this)
                .title("请选择水系类型")
                .items(Constant.PlacePoolType)
                .itemsCallbackSingleChoice(getPresenter().getPlaceDetail().getPoolType(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        getPresenter().setPoolType(which);
                        return true;
                    }
                })
                .positiveText("确定")
                .show();
    }

    private void showFishTypeEdit() {
        String[] types;
        if (getPresenter().getPlaceDetail().getServiceType() != null)
            types = getPresenter().getPlaceDetail().getServiceType().split(",");
        else
            types = new String[0];

        Integer[] fishType = new Integer[types.length];
        for (int i = 0; i < types.length; i++) {
            fishType[i] = Integer.parseInt(types[i]);
        }

        new MaterialDialog.Builder(this)
                .title("请选择鱼种")
                .items(Constant.FishType)
                .itemsCallbackMultiChoice(fishType, new MaterialDialog.ListCallbackMultiChoice() {

                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, Integer[] integers, CharSequence[] charSequences) {
                        getPresenter().setFishType(integers);
                        return true;
                    }
                })
                .positiveText("确定")
                .show();
    }

    private void showServerTypeEdit() {
        String[] types;
        if (getPresenter().getPlaceDetail().getServiceType() != null)
            types = getPresenter().getPlaceDetail().getServiceType().split(",");
        else
            types = new String[0];

        Integer[] serverTypes = new Integer[types.length];
        for (int i = 0; i < types.length; i++) {
            serverTypes[i] = Integer.parseInt(types[i]);
        }
        new MaterialDialog.Builder(this)
                .title("请选择服务类型")
                .items(Constant.PlaceServiceType)
                .itemsCallbackMultiChoice(serverTypes, new MaterialDialog.ListCallbackMultiChoice() {

                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, Integer[] integers, CharSequence[] charSequences) {
                        getPresenter().setServerType(integers);
                        return true;
                    }
                })
                .positiveText("确定")
                .show();
    }
}
