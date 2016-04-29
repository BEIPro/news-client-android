package com.song.normalclient.Data;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.song.normalclient.Volley.OkHttpStack;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by songsubei on 22/03/16.
 */
public class MNewsAPI {

    private static final String TAG = "MNewsAPI";

    private static RequestQueue requestQueue;
    private static boolean D = false;

    public static Observable getNewsList(String url, String urlArgs, int pageNum, final int maxWidth, final int maxHeight) {
        return deferGetNewsJSONObject(url, urlArgs, pageNum).
                subscribeOn(Schedulers.io()).
                map(new Func1<JSONObject, List<NewsList.news>>() {
                    @Override
                    public List<NewsList.news> call(JSONObject jsonObject) {
                        return jsonToList(jsonObject);
                    }
                }).
                concatMap(new Func1<List<NewsList.news>, Observable<NewsList.news>>() {
                    @Override
                    public Observable<NewsList.news> call(List<NewsList.news> newses) {
                        return Observable.from(newses);
                    }
                }).map(new Func1<NewsList.news, NewsList.news>() {

            @Override
            public NewsList.news call(NewsList.news news) {
                news.setThumbnailBitmap(getNewsImage(news.getPicUrl(), maxWidth, maxHeight));
                return news;
            }
        }).buffer(10);
    }

    private static rx.Observable deferGetNewsJSONObject(String url, final String urlArgs, final int pageNum) {
        final String mUrl;
        mUrl = url + "?" + urlArgs + pageNum;
        return rx.Observable.defer(new Func0<rx.Observable<JSONObject>>() {
            @Override
            public rx.Observable<JSONObject> call() {
                return rx.Observable.just(getNewsJSONObject(mUrl));
            }
        });
    }

    private static rx.Observable deferGetJsoupDoc(final String url) {
        return rx.Observable.defer(new Func0<rx.Observable<Document>>() {
            @Override
            public rx.Observable<Document> call() {
                return Observable.just(getJsoupDoc(url));
            }
        });
    }

    public static RequestQueue creatRequestQueue(Context context) {
        return requestQueue = Volley.newRequestQueue(context, new OkHttpStack());
    }

    private static JSONObject getNewsJSONObject(String url) {

        JsonObjectRequest jsonObjectRequest;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

        jsonObjectRequest = new JsonObjectRequest(url, requestFuture, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogD(TAG, "getNewsJSONObject error" + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("apikey", "3dc0406a6b2ae6fa7e0eee155f201415");
                return headers;
            }
        };
        LogD(TAG, "getNewsJSONObject" + url);
        requestQueue.add(jsonObjectRequest);
        JSONObject jsonObject = null;
        try {
            jsonObject = requestFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        LogD(TAG, "getNewsJSONObject" + jsonObject.toString());
        return jsonObject;
    }

    private static List<NewsList.news> jsonToList(JSONObject jsonObject) {
        Gson gson = new Gson();
        NewsList newsList = gson.fromJson(jsonObject.toString(), NewsList.class);

        return newsList.newslist;
    }

    private static Bitmap getNewsImage(final String url, int maxWidth, int maxHeight) {
        RequestFuture<Bitmap> requestFuture = RequestFuture.newFuture();
        ImageRequest imageRequest = new ImageRequest(url, requestFuture, maxWidth, maxHeight, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogD(TAG, "getNewsImage error url" + url);
                LogD(TAG, "getNewsImage error" + error);
            }
        });
        Bitmap bitmap = null;
        if(url.equals("")){
            return Bitmap.createBitmap(maxWidth, maxHeight, Bitmap.Config.RGB_565);
        }else {
            requestQueue.add(imageRequest);
            try {
                bitmap = requestFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    public static Observable getNewsDetails(NewsList.news news, final int maxWidth, final int maxHeight) {
        return deferGetJsoupDoc(news.getUrl())
                .subscribeOn(Schedulers.io())
                .map(new Func1<Document, NewsList.NewsDetails>() {
                    @Override
                    public NewsList.NewsDetails call(Document document) {
                        return getNewsDetailFromJoupDoc(document, maxWidth, maxHeight);
                    }
                });
    }

    private static Document getJsoupDoc(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    private static NewsList.NewsDetails getNewsDetailFromJoupDoc(Document document, int maxWidth, int maxHeight) {
        String detailsImgUrl = null;
        StringBuffer stringBuffer = new StringBuffer();
        Element articalNodes = document.getElementById("artical");
        Element articalTitle = articalNodes.getElementById("artical_topic");
        Element articalReal = articalNodes.getElementById("main_content");
        Elements elements = articalReal.select("p");
        Elements tagelements = new Elements();
        Elements detailPicUrl = articalNodes.getElementsByClass("detailPic");
        if(detailPicUrl.size() != 0){
            Elements imgUrls = detailPicUrl.select("img[src]");
            detailsImgUrl = imgUrls.attr("abs:src");
            LogD(TAG, "imgurl != null");
        }
        else {
            Elements e = elements.select("p");
            for(int i =0; i < e.size(); i++){
                    if(!e.get(i).childNode(0).attr("src").equals("")){
                        detailsImgUrl = e.get(i).child(0).attr("src");
                        break;
                    }
            }
            tagelements.add(elements.get(1));
            elements.remove(1);
        }

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).className().equals("picIntro")) {
                tagelements.add(elements.get(i));
                continue;
            }
            if(!elements.get(i).text().equals("")){
                stringBuffer.append(elements.get(i).text());
                stringBuffer.append("\r\n");
            }
        }

        return new NewsList.NewsDetails(getNewsImage(detailsImgUrl, maxWidth, maxHeight),
                stringBuffer.toString(), articalTitle.text(), tagelements.size() > 0 ? tagelements.get(0).text(): "");
    }

    public static void LogD(String TAG, String content){
        if (D){
            Log.d(TAG, content);
        }
    }
}
