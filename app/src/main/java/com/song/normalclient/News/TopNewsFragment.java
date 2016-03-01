package com.song.normalclient.News;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.song.normalclient.R;
import com.song.normalclient.presenters.NewsRecycleViewAdapter;

import java.util.List;

/**
 * Created by songsubei on 27/09/15.
 */
public class TopNewsFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private Context context;

    public TopNewsFragment(int layoutSrcId) {
        super(layoutSrcId);
    }

    public TopNewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void initViews(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.top_news_recycleview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new NewsRecycleViewAdapter());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
