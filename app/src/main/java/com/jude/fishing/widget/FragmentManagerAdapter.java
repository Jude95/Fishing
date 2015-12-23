package com.jude.fishing.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by Mr.Jude on 2015/12/23.
 */
public abstract class FragmentManagerAdapter {
    public static final int HIDE = 0;
    public static final int ATTACH = 1;
    public static final int REMOVE = 2;


    private static final String TAG = "FragmentManagerAdapter";
    private static final boolean DEBUG = true;
    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private int mMode = 0;

    public void setMode(int mMode) {
        this.mMode = mMode;
    }

    public FragmentManagerAdapter(FragmentManager fm) {
        mFragmentManager = fm;
    }

    public Fragment applyItem(ViewGroup container, int itemId){
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        // Do we already have this fragment?
        Fragment fragment = findFragment(container, itemId);
        if (fragment != null) {
            if (DEBUG) Log.v(TAG, "Attaching item #" + itemId + ": f=" + fragment+
                    makeFragmentName(container.getId(), itemId));
            switch (mMode){
                case HIDE:
                    mCurTransaction.show(fragment);
                    break;
                case ATTACH:
                    mCurTransaction.attach(fragment);
                    break;
                case REMOVE:
                    mCurTransaction.add(container.getId(), fragment,
                            makeFragmentName(container.getId(), itemId));
                    break;
            }
        } else {
            fragment = getItem(itemId);
            if (DEBUG) Log.v(TAG, "Adding item #" + itemId + ": f=" + fragment+" "+
                    makeFragmentName(container.getId(), itemId));
            mCurTransaction.add(container.getId(), fragment,
                    makeFragmentName(container.getId(), itemId));
        }
        setPrimaryItem(fragment);
        mCurTransaction.commit();
        mCurTransaction = null;
        mFragmentManager.executePendingTransactions();
        return fragment;
    }

    private void setPrimaryItem(Fragment fragment) {
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
                removeItem(mCurrentPrimaryItem);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    private void removeItem(Fragment fragment) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        if (DEBUG) Log.v(TAG, "Detaching item " + ": f=" + fragment
                + " v=" + fragment.getView());

        switch (mMode){
            case HIDE:
                mCurTransaction.hide(fragment);
                break;
            case ATTACH:
                mCurTransaction.detach(fragment);
                break;
            case REMOVE:
                mCurTransaction.remove(fragment);
                break;
        }
    }

    protected abstract Fragment getItem(int id);
    public String getTitle(int id){return null;}

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    private Fragment findFragment(ViewGroup container, int itemId){
        String name = makeFragmentName(container.getId(), itemId);
        return mFragmentManager.findFragmentByTag(name);
    }
}
