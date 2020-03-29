package com.a404fan.video.utils;

import android.content.Context;
import android.widget.ImageView;

import com.a404fan.video.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

/**
 * 类描述：加载图片工具类(仅仅包装一下，方便以后需要更换处理库时统一更换)
 * 创建人：林墨
 * 创建时间：2018/4/20.
 * 修改人：
 * 修改时间：
 */
public class ImageUtils {

    /**
     * 加载图片
     * @param context           上下文
     * @param url               图片地址
     * @param imageView         图片控件
     * @param def               默认显示的图片（失败时）
     */
    public static void load(Context context, String url, ImageView imageView, int def){
        if(context == null){
            return;
        }
        Glide.with(context)
                .load(url)
                .error(def)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView){
        load(context, url, imageView, R.mipmap.ic_launcher);
    }

    /**
     * 加载图片,带有一个圆角
     * @param context           上下文
     * @param url               网址
     * @param imageView         控件
     * @param def               默认显示
     * @param px                圆角像素
     */
    public static void round(Context context, String url, ImageView imageView, int def, int px){
        if(context == null){
            return;
        }
        Glide.with(context)
                .load(url)
                .error(def)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(context, px))
                .into(imageView);
    }

    public static void round(Context context, String url, ImageView imageView,  int px){
        round(context, url, imageView, R.mipmap.ic_launcher, px);
    }



    /**
     * 加载一个本地图片
     * @param context
     * @param file
     * @param imageView
     */
    public static void load(Context context, File file, ImageView imageView){
        if(context == null){
            return;
        }
        Glide.with(context)
            .load(file)
            .into(imageView);
    }

}
