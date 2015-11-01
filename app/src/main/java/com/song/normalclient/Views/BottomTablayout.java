package com.song.normalclient.Views;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;

import com.song.normalclient.R;

/**
 * Created by songsubei on 29/10/15.
 */
public class BottomTablayout extends TabLayout {

    public BottomTablayout(Context context) {
        super(context);
    }

    public BottomTablayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomTablayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTabsFromPagerAdapter(PagerAdapter adapter) {
        this.removeAllTabs();
        int i = 0;

        for(int count = adapter.getCount(); i < 3; ++i) {
            this.addTab(this.newTab().setIcon(R.drawable.mail_alt));
        }
    }
}
