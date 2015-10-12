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
public class NewsFragment extends Fragment implements AppCompatCallback{

    private AppCompatDelegate appCompatDelegate;
    private Toolbar toolbar;
    private AppCompatActivity activity;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View rootView;


    public NewsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (AppCompatActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_layout, container, false);
        initViews();
        return rootView;
    }

    private void initViews(){
        initToolBar();
        initViewPager();
        initTablayout();
    }

    private void initToolBar() {
        toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
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
        tabLayout.addTab(tabLayout.newTab().setText("Tops"));
        tabLayout.addTab(tabLayout.newTab().setText("Tech"));
        tabLayout.addTab(tabLayout.newTab().setText("Sports"));
        tabLayout.addTab(tabLayout.newTab().setText("Locals"));
        tabLayout.addTab(tabLayout.newTab().setText("EnterTain"));
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
