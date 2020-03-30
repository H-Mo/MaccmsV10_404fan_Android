package com.a404fan.video.utils;

import com.a404fan.video.constant.CategoryConstant;
import com.a404fan.video.constant.UrlConstant;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import moe.div.mohttp.MoHttp;

/**
 * @author 林墨
 * [博客] http://div.moe
 * [时间] 20/3/30  21:30
 * [描述] 网络请求帮助类
 */
public class HttpHelper {

    /**
     * 获取最近更新的电影列表
     * @return      最近更新电影列表
     */
    public static Observable<String> getNewMovieList(){
        String url = UrlConstant.Vod_List + "&t=" + + CategoryConstant.Movie_ID + "&pg=1&wd=&h=";
        return MoHttp.getInstance()
            .get(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取最近更新的连续剧列表
     * @return      最近更新连续剧列表
     */
    public static Observable<String> getNewOperaList(){
        String url = UrlConstant.Vod_List + "&t=" + + CategoryConstant.Opera_ID + "&pg=1&wd=&h=";
        return MoHttp.getInstance()
            .get(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取最近更新的动漫列表
     * @return      最近更新动漫列表
     */
    public static Observable<String> getNewAnimeList(){
        String url = UrlConstant.Vod_List + "&t=" + + CategoryConstant.Anime_ID + "&pg=1&wd=&h=";
        return MoHttp.getInstance()
            .get(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取最近更新的综艺列表
     * @return      最近更新综艺列表
     */
    public static Observable<String> getNewVarietyList(){
        String url = UrlConstant.Vod_List + "&t=" + + CategoryConstant.Variety_ID + "&pg=1&wd=&h=";
        return MoHttp.getInstance()
            .get(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

}
