package com.song.normalclient.Data;

import android.graphics.Bitmap;

/**
 * Created by songsubei on 20/03/16.
 */
public class NewsItem{

    private Bitmap scaleImg;
    private String title;
    private String brief;
    private String type;

    public NewsItem(Bitmap scaleImg, String title, String brief, String type) {
        this.scaleImg = scaleImg;
        this.title = title;
        this.brief = brief;
        this.type = type;
    }

    public Bitmap getScaleImg() {
        return scaleImg;
    }

    public void setScaleImg(Bitmap scaleImg) {
        this.scaleImg = scaleImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
