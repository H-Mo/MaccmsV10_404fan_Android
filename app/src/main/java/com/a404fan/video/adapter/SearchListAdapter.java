package com.a404fan.video.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a404fan.video.R;
import com.a404fan.video.model.VodList;
import com.a404fan.video.utils.ImageUtils;
import com.a404fan.video.utils.StringUtil;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import moe.div.mobase.adapter.MoBaseRecyclerAdapter;

/**
 * @author 林墨
 * [博客] http://div.moe
 * [时间] 20/4/12  16:34
 * [描述] 搜索结果列表适配器
 */
public class SearchListAdapter extends MoBaseRecyclerAdapter<VodList.ListBean, SearchListAdapter.SearchHolder> {

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_search, parent, false);
        return new SearchHolder(view);
    }

    @Override
    protected void onBindData(SearchHolder holder, int position) {
        Context context = holder.itemView.getContext();
        VodList.ListBean item = getItem(position);
        // 封面
        ImageUtils.load(context, item.getVod_pic(), holder.pic_iv);
        // 标题
        StringUtil.safeSetText(holder.title_tv, item.getVod_name());
        // 类型
        StringUtil.safeSetText(holder.type_tv, item.getType_name());
        // 备注
        StringUtil.safeSetText(holder.remarks_tv, item.getVod_remarks());
        // 更新时间
        String time = "更新时间：" + item.getVod_time();
        StringUtil.safeSetText(holder.time_tv, time);
    }


    public static class SearchHolder extends RecyclerView.ViewHolder{

        public ImageView pic_iv;        // 封面
        public TextView title_tv;       // 标题
        public TextView type_tv;        // 类型
        public TextView remarks_tv;     // 备注
        public TextView time_tv;        // 更新时间
        public CardView play_cv;        // 立即观看

        public SearchHolder(View itemView) {
            super(itemView);
            pic_iv = itemView.findViewById(R.id.pic_iv);
            title_tv = itemView.findViewById(R.id.title_tv);
            type_tv = itemView.findViewById(R.id.type_tv);
            remarks_tv = itemView.findViewById(R.id.remarks_tv);
            time_tv = itemView.findViewById(R.id.time_tv);
            play_cv = itemView.findViewById(R.id.play_cv);
        }

    }
}
