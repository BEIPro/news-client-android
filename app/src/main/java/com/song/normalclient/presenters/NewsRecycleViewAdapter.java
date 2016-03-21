package com.song.normalclient.presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.song.normalclient.Data.NewsItem;
import com.song.normalclient.R;

import java.util.List;

/**
 * Created by songsubei on 22/02/16.
 */
public class NewsRecycleViewAdapter extends RecyclerView.Adapter{
    public static interface NewsRecycleViewListener{
        void onClick();
    }

    private List<NewsItem> newsList;
    private NewsRecycleViewListener newsRecycleViewListener;

    public NewsRecycleViewAdapter(List<NewsItem> newsList){
        this.newsList = newsList;
    }

    public void setOnRecycleViewAdapter(NewsRecycleViewListener newsRecycleViewListener){
        this.newsRecycleViewListener = newsRecycleViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, null);
        LinearLayout.LayoutParams params;
        WindowManager windowManager = (WindowManager) parent.getContext().getSystemService(Context.WINDOW_SERVICE);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, windowManager.getDefaultDisplay().getHeight()/6);
        view.setLayoutParams(params);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsViewHolder viewHolder = (NewsViewHolder) holder;
        NewsItem newsItem = newsList.get(position);
        viewHolder.imageView.setImageBitmap(newsItem.getScaleImg());
        viewHolder.breviaryNewsTextView.setText(newsItem.getBrief());
        viewHolder.newsType.setText(newsItem.getType());
        viewHolder.titleTextView.setText(newsItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        ImageView imageView;
        TextView titleTextView;
        TextView newsType;
        TextView breviaryNewsTextView;

        public NewsViewHolder(View itemView) {
            super(itemView);
//            linearLayout.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.breviary_image);
            titleTextView = (TextView) itemView.findViewById(R.id.breviary_title);
            newsType = (TextView) itemView.findViewById(R.id.news_type);
            breviaryNewsTextView = (TextView) itemView.findViewById(R.id.breviary_news);
        }

        @Override
        public void onClick(View v) {
            newsRecycleViewListener.onClick();
        }
    }
}
