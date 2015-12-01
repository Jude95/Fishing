package com.jude.fishing.module.place;

import com.jude.beam.bijection.Presenter;

/**
 * Created by Mr.Jude on 2015/9/28.
 */
public class PlacePresenter extends Presenter<PlaceFragment>{
    private PlaceMapFragment mMap;
    private PlaceListFragment mList;


    public void refresh(){
        if (getView().isMapFragment)
            mMap.getPresenter().subscribe();
        else
            mList.getPresenter().subscribe();
    }

    public PlaceMapFragment getMapFragment(){
        if (mMap==null)mMap = new PlaceMapFragment();
        return mMap;
    }

    public PlaceListFragment getListFragment(){
        if (mList == null)mList = new PlaceListFragment();
        return mList;
    }

    @Override
    protected void onCreateView(PlaceFragment view) {
        super.onCreateView(view);
        getView().initFragment();
    }

}
