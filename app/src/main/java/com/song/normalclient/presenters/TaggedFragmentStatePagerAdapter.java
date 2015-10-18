package com.song.normalclient.presenters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.song.normalclient.Debug.LogDebug;

import java.util.ArrayList;

/**
 * Created by songsubei on 27/09/15.
 */
public abstract class TaggedFragmentStatePagerAdapter extends PagerAdapter {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private ArrayList<Fragment.SavedState> savedStates = new ArrayList<Fragment.SavedState>();
    private Fragment mCurrentPrimaryItem = null;

    public TaggedFragmentStatePagerAdapter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment) object).getView() == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(fragments.size() > position){
            if(fragments.get(position) != null){
                return fragments.get(position);
            }
        }

        while (fragments.size() <= position){
            fragments.add(null);
        }

        if(fragmentTransaction == null){
            fragmentTransaction = fragmentManager.beginTransaction();
        }

        Fragment fragment = getItem(position);
        if (savedStates.size() > position) {
            Fragment.SavedState fss = savedStates.get(position);
            if (fss != null) {
                fragment.setInitialSavedState(fss);
            }
        }

        fragment.setMenuVisibility(false);
        fragment.setUserVisibleHint(false);
        fragmentTransaction.add(container.getId(), fragment, getTag(position));
        fragments.set(position, fragment);

        return fragment;
    }

    public abstract Fragment getItem(int position);
    public abstract String getTag(int position);

    @Override
    public void finishUpdate(View container) {
        if (fragmentTransaction != null) {
            fragmentTransaction.commitAllowingStateLoss();
            fragmentTransaction = null;
            fragmentManager.executePendingTransactions();
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;

        if (fragmentTransaction == null) {
            fragmentTransaction = fragmentManager.beginTransaction();
        }
        while (savedStates.size() <= position) {
            savedStates.add(null);
        }
        savedStates.set(position, fragmentManager.saveFragmentInstanceState(fragment));
        fragments.set(position, null);

        fragmentTransaction.remove(fragment);
    }
}
