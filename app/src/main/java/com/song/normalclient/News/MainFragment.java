package com.song.normalclient.News;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.song.normalclient.R;
import com.song.normalclient.presenters.MainLayoutAdapter;

/**
 * Created by songsubei on 26/03/16.
 */
public class MainFragment extends BaseFragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentActivity activity;

    public MainFragment(int layoutSrcId) {
        super(layoutSrcId);
    }

    @Override
    void initViews(View rootView) {
        initViewPager(rootView);
        initTablayout(rootView);
    }

    void initViewPager(View rootView){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        viewPager = (ViewPager) rootView.findViewById(R.id.main_unscrollableviewpager);
        viewPager.setAdapter(new MainLayoutAdapter(fragmentManager));
    }

    void initTablayout(View rootView){
        tabLayout = (TabLayout) rootView.findViewById(R.id.main_tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity)activity;
    }
}
