package com.a404fan.video.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.a404fan.video.R;
import com.a404fan.video.model.BannerItem;
import com.a404fan.video.utils.ImageUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import moe.div.mobase.adapter.MoBaseRecyclerAdapter;

/**
 * @author 林墨
 * [博客] http://div.moe
 * [时间] 20/3/29  22:44
 * [描述] 主页的轮播图适配器
 */
public class BannerAdapter extends MoBaseRecyclerAdapter<BannerItem, BannerAdapter.BannerHolder> {

    public int getInitPosition(){
        if(mList == null){
            return 0;
        }
        int position = Integer.MAX_VALUE / 2;
        int diff = position % mList.size();
        return position - diff;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : Integer.MAX_VALUE ;
    }

    @Override
    public BannerItem getItem(int position) {
        position = position % mList.size();
        return super.getItem(position);
    }


    @Override
    protected void onBindData(BannerHolder holder, int position) {
        BannerItem item = getItem(position);
        ImageUtils.load(holder.itemView.getContext(), item.getImg(), holder.pic_iv);
    }

    @NonNull
    @Override
    public BannerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_home_banner, parent, false);
        return new BannerHolder(view);
    }

    public static class BannerHolder extends RecyclerView.ViewHolder{

        public ImageView pic_iv;

        public BannerHolder(View itemView) {
            super(itemView);
            pic_iv = itemView.findViewById(R.id.pic_iv);
        }
    }
}
