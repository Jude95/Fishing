package com.jude.fishing.module.place;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.fishing.R;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.module.user.LoginActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/28.
 */
@RequiresPresenter(PlacePresenter.class)
public class PlaceFragment extends BeamFragment<PlacePresenter> {

    @InjectView(R.id.container_place)
    FrameLayout containerPlace;
    @InjectView(R.id.style)
    FloatingActionButton style;

    public boolean isMapFragment = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.app_name);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.add){
            if (AccountModel.getInstance().getAccount()!=null)
                startActivity(new Intent(getActivity(),PlaceAddActivity.class));
            else
                startActivity(new Intent(getActivity(),LoginActivity.class));
            return true;
        }
        if (item.getItemId()==R.id.filter){
            FilterDialogView filterDialogView = new FilterDialogView(getContext());
            filterDialogView.setCostType(PlaceModel.getInstance().getFilterCostType());
            filterDialogView.setPoolType(PlaceModel.getInstance().getFilterPoolType());
            new MaterialDialog.Builder(getContext())
                    .title("钓点筛选")
                    .customView(filterDialogView,true)
                    .positiveText("确定")
                    .onPositive((materialDialog, dialogAction) -> {
                        int poolType = filterDialogView.getPoolType();
                        int costType = filterDialogView.getCostType();
                        PlaceModel.getInstance().saveFilter(poolType,costType);
                        getPresenter().refresh();
                    })
                    .negativeText("取消")
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_fragment_main, container, false);
        ButterKnife.inject(this, view);
        style.setOnClickListener(v->{
            if (isMapFragment)setListFragment();
            else setMapFragment();
        });
        return view;
    }

    public void setMapFragment(){
        isMapFragment = true;
        getChildFragmentManager().beginTransaction().replace(R.id.container_place, getPresenter().getMapFragment()).commit();
        style.setImageResource(R.drawable.ic_category);
    }

    public void setListFragment(){
        isMapFragment = false;
        getChildFragmentManager().beginTransaction().replace(R.id.container_place,getPresenter().getListFragment()).commit();
        style.setImageResource(R.drawable.ic_around);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
