package com.song.normalclient.Data;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ExecutionException;

import rx.functions.Func0;

/**
 * Created by songsubei on 22/03/16.
 */
public class MNewsAPI{

    private static RequestQueue requestQueue;

    /*Observable getTopNews(URL url){

    }*/
    public static JSONArray getJsonArray(String url){
        return getNewsJsonArray(url);
    }


    /*private rx.Observable deferGetNewsJsonArray(final String url){
        return rx.Observable.defer(new Func0<rx.Observable<JSONArray>>() {
            @Override
            public rx.Observable<JSONArray> call() {
                return rx.Observable.just(getNewsJsonArray(url));
            }
        });

    }*/

    public static RequestQueue creatRequestQueue (Context context){
       return requestQueue = Volley.newRequestQueue(context);
    };


    private static JSONArray getNewsJsonArray(String url){
        if (requestQueue == null){
            return null;
        }
        RequestFuture<JSONArray> requestFuture = RequestFuture.newFuture();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, requestFuture, null){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("apikey","3dc0406a6b2ae6fa7e0eee155f201415");
                return headers;
            }
        };
        requestQueue.add(jsonArrayRequest);
        JSONArray jsonArray = null;
        try {
            jsonArray = requestFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
