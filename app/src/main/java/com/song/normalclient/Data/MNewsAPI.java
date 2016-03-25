package com.song.normalclient.Data;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

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
public class MNewsAPI{

    private static final String TAG = "MNewsAPI";

    private static RequestQueue requestQueue;

    /*Observable getTopNews(URL url){

    }*/
    /*public static JSONObject getJsonArray(String url){
        return getNewsJsonArray(url);
    }*/


    /*private rx.Observable deferGetNewsJsonArray(final String url){
        return rx.Observable.defer(new Func0<rx.Observable<NewsList.news>>() {
            @Override
            public rx.Observable<List<NewsList.news>> call() {
                return rx.Observable.from(getNewsJsonArray(url));
            }
        });

    }*/
    public static Observable GetNewsList(String url, String urlArgs){
        return deferTransferNewsList(url, urlArgs).
                subscribeOn(Schedulers.io()).
                map(new Func1<JSONObject, List<NewsList.news>>() {
                    @Override
                    public List<NewsList.news> call(JSONObject jsonObject) {
                        return JsonToList(jsonObject);
                    }
                });
    }

    private static rx.Observable deferTransferNewsList(String url, String urlArgs){
        final String mUrl = url + "?" + urlArgs;
        return rx.Observable.defer(new Func0<rx.Observable<JSONObject>>() {
            @Override
            public rx.Observable<JSONObject> call() {
                return rx.Observable.just(getNewsJSONObject(mUrl));
            }
        });
    }

    public static RequestQueue creatRequestQueue (Context context){
       return requestQueue = Volley.newRequestQueue(context);
    };


    private static JSONObject getNewsJSONObject(String url){

        /*if (requestQueue == null){
            return null;
        }*/

        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, requestFuture, null){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("apikey","3dc0406a6b2ae6fa7e0eee155f201415");
                return headers;
            }
        };

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

    static List<NewsList.news> JsonToList(JSONObject jsonObject){
        Gson gson = new Gson();
        NewsList newsList = gson.fromJson(jsonObject.toString(), NewsList.class);

        return newsList.newslist;
    }

}
