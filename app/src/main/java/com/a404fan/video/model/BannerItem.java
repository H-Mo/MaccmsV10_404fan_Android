package com.a404fan.video.model;

/**
 * @author 林墨
 * [博客] http://div.moe
 * [时间] 20/3/29  22:46
 * [描述] 轮播图子项
 */
public class BannerItem {

    /**
     * click :
     * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541693238829&di=e8c7aa2489f2dace3c25871143ba5d50&imgtype=0&src=http%3A%2F%2Fwx2.sinaimg.cn%2Flarge%2F740ca5e5gy1fmfzw9bwc5j21gj0zaqv6.jpg
     * type : none
     */

    private String click;
    private String img;
    private String type;       // web:网页内容 |  key:搜索关键字

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
