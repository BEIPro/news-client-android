package com.song.normalclient.News;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.song.normalclient.Data.MNewsAPI;
import com.song.normalclient.Data.NewsList;
import com.song.normalclient.R;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by songsubei on 26/09/15.
 */
public class MNewsActivity extends AppCompatActivity implements TopNewsFragment.onItemClickListner {

    private TopNewsDetailsFragment topNewsDetailsFragment;
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
        topNewsDetailsFragment = new TopNewsDetailsFragment(R.layout.top_news_details_fragment);
    }

    void initMainFragment() {
        mainFragment = new MainFragment(R.layout.main_layout);
    }

    void setMainFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_activity_layout, mainFragment, null);
        fragmentTransaction.commit();
    }

    void setTopNewsDetailsFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(mainFragment);
        fragmentTransaction.add(R.id.main_activity_layout, topNewsDetailsFragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemClicked(NewsList.news news) {
        MNewsAPI.getNewsDetails(news)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList.NewsDetails>() {
                    @Override
                    public void call(NewsList.NewsDetails newsDetails) {
                        topNewsDetailsFragment.updateContentWithNewsDetails(newsDetails);
                        setTopNewsDetailsFragment();
                    }
                });
    }
}
