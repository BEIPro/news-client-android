package com.song.normalclient.presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.song.normalclient.Data.NewsList;
import com.song.normalclient.R;

import java.util.List;

/**
 * Created by songsubei on 22/02/16.
 */
public class NewsRecycleViewAdapter extends RecyclerView.Adapter{
    public static interface NewsRecycleViewListener{
        void onClick(NewsList.news news);
    }

    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;


    private List<NewsList.news> newsList;
    private NewsRecycleViewListener newsRecycleViewListener;

    public NewsRecycleViewAdapter(List<NewsList.news> newsList){
        this.newsList = newsList;
    }

    public void setOnItemClickedListner(NewsRecycleViewListener newsRecycleViewListener){
        this.newsRecycleViewListener = newsRecycleViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false);
            Log.d("NewsRecycleViewAdapter","onCreateViewHolder");
            return new NewsViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycleview_foot_view, parent, false);
            return new FootViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NewsViewHolder) {
            NewsViewHolder viewHolder = (NewsViewHolder) holder;
            NewsList.news newsItem = newsList.get(position);

            RelativeLayout.LayoutParams params;
            WindowManager windowManager = (WindowManager) viewHolder.itemView.getContext().getSystemService(Context.WINDOW_SERVICE);
            params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, windowManager.getDefaultDisplay().getHeight() / 6);
            viewHolder.itemView.setLayoutParams(params);
            params = new RelativeLayout.LayoutParams(windowManager.getDefaultDisplay().getWidth() / 3, LinearLayout.LayoutParams.MATCH_PARENT);
            viewHolder.imageView.setLayoutParams(params);

            viewHolder.imageView.setImageBitmap(newsItem.getThumbnailBitmap());
            viewHolder.newsSrcTextView.setText(newsItem.getDescription());
            viewHolder.newsTime.setText(newsItem.getCtime());
            viewHolder.titleTextView.setText(newsItem.getTitle());
            viewHolder.itemView.setTag(newsItem);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size() + 1;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;
        private TextView titleTextView;
        private TextView newsTime;
        private TextView newsSrcTextView;
        private View itemView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            Log.d("NewsRecycleViewAdapter", " new NewsViewHolder");
            itemView.setOnClickListener(this);
            this.itemView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.breviary_image);
            titleTextView = (TextView) itemView.findViewById(R.id.breviary_title);
            newsTime = (TextView) itemView.findViewById(R.id.news_time);
            newsSrcTextView = (TextView) itemView.findViewById(R.id.news_source);
        }

        @Override
        public void onClick(View v) {
            newsRecycleViewListener.onClick((NewsList.news) v.getTag());
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder{

        private ProgressBar progressBar;
        private TextView textView;

        public FootViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.foot_progressbar);
            textView = (TextView) itemView.findViewById(R.id.foot_view_item_tv);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1){
            return TYPE_FOOTER;
        }
        else {
            return TYPE_ITEM;
        }
    }
}
