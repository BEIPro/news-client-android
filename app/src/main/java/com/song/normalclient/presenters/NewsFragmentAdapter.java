package com.song.normalclient.presenters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.song.normalclient.News.EnterTainNewsFragment;
import com.song.normalclient.News.LocalNewsFragment;
import com.song.normalclient.News.OthersNewsFragment;
import com.song.normalclient.News.SportNewsFragment;
import com.song.normalclient.News.TechNewsFragment;
import com.song.normalclient.R;

/**
 * Created by songsubei on 01/10/15.
 */
public class NewsFragmentAdapter extends TaggedFragmentStatePagerAdapter {

    private String[] categories = {"Sport", "Tech", "Others", "Local", "Entertain"};

    public NewsFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new SportNewsFragment(R.layout.sport_news_fragment);
            case 1:
                return new TechNewsFragment(R.layout.tech_news_fragment);
            case 2:
                return new OthersNewsFragment(R.layout.others_news_fragment);
            case 3:
                return new LocalNewsFragment(R.layout.local_news_fragment);
            case 4:
                return new EnterTainNewsFragment(R.layout.entertain_news_fragment);
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
