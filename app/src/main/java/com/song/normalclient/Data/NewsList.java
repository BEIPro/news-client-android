package com.song.normalclient.Data;

import java.util.List;

/**
 * Created by songsubei on 24/03/16.
 */
public class NewsList {
    public String code;
    public String msg;
    public List<news> newslist;

    public static class news{
        public String ctime;
        public String title;
        public String description;
        public String picUrl;
        public String url;

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
}
