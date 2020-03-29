package com.a404fan.video.utils;

import android.util.Log;

/**
 * @author 林墨
 * @time 19/1/4  22:53
 * @desc 打印日记工具,为了方便同意开启和关闭
 */
public class LogUtils {

    public final static boolean FLAG = true;

    public static void i(String tag,String msg){
        if(!FLAG){
            return;
        }
        android.util.Log.i(tag, msg);
    }

    public static void i(String msg){
        if(!FLAG){
            return;
        }
        android.util.Log.i("mo--", msg);
    }

}
