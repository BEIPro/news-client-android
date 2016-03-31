package com.song.normalclient.News;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devspark.progressfragment.ProgressFragment;
import com.song.normalclient.R;

/**
 * Created by songsubei on 28/03/16.
 */
public abstract class MyPrograssFragment extends ProgressFragment {

    private View mContentView;

    public MyPrograssFragment() {
    }

    public MyPrograssFragment(int layoutSrcId) {
        Bundle bundle = new Bundle();
        bundle.putInt("srcId", layoutSrcId);
        setArguments(bundle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        View rootView = null;
        if (bundle != null){
            int srcId;
            if ((srcId = bundle.getInt("srcId")) != 0){
                this.mContentView = inflater.inflate(srcId, container, false);
                rootView = inflater.inflate(R.layout.fragment_custom_progress, container, false);
                initViews(this.mContentView);
            }
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(mContentView);
        setEmptyText("emty");
    }

    abstract void initViews(View rootView);
}
