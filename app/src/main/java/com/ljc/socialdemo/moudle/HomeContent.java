package com.ljc.socialdemo.moudle;

import java.util.List;

/**
 * Created by 18075 on 2018/11/9.
 */

public class HomeContent {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1532594882779443357
         * list_title : 跑步机和户外跑哪个好？你的膝盖选了跑步机
         * source :
         * author : 小P 院长
         * editor :
         * publish_time : 1541586537
         * status : 已发布
         * list_img : ["http://files.huobanys.com/group1/M00/00/AC/ChvHbFvivk-AfvD1AAFkJMCONQw48.jpeg"]
         * channel : 养身
         * summary : 跑步机和户外跑哪个好？你的膝盖选了跑步机
         * detail_url : https://mainapi.huobanys.com/api/h5/informationDetail/1532594882779443357
         */

        private String id;
        private String       list_title;
        private String       source;
        private String       author;
        private String       editor;
        private String       publish_time;
        private String       status;
        private String       channel;
        private String       summary;
        private String       detail_url;
        private List<String> list_img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getList_title() {
            return list_title;
        }

        public void setList_title(String list_title) {
            this.list_title = list_title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getDetail_url() {
            return detail_url;
        }

        public void setDetail_url(String detail_url) {
            this.detail_url = detail_url;
        }

        public List<String> getList_img() {
            return list_img;
        }

        public void setList_img(List<String> list_img) {
            this.list_img = list_img;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", list_title='" + list_title + '\'' +
                    ", source='" + source + '\'' +
                    ", author='" + author + '\'' +
                    ", editor='" + editor + '\'' +
                    ", publish_time='" + publish_time + '\'' +
                    ", status='" + status + '\'' +
                    ", channel='" + channel + '\'' +
                    ", summary='" + summary + '\'' +
                    ", detail_url='" + detail_url + '\'' +
                    ", list_img=" + list_img +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HomeContent{" +
                "data=" + data +
                '}';
    }
}
