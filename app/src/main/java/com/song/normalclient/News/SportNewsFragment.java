package com.song.normalclient.News;

import android.content.Context;
import android.os.*;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

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
public class SportNewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "SportNewsFragment";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private List<NewsList.news> newsList;
    private NewsRecycleViewAdapter newsRecycleViewAdapter;
    private View rootView;

    private int currentPage = 1;

    public static final int DATA_SWAP = 10;
    public static final int DATA_ADD = 11;
    private boolean firstIn = true;

    public SportNewsFragment(int layoutSrcId) {
        super(layoutSrcId);
    }

    public SportNewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "firstIn = " + firstIn + "currentPage = " + currentPage);
    }

    @Override
    void initViews(View rootView) {
        this.rootView = rootView;
        MNewsAPI.creatRequestQueue(context);
        onRefresh();
        initSwipRefreshLayout(rootView);
    }

    void initRecycleView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.top_news_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        Log.e(TAG, "initRecycleView" + android.os.Process.myTid());
        recyclerView.setOnScrollListener(new UpPullLoadListner(layoutManager) {
            @Override
            void onLoadMore() {
                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                MNewsAPI.getNewsList(MNewsContract.HTTPURL, MNewsContract.HTTPARG, currentPage, windowManager.getDefaultDisplay().getWidth()/3, windowManager.getDefaultDisplay().getHeight()/6 )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<NewsList.news>>() {
                            @Override
                            public void call(List<NewsList.news> newses) {
                                if (newsList.size() <= currentPage * 10) {
                                    swapNewsList(newses, currentPage, DATA_ADD);
                                } else {
                                    swapNewsList(newses, currentPage, DATA_SWAP);
                                }
                                newsRecycleViewAdapter.notifyDataSetChanged();
                            }
                        });
            }
        });
    }

    void initSwipRefreshLayout(View rootView) {
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(R.color.design_textinput_error_color, R.color.material_blue_grey_800);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        swipeRefreshLayout.setRefreshing(true
        );
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onRefresh() {
        if (firstIn) {
            initRecycleView(rootView);
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        MNewsAPI.getNewsList(MNewsContract.HTTPURL, MNewsContract.HTTPARG, 1, windowManager.getDefaultDisplay().getWidth()/3, windowManager.getDefaultDisplay().getHeight()/6).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<List<NewsList.news>>() {

                    @Override
                    public void call(List<NewsList.news> newses) {
                        if (firstIn) {
                            swapNewsList(newses, 0, DATA_ADD);
                            newsRecycleViewAdapter = new NewsRecycleViewAdapter(newsList);
                            newsRecycleViewAdapter.setOnItemClickedListner(new NewsRecycleViewAdapter.NewsRecycleViewListener() {
                                @Override
                                public void onClick(NewsList.news news) {
                                    onItemClickListner onItemClickListner = (onItemClickListner) context;
                                    onItemClickListner.onItemClicked(news);
                                }
                            });
                            recyclerView.setAdapter(newsRecycleViewAdapter);
                            firstIn = false;
                        } else {
                            swapNewsList(newses, 0, DATA_SWAP);
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        if (!firstIn) {
            newsRecycleViewAdapter.notifyDataSetChanged();
        }
    }

    abstract class UpPullLoadListner extends RecyclerView.OnScrollListener {

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

    void swapNewsList(List<NewsList.news> srcNewses, int pageNum, int swapOrAdd) {
        Log.d(TAG, "swapNewsList" + "size = ");
        if (newsList == null) {
            newsList = new ArrayList<>();
        }
        if (swapOrAdd == DATA_SWAP) {
            for (int i = 0; i < 10; i++) {
                newsList.set(i + 10 * pageNum, srcNewses.get(i));
            }
        } else if (swapOrAdd == DATA_ADD) {
            newsList.addAll(srcNewses);
        }
    }

    public interface onItemClickListner {
        void onItemClicked(NewsList.news news);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (newsList != null) {
            newsList.clear();
        }
        Log.d(TAG, "firstIn = " + firstIn + "currentPage = " + currentPage);
    }

}
