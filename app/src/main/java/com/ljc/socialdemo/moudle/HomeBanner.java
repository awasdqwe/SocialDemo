package com.ljc.socialdemo.moudle;

import java.util.List;

/**
 * Created by 18075 on 2018/11/9.
 */

public class HomeBanner {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * news_photo : http://files.huobanys.com/group1/M00/00/81/ChvHbFszMTWATRXnAACM5WF4LNo494.jpg
         * multi_news_photo : ["http://files.huobanys.com/group1/M00/00/81/ChvHbFszMTWATRXnAACM5WF4LNo494.jpg"]
         * material_id : 1530079791556494050|1|1530081581229452708
         * news_id : 1530081581229452708
         * news_url : https://mainapp.huobanys.com/userApi/article_pagecontent_1530081581229452708_1530082066.html
         * desc : 伙伴医生
         * news_title : 【辟谣】喝茶等于喝农药？现在造谣都不用打草稿了吗？
         * publish_time : 1530082081
         * channel_name : 辟谣
         * news_type : 1
         * width : 100
         * height : 100
         */

        private String news_photo;
        private String       material_id;
        private String       news_id;
        private String       news_url;
        private String       desc;
        private String       news_title;
        private String       publish_time;
        private String       channel_name;
        private String       news_type;
        private String       width;
        private String       height;
        private List<String> multi_news_photo;

        public String getNews_photo() {
            return news_photo;
        }

        public void setNews_photo(String news_photo) {
            this.news_photo = news_photo;
        }

        public String getMaterial_id() {
            return material_id;
        }

        public void setMaterial_id(String material_id) {
            this.material_id = material_id;
        }

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
        }

        public String getNews_url() {
            return news_url;
        }

        public void setNews_url(String news_url) {
            this.news_url = news_url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getNews_title() {
            return news_title;
        }

        public void setNews_title(String news_title) {
            this.news_title = news_title;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getChannel_name() {
            return channel_name;
        }

        public void setChannel_name(String channel_name) {
            this.channel_name = channel_name;
        }

        public String getNews_type() {
            return news_type;
        }

        public void setNews_type(String news_type) {
            this.news_type = news_type;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public List<String> getMulti_news_photo() {
            return multi_news_photo;
        }

        public void setMulti_news_photo(List<String> multi_news_photo) {
            this.multi_news_photo = multi_news_photo;
        }
    }
}
