package com.song.normalclient.News;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.song.normalclient.Data.NewsList;
import com.song.normalclient.R;

/**
 * Created by songsubei on 26/03/16.
 */
public class TopNewsDetailsFragment extends BaseFragment{

    private ImageView imageView;
    private TextView titleTextView;
    private TextView tagTextView;
    private TextView contentTextView;
    private NewsList.NewsDetails newsDetails;
    private Context context;

    public TopNewsDetailsFragment(int layoutSrcId) {
        super(layoutSrcId);
    }

    @Override
    void initViews(View rootView) {
        imageView = (ImageView) rootView.findViewById(R.id.top_details_imgview);
        titleTextView = (TextView) rootView.findViewById(R.id.top_details_news_title);
        contentTextView = (TextView) rootView.findViewById(R.id.top_details_content);
        tagTextView = (TextView) rootView.findViewById(R.id.pic_tag);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        LinearLayout.LayoutParams params;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, windowManager.getDefaultDisplay().getHeight() / 4);
        imageView.setLayoutParams(params);

        imageView.setImageBitmap(newsDetails.getDetailsBitmap());
        titleTextView.setText(newsDetails.getDetailsTitle());
        contentTextView.setText(newsDetails.getDetailsText());
        tagTextView.setText(newsDetails.getPicTag());
        return rootView;
    }

    public void updateContentWithNewsDetails(NewsList.NewsDetails newsDetails){
        this.newsDetails = newsDetails;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
