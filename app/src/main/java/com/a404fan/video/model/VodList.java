package com.a404fan.video.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author 林墨
 * [博客] http://div.moe
 * [时间] 20/3/30  21:44
 * [描述] 视频列表数据模型
 */
public class VodList {


    /**
     * class : [{"type_id":1,"type_name":"电影"}]
     * code : 1
     * limit : 12
     * list : [{"type_id":14,"type_name":"港台剧","vod_en":"hanfuchenglongyueyuban","vod_id":87203,"vod_name":"憨夫成龙粤语版","vod_pic":"https://tu.tianzuida.com/pic/upload/vod/2020-03-30/1585567469.jpg","vod_play_from":"zuidam3u8","vod_remarks":"20集全","vod_time":"2020-03-30 20:00:06"}]
     * msg : 数据列表
     * page : 1
     * pagecount : 76
     * total : 901
     */

    private int code;
    private String limit;
    private String msg;
    private String page;
    private int pagecount;
    private int total;
    @SerializedName("class")
    private List<ClassBean> classX;
    private List<ListBean> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ClassBean> getClassX() {
        return classX;
    }

    public void setClassX(List<ClassBean> classX) {
        this.classX = classX;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ClassBean {
        /**
         * type_id : 1
         * type_name : 电影
         */

        private int type_id;
        private String type_name;

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }

    public static class ListBean {
        /**
         * type_id : 14
         * type_name : 港台剧
         * vod_en : hanfuchenglongyueyuban
         * vod_id : 87203
         * vod_name : 憨夫成龙粤语版
         * vod_pic : https://tu.tianzuida.com/pic/upload/vod/2020-03-30/1585567469.jpg
         * vod_play_from : zuidam3u8
         * vod_remarks : 20集全
         * vod_time : 2020-03-30 20:00:06
         */

        private int type_id;
        private String type_name;
        private String vod_en;
        private int vod_id;
        private String vod_name;
        private String vod_pic;
        private String vod_play_from;
        private String vod_remarks;
        private String vod_time;

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getVod_en() {
            return vod_en;
        }

        public void setVod_en(String vod_en) {
            this.vod_en = vod_en;
        }

        public int getVod_id() {
            return vod_id;
        }

        public void setVod_id(int vod_id) {
            this.vod_id = vod_id;
        }

        public String getVod_name() {
            return vod_name;
        }

        public void setVod_name(String vod_name) {
            this.vod_name = vod_name;
        }

        public String getVod_pic() {
            return vod_pic;
        }

        public void setVod_pic(String vod_pic) {
            this.vod_pic = vod_pic;
        }

        public String getVod_play_from() {
            return vod_play_from;
        }

        public void setVod_play_from(String vod_play_from) {
            this.vod_play_from = vod_play_from;
        }

        public String getVod_remarks() {
            return vod_remarks;
        }

        public void setVod_remarks(String vod_remarks) {
            this.vod_remarks = vod_remarks;
        }

        public String getVod_time() {
            return vod_time;
        }

        public void setVod_time(String vod_time) {
            this.vod_time = vod_time;
        }
    }
}
