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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

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
import rx.Scheduler;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by songsubei on 22/03/16.
 */
public class MNewsAPI {

    private static final String TAG = "MNewsAPI";

    private static RequestQueue requestQueue;

    public static Observable getNewsList(String url, String urlArgs, int pageNum) {
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
                news.setThumbnailBitmap(getNewsImage(news.getPicUrl()));
                return news;
            }
        }).buffer(10);
    }

    private static rx.Observable deferGetNewsJSONObject(String url, final String urlArgs, int pageNum) {
        final String mUrl;
        if (urlArgs != null) {
            mUrl = url + "?" + urlArgs + pageNum;
        } else {
            mUrl = url;
        }
        return rx.Observable.defer(new Func0<rx.Observable<JSONObject>>() {
            @Override
            public rx.Observable<JSONObject> call() {
                if (urlArgs != null) {
                    return rx.Observable.just(getNewsJSONObject(mUrl, true));
                } else {
                    return rx.Observable.just(getNewsJSONObject(mUrl, false));
                }
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

    private static rx.Observable deferGetNewsJSONObject(String url) {
        return deferGetNewsJSONObject(url, null, 0);
    }

    public static RequestQueue creatRequestQueue(Context context) {
        return requestQueue = Volley.newRequestQueue(context);
    }

    private static JSONObject getNewsJSONObject(String url, boolean needKey) {

        JsonObjectRequest jsonObjectRequest;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        if (needKey) {
            jsonObjectRequest = new JsonObjectRequest(url, requestFuture, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "getNewsJSONObject error" + error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    headers.put("apikey", "3dc0406a6b2ae6fa7e0eee155f201415");
                    return headers;
                }
            };
        } else {
            jsonObjectRequest = new JsonObjectRequest(url, requestFuture, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "getNewsJSONObject error" + error);
                }
            });
        }
        Log.d(TAG, "getNewsJSONObject" + url);
        requestQueue.add(jsonObjectRequest);
        JSONObject jsonObject = null;
        try {
            jsonObject = requestFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getNewsJSONObject" + jsonObject.toString());
        return jsonObject;
    }

    static List<NewsList.news> jsonToList(JSONObject jsonObject) {
        Gson gson = new Gson();
        NewsList newsList = gson.fromJson(jsonObject.toString(), NewsList.class);

        return newsList.newslist;
    }

    static Bitmap getNewsImage(String url) {
        RequestFuture<Bitmap> requestFuture = RequestFuture.newFuture();
        ImageRequest imageRequest = new ImageRequest(url, requestFuture, 0, 0, Bitmap.Config.RGB_565, requestFuture);
        requestQueue.add(imageRequest);
        Bitmap bitmap = null;
        try {
            bitmap = requestFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    static public Observable getNewsDetails(NewsList.news news) {
        return deferGetJsoupDoc(news.getUrl())
                .subscribeOn(Schedulers.io())
                .map(new Func1<Document, NewsList.NewsDetails>() {
                    @Override
                    public NewsList.NewsDetails call(Document document) {
                        StringBuffer stringBuffer = new StringBuffer();
                        Element articalNodes = document.getElementById("artical");
                        Element articalTitle = articalNodes.getElementById("artical_topic");
                        Element articalReal = articalNodes.getElementById("main_content");
                        Elements detailPicUrl = articalNodes.getElementsByClass("detailPic");
                        Elements imgUrls = detailPicUrl.select("img[src]");
                        String detailsImgUrl = imgUrls.attr("abs:src");
                        Elements elements = articalReal.select("p");
                        Elements tagelements = new Elements();
                        for (int i = 0; i < elements.size(); i++) {
                            if (elements.get(i).className().equals("picIntro")) {
                                tagelements.add(elements.get(i));
                                continue;
                            }
                            stringBuffer.append(elements.get(i).text());
                            stringBuffer.append("\r\n");
                        }
                        return new NewsList.NewsDetails(getNewsImage(detailsImgUrl),
                                stringBuffer.toString(), articalTitle.text(), tagelements.get(0).text());
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
}
