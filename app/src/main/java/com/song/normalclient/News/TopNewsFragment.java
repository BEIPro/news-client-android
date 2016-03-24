package com.song.normalclient.News;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.song.normalclient.Data.MNewsAPI;
import com.song.normalclient.Data.MNewsContract;
import com.song.normalclient.Data.NewsItem;
import com.song.normalclient.R;
import com.song.normalclient.presenters.NewsRecycleViewAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by songsubei on 27/09/15.
 */
public class TopNewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "TopNewsFragment";

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
        Log.e("fuck", "?????" + android.os.Process.myTid());
        recyclerView.setOnScrollListener(new UpPullLoadListner(layoutManager) {
            @Override
            void onLoadMore() {
                android.os.Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MNewsAPI.creatRequestQueue(context);
                        Observable.just(MNewsContract.URL)
                                .subscribeOn(Schedulers.io())
                                .map(new Func1<String, Object>() {
                                    @Override
                                    public Object call(String s) {
                                        Log.e(TAG, s);
                                        return MNewsAPI.getJsonArray(s);
                                    }
                                }).subscribe(new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                Log.e(TAG, o.toString());
                            }
                        });


                        Log.e(TAG, "???" + android.os.Process.myTid());
//                        Log.e("fuck", String.valueOf(MNewsAPI.getJsonArray(MNewsContract.URL)));
                        newsList.add(new NewsItem(tmpBitmap, "??+" + add++, "?+?+?" + add++,
                                "??+++??" + add++));
                        newsRecycleViewAdapter.notifyDataSetChanged();
                    }
                }, 2500);
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
