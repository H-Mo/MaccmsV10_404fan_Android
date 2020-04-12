package com.a404fan.video.constant;

/**
 * @author 林墨
 * [博客] http://div.moe
 * [时间] 20/3/30  21:20
 * [描述] Url常量类
 */
public class UrlConstant {

    /**
     * 影视系统网址
     */
    public static final String Base_Url = "https://404fan.com/";

    /**
     * 主页的轮播图数据
     */
    public static final String Home_Banner = Base_Url + "app/banner.json";

    /**
     * MaccmsV10的API接口
     */
    public static final String Base_Api = Base_Url + "api.php/";

    /**
     * 视频列表数据
     */
    public static final String Vod_List = Base_Api + "provide/vod/?ac=list";

    /**
     * 视频详情数据
     */
    public static final String Vod_Details = Base_Api + "provide/vod/?ac=detail";

}
