package com.song.normalclient.presenters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.song.normalclient.News.EnterTainNewsFragment;
import com.song.normalclient.News.LifeFragment;
import com.song.normalclient.News.LocalNewsFragment;
import com.song.normalclient.News.NewsFragment;
import com.song.normalclient.News.SportNewsFragment;
import com.song.normalclient.News.TechNewsFragment;
import com.song.normalclient.News.TopNewsFragment;
import com.song.normalclient.News.TradeFragment;
import com.song.normalclient.R;

/**
 * Created by songsubei on 15/10/15.
 */
public class MainLayoutAdapter extends TaggedFragmentStatePagerAdapter {

    private String[] categories = {"Cultrue", "Trade", "Life"};

    public MainLayoutAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new NewsFragment(R.layout.news_layout);
            case 1:
                return new LifeFragment(R.layout.life_fragment);
            case 2:
                return new TradeFragment(R.layout.trade_fragment);
        }
        return null;
    }

    @Override
    public String getTag(int position) {
        return "TMP" + position;
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categories[position];
    }
}
