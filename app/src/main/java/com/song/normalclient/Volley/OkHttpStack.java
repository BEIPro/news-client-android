package com.song.normalclient.Volley;

import com.android.volley.toolbox.HurlStack;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.OkUrlFactory;

/**
 * Created by songsubei on 31/03/16.
 */
public class OkHttpStack extends HurlStack {


    private OkHttpClient okHttpClient;

    public OkHttpStack() {
        this(new OkHttpClient());
    }

    public OkHttpStack(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        OkUrlFactory okUrlFactory = new OkUrlFactory(okHttpClient);
        return okUrlFactory.open(url);
    }
}
