package com.song.normalclient.News;

import android.content.Context;
import android.os.*;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.song.normalclient.Data.MNewsAPI;
import com.song.normalclient.Data.MNewsContract;
import com.song.normalclient.Data.NewsList;
import com.song.normalclient.R;
import com.song.normalclient.presenters.NewsRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by songsubei on 27/09/15.
 */
public class TopNewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "TopNewsFragment";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private List<NewsList.news> newsList;
    private NewsRecycleViewAdapter newsRecycleViewAdapter;
    private View rootView;

    private static int currentPage = 1;

    public static final int DATA_SWAP = 10;
    public static final int DATA_ADD = 11;
    private boolean firstIn = true;

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
        this.rootView = rootView;
        MNewsAPI.creatRequestQueue(context);
        onRefresh();
        initSwipRefreshLayout(rootView);
    }

    void initRecycleView(View rootView){
        newsRecycleViewAdapter = new NewsRecycleViewAdapter(newsList);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.top_news_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        newsRecycleViewAdapter.setOnItemClickedListner(new NewsRecycleViewAdapter.NewsRecycleViewListener() {
            @Override
            public void onClick(NewsList.news news) {
                onItemClickListner onItemClickListner = (onItemClickListner)context;
                onItemClickListner.onItemClicked(news);
            }
        });
        recyclerView.setAdapter(newsRecycleViewAdapter);
        Log.e(TAG, "initRecycleView" + android.os.Process.myTid());
        recyclerView.setOnScrollListener(new UpPullLoadListner(layoutManager) {
            @Override
            void onLoadMore() {
                MNewsAPI.GetNewsList(MNewsContract.HTTPURL, MNewsContract.HTTPARG, currentPage)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<NewsList.news>>() {
                            @Override
                            public void call(List<NewsList.news> newses) {
                                if (newsList.size() <= currentPage *10){
                                    swapNewsList(newses, currentPage, DATA_ADD);
                                }
                                else {
                                    swapNewsList(newses, currentPage, DATA_SWAP);
                                }
                                newsRecycleViewAdapter.notifyDataSetChanged();
                            }
                        });
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
        MNewsAPI.GetNewsList(MNewsContract.HTTPURL, MNewsContract.HTTPARG, 1).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<List<NewsList.news>>() {

                    @Override
                    public void call(List<NewsList.news> newses) {
                        if (firstIn) {
                            swapNewsList(newses, 0, DATA_ADD);
                            initRecycleView(rootView);
                            firstIn = false;
                        }
                        else {
                            swapNewsList(newses, 0, DATA_SWAP);
                        }
                    }
                });
        if (!firstIn) {
            newsRecycleViewAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
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
                currentPage++;
                onLoadMore();
                loading = true;
            }
        }

        abstract void onLoadMore();
    }

    void swapNewsList(List<NewsList.news> srcNewses, int pageNum, int swapOrAdd){
        if(newsList == null){
            newsList = new ArrayList<>();
        }
        if(swapOrAdd == DATA_SWAP) {
            for (int i = 0; i < 10; i++) {
                newsList.set(i + 10 * pageNum, srcNewses.get(i));
            }
        }
        else if(swapOrAdd == DATA_ADD){
            newsList.addAll(srcNewses);
        }
    }

    public interface onItemClickListner{
        void onItemClicked(NewsList.news news);
    }

}
