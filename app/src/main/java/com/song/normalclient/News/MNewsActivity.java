package com.song.normalclient.News;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.song.normalclient.Data.NewsList;
import com.song.normalclient.R;


/**
 * Created by songsubei on 26/09/15.
 */
public class MNewsActivity extends AppCompatActivity implements SportNewsFragment.onItemClickListner {

    private SportNewsDetailsFragment sportNewsDetailsFragment;
    private MainFragment mainFragment;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        initViews();
        setMainFragment();
    }

    void initViews() {
        initMainFragment();
        initTopNewsDetailsFragment();
    }

    void initTopNewsDetailsFragment() {
        sportNewsDetailsFragment = new SportNewsDetailsFragment(R.layout.sport_news_details_fragment);
    }

    void initMainFragment() {
        mainFragment = new MainFragment(R.layout.main_layout);
    }

    void setMainFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_activity_layout, mainFragment, null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();
    }

    void setTopNewsDetailsFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(mainFragment);
        fragmentTransaction.add(R.id.main_activity_layout, sportNewsDetailsFragment, null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemClicked(NewsList.news news) {

        setTopNewsDetailsFragment();
        sportNewsDetailsFragment.setNews(news);
    }
}
