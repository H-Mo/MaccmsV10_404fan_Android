package com.a404fan.video.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a404fan.video.activity.SearchActivity;
import com.a404fan.video.adapter.SearchListAdapter;
import com.a404fan.video.model.VodList;
import com.a404fan.video.utils.HttpHelper;
import com.a404fan.video.utils.JsonUtil;
import com.a404fan.video.utils.LogUtils;
import com.a404fan.video.widget.GlideOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Observable;
import moe.div.mobase.adapter.MoBaseRecyclerAdapter;

/**
 * @author 林墨
 * [博客] http://div.moe
 * [时间] 20/4/11  18:19
 * [描述] 搜索列表页面
 */
public class SearchListFragment extends MoBaseDataListFragment<VodList.ListBean> {

    private String mKey;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 取出传递过来的搜索词
        Bundle bundle = getArguments();
        if(bundle != null){
            mKey = bundle.getString(SearchActivity.Tag_Key);
        }
        if(mKey == null){
            mKey = "";
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected Observable<String> loadDataImpl(int page) {
        return  HttpHelper.getSearchList(page, mKey);
    }

    @Override
    protected MoBaseRecyclerAdapter getAdapterImpl() {
        return new SearchListAdapter();
    }

    @Override
    protected List<VodList.ListBean> parseJson(String json) {
        VodList vodList = JsonUtil.parseObject(json, VodList.class);
        if(vodList == null || vodList.getList() == null){
            return new ArrayList<VodList.ListBean>();
        }
        return vodList.getList();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        // 列表子项的点击事件
        mAdapter.setOnItemClickListener((parent, view, position, id) -> {
            // TODO: 20/4/12
        });
    }
}
