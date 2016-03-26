package com.song.normalclient.News;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.song.normalclient.R;
import com.song.normalclient.presenters.MainLayoutAdapter;


/**
 * Created by songsubei on 26/09/15.
 */
public class MNewsActivity extends AppCompatActivity{

    private FragmentTransaction fragmentTransaction;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initViews();
    }

    void initViews(){

        initViewPager();
        initTablayout();
    }

    void initViewPager(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager = (ViewPager) findViewById(R.id.main_unscrollableviewpager);
        viewPager.setAdapter(new MainLayoutAdapter(fragmentManager));
    }

    void initTablayout(){
        tabLayout = (TabLayout) findViewById(R.id.main_tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
