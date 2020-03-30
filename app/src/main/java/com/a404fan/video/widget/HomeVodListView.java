package com.a404fan.video.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.a404fan.video.R;
import com.a404fan.video.model.VodList;
import com.a404fan.video.utils.ImageUtils;
import com.a404fan.video.utils.StringUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

/**
 * @author 林墨
 * [博客] http://div.moe
 * [时间] 20/3/30  22:27
 * [描述] 主页的视频列表视图
 */
public class HomeVodListView extends FrameLayout {

    private ImageView icon_iv;
    private TextView title_tv;
    private GridLayout data_gl;

    private List<VodList.ListBean> mList;
    private int mMaxItemCount = 6;

    public HomeVodListView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public HomeVodListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeVodListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public HomeVodListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@NonNull Context context){
        // 设置布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_home_vod_list, this, false);
        icon_iv = view.findViewById(R.id.icon_iv);
        title_tv = view.findViewById(R.id.title_tv);
        data_gl = view.findViewById(R.id.data_gl);
        addView(view);
    }

    /**
     * 设置最大展示数据个数
     * @param maxItemCount      数据个数
     */
    public void setMaxItemCount(int maxItemCount){
        mMaxItemCount = maxItemCount;
    }

    /**
     * 设置这个列表块的标题
     * @param text
     */
    public void setTitle(String text){
        if(text == null){
            return;
        }
        title_tv.setText(text);
    }

    /**
     * 展示数据
     * @param data
     */
    public void show(List<VodList.ListBean> data){
        mList = data;
        // 清空原来的
        data_gl.removeAllViews();
        // 遍历填充子布局
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for(int i = 0; i < data.size(); i++) {
            // 限定数量
            if(i >= mMaxItemCount){
                break;
            }
            // 取出
            VodList.ListBean bean = data.get(i);
            // 充气布局
            View view = inflater.inflate(R.layout.item_home_vod, data_gl, false);
            // 找到控件
            ImageView pic = view.findViewById(R.id.pic);
            TextView title = view.findViewById(R.id.title);
            // 填充数据
            ImageUtils.load(getContext(), bean.getVod_pic(), pic);
            StringUtil.safeSetText(title, bean.getVod_name());
            // 设置事件
            view.setTag(bean);
            view.setOnClickListener(v -> {
                // TODO: 20/3/30 跳转详情页面
            });
            // 添加到容器
            data_gl.addView(view);
        }
    }

}
