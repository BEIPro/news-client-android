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

import com.song.normalclient.R;

import java.util.List;

/**
 * Created by songsubei on 22/02/16.
 */
public class NewsRecycleViewAdapter extends RecyclerView.Adapter{
    public static interface NewsRecycleViewListener{
        void onClick();
    }

    private List newsList;
    private NewsRecycleViewListener newsRecycleViewListener;

    public NewsRecycleViewAdapter(){
//        this.newsList = newsList;
        Log.w("kidding me?", "???");
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
//        viewHolder.imageView.setBackground(R.drawable.mail_alt);
        viewHolder.breviaryNewsTextView.setText("Fucking news" + position);
        viewHolder.commentNumTextView.setText("Fucking itemNum");
        viewHolder.titleTextView.setText("Fucking title");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        ImageView imageView;
        TextView titleTextView;
        TextView commentNumTextView;
        TextView breviaryNewsTextView;

        public NewsViewHolder(View itemView) {
            super(itemView);
//            linearLayout.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.breviary_image);
            titleTextView = (TextView) itemView.findViewById(R.id.breviary_title);
            commentNumTextView = (TextView) itemView.findViewById(R.id.breviary_comments_num);
            breviaryNewsTextView = (TextView) itemView.findViewById(R.id.breviary_news);
        }

        @Override
        public void onClick(View v) {
            newsRecycleViewListener.onClick();
        }
    }
}
