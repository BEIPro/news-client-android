package com.song.normalclient.Data;

import android.graphics.Bitmap;

import com.song.normalclient.News.BaseFragment;

import java.util.List;

/**
 * Created by songsubei on 24/03/16.
 */
public class NewsList {
    public String code;
    public String msg;
    public List<news> newslist;

    public static class news{
        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;
        private Bitmap thumbnailBitmap;
        private Bitmap detailsBitmap;
        private String detailsText;
        private String detailsTitle;
        private String picTag;

        public Bitmap getDetailsBitmap() {
            return detailsBitmap;
        }

        public void setDetailsBitmap(Bitmap detailsBitmap) {
            this.detailsBitmap = detailsBitmap;
        }

        public String getDetailsText() {
            return detailsText;
        }

        public void setDetailsText(String detailsText) {
            this.detailsText = detailsText;
        }

        public String getDetailsTitle() {
            return detailsTitle;
        }

        public void setDetailsTitle(String detailsTitle) {
            this.detailsTitle = detailsTitle;
        }

        public String getPicTag() {
            return picTag;
        }

        public void setPicTag(String picTag) {
            this.picTag = picTag;
        }

        public Bitmap getDetaisBitmap() {
            return detaisBitmap;
        }

        public void setDetaisBitmap(Bitmap detaisBitmap) {
            this.detaisBitmap = detaisBitmap;
        }

        private Bitmap detaisBitmap;

        public Bitmap getThumbnailBitmap() {
            return thumbnailBitmap;
        }

        public void setThumbnailBitmap(Bitmap thumbnailBitmap) {
            this.thumbnailBitmap = thumbnailBitmap;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class NewsDetails{

        private Bitmap detailsBitmap;
        private String detailsText;
        private String detailsTitle;
        private String picTag;

        public NewsDetails(Bitmap detailsBitmap, String detailsText, String detailsTitle, String picTag) {
            this.detailsBitmap = detailsBitmap;
            this.detailsText = detailsText;
            this.detailsTitle = detailsTitle;
            this.picTag = picTag;
        }

        public String getDetailsTitle() {
            return detailsTitle;
        }

        public void setDetailsTitle(String detailsTitle) {
            this.detailsTitle = detailsTitle;
        }

        public String getPicTag() {
            return picTag;
        }

        public void setPicTag(String picTag) {
            this.picTag = picTag;
        }

        public Bitmap getDetailsBitmap() {
            return detailsBitmap;
        }

        public void setDetailsBitmap(Bitmap detailsBitmap) {
            this.detailsBitmap = detailsBitmap;
        }

        public String getDetailsText() {
            return detailsText;
        }

        public void setDetailsText(String detailsText) {
            this.detailsText = detailsText;
        }
    }
}
