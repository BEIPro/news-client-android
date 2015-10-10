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

    private static final String TAG = "TaggedFragmentStatePagerAdapter";

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private ArrayList<Fragment.SavedState> savedStates = new ArrayList<Fragment.SavedState>();
    private DebugClass debugClass = new DebugClass();

    public TaggedFragmentStatePagerAdapter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(TAG, "start to add a fragment " + position);
        if(fragments.size() < position){
            if(fragments.get(position) != null){
                return fragments.get(position);
            }
        }

        while (fragments.size() >= position){
            fragments.add(null);
        }

        if(fragmentTransaction == null){
            fragmentTransaction = fragmentManager.beginTransaction();
        }

        Fragment fragment = getItem(position);
        fragmentTransaction.add(container.getId(), fragment);
        fragments.set(position, fragment);

        Log.d(TAG, "add a fragment " + position);
        return fragment;
    }

    public abstract Fragment getItem(int position);

    @Override
    public void finishUpdate(View container) {
        if (fragmentTransaction != null) {
            fragmentTransaction.commitAllowingStateLoss();
        }
        fragmentTransaction = null;
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    private class DebugClass extends LogDebug{

        @Override
        public void log(boolean DBG, String Tag, String logMsg) {
            parantLog(DBG, Tag, logMsg);
        }
    }
}
