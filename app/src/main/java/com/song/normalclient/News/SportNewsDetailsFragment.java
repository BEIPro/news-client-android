package com.song.normalclient.News;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.song.normalclient.Data.MNewsAPI;
import com.song.normalclient.Data.NewsList;
import com.song.normalclient.R;
import com.song.normalclient.Views.SwipeBackView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by songsubei on 26/03/16.
 */
public class SportNewsDetailsFragment extends MyPrograssFragment{

    private ImageView imageView;
    private TextView titleTextView;
    private TextView tagTextView;
    private TextView contentTextView;
    private NewsList.NewsDetails newsDetails;
    private Context context;
    private NewsList.news news;
    private SwipeBackView swipeBackView;

    public NewsList.news getNews() {
        return news;
    }

    public void setNews(NewsList.news news) {
        this.news = news;
    }

    public SportNewsDetailsFragment(int layoutSrcId) {
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
        final View rootView = super.onCreateView(inflater, container, savedInstanceState);
        swipeBackView = new SwipeBackView(context);
        swipeBackView.replaceLayer(rootView);
        LinearLayout.LayoutParams params;
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, windowManager.getDefaultDisplay().getHeight() / 4);
        imageView.setLayoutParams(params);

        ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                MNewsAPI.getNewsDetails(news, windowManager.getDefaultDisplay().getWidth(),windowManager.getDefaultDisplay().getHeight()/4)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<NewsList.NewsDetails>() {
                            @Override
                            public void call(NewsList.NewsDetails newsDetails) {
                                updateContentWithNewsDetails(newsDetails);
                            }
                        });
            }
        });
        return swipeBackView;
    }

    public void updateContentWithNewsDetails(NewsList.NewsDetails newsDetails){
        imageView.setImageBitmap(newsDetails.getDetailsBitmap());
        titleTextView.setText(newsDetails.getDetailsTitle());
        contentTextView.setText(newsDetails.getDetailsText());
        if(!newsDetails.getPicTag().equals("")){
            tagTextView.setText(newsDetails.getPicTag());
        }

        setContentShown(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentShown(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
