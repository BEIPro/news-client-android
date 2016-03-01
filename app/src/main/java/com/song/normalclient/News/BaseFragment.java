package com.song.normalclient.News;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by songsubei on 27/09/15.
 */
public class BaseFragment extends Fragment {

    public BaseFragment() {
    }

    public BaseFragment(int layoutSrcId) {
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
                    rootView = inflater.inflate(srcId, container, false);
                    initViews(rootView);
                }
        }
        return rootView;
    }

    void initViews(View rootView){
    };
}
