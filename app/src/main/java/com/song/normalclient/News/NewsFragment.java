package com.song.normalclient.News;

import android.app.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.song.normalclient.R;
import com.song.normalclient.presenters.NewsFragmentAdapter;




/**
 * Created by songsubei on 27/09/15.
 */
public class NewsFragment extends BaseFragment implements AppCompatCallback{

    private Toolbar toolbar;
    private AppCompatActivity activity;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View rootView;

    public NewsFragment(int layoutSrcId) {
        super(layoutSrcId);
    }

    public NewsFragment(){
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (AppCompatActivity) activity;
    }

    @Override
    void initViews(View rootView) {
        this.rootView = rootView;
        initToolBar();
        initViewPager();
        initTablayout();
    }

    private void initToolBar() {
        toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("33oz");
        activity.setSupportActionBar(toolbar);
    }

    private void initViewPager(){
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        NewsFragmentAdapter newsFragmentAdapter = new NewsFragmentAdapter(activity.getSupportFragmentManager());
        viewPager.setAdapter(newsFragmentAdapter);
    }

    private void initTablayout(){
        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        /*tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
