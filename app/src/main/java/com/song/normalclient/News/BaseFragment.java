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

    private int layoutSrcId;

    public BaseFragment() {
    }

    public BaseFragment(int layoutSrcId) {
        this.layoutSrcId = layoutSrcId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(layoutSrcId, container, false);
        return rootView;
    }
}
