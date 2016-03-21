package com.song.normalclient.News;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.song.normalclient.Data.NewsItem;
import com.song.normalclient.R;
import com.song.normalclient.presenters.NewsRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songsubei on 27/09/15.
 */
public class TopNewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private List<NewsItem> newsList;
    private NewsRecycleViewAdapter newsRecycleViewAdapter;
    private static int add;

    private Bitmap tmpBitmap;

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
        initList();
        initRecycleView(rootView);
        initSwipRefreshLayout(rootView);
    }

    void initRecycleView(View rootView){
        newsRecycleViewAdapter = new NewsRecycleViewAdapter(newsList);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.top_news_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsRecycleViewAdapter);
        recyclerView.setOnScrollListener(new UpPullLoadListner(layoutManager) {
            @Override
            void onLoadMore() {
                newsList.add(new NewsItem(tmpBitmap, "??+" + add++, "?+?+?" + add++,
                        "??+++??" + add++));
                newsRecycleViewAdapter.notifyDataSetChanged();
            }
        });
    }

    void initSwipRefreshLayout(View rootView){
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(R.color.design_textinput_error_color, R.color.material_blue_grey_800);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onRefresh() {
        newsList.set(0, new NewsItem(tmpBitmap, "??" + add++, "???" + add++, "????" + add++));

        newsRecycleViewAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }


    void initList(){
        tmpBitmap = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.mail_alt);
        newsList = new ArrayList<>();
        for(int i = 0; i < 10; i++)
            newsList.add(new NewsItem(tmpBitmap, "??" +i, "???" + i, "????" + i));
    }

    abstract class UpPullLoadListner extends RecyclerView.OnScrollListener{

        private boolean loading = true;
        private int preTotal = 0;
        private int firstVisibleItem, visibleItemCount, totalItemCount;

        LinearLayoutManager linearLayoutManager;

        public UpPullLoadListner(LinearLayoutManager linearLayoutManager) {
            this.linearLayoutManager = linearLayoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            totalItemCount = linearLayoutManager.getItemCount();
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
            visibleItemCount = recyclerView.getChildCount();

            if (loading) {
                if (totalItemCount > preTotal) {
                    loading = false;
                    preTotal = totalItemCount;
                }
            }
            if (!loading
                    && (totalItemCount - visibleItemCount) <= firstVisibleItem) {
//                currentPage++;
                onLoadMore();
                loading = true;
            }
        }

        abstract void onLoadMore();
    }

}
