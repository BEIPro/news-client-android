package com.song.normalclient.News;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.song.normalclient.Data.NewsList;
import com.song.normalclient.R;

/**
 * Created by songsubei on 26/03/16.
 */
public class TopNewsDetailsFragment extends BaseFragment{

    private ImageView imageView;
    private TextView textView;
    private NewsList.news news;

    public TopNewsDetailsFragment(int layoutSrcId) {
        super(layoutSrcId);
    }

    @Override
    void initViews(View rootView) {
        imageView = (ImageView) rootView.findViewById(R.id.top_details_imgview);
        textView = (TextView) rootView.findViewById(R.id.top_details_textview);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        imageView.setImageBitmap(news.getBitmap());
        textView.setText(news.getTitle());
        return rootView;
    }

    public void updateContent(NewsList.news news){
        this.news = news;
    }
}
