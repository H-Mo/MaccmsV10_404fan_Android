package com.a404fan.video.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a404fan.video.App;
import com.a404fan.video.R;
import com.a404fan.video.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import io.reactivex.Observable;
import moe.div.mobase.adapter.MoBaseRecyclerAdapter;
import moe.div.mobase.fragment.MoBaseFragment;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author 林墨
 * [博客] http://div.moe
 * [时间] 20/4/11  17:05
 * [描述] 封装好下拉刷新、上拉加载更多的页面
 */
public abstract class MoBaseDataListFragment<T> extends MoBaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    protected BGARefreshLayout mRecyclerViewRefresh;
    protected RecyclerView recyclerView;

    protected RelativeLayout no_data_rl;
    protected LinearLayout no_data_ll;
    protected ImageView no_data_iv;
    protected TextView no_data_hint_tv;
    protected TextView no_data_button_tv;

    protected RelativeLayout progress_rl;
    protected LinearLayout progress_ll;

    private int mPage = 1;

    protected MoBaseRecyclerAdapter mAdapter;

    protected boolean isHasLoadingMore = true;        // 是否有加载更多
    protected boolean isHeadLayoutScrollable = true;  // 头部布局是否可以滑动

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mo_base_data_list, container, false);
        mRecyclerViewRefresh = (BGARefreshLayout) view.findViewById(R.id.recyclerView_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        no_data_rl = (RelativeLayout) view.findViewById(R.id.no_data_rl);
        no_data_ll = (LinearLayout) view.findViewById(R.id.no_data_ll);
        no_data_iv = (ImageView) view.findViewById(R.id.no_data_iv);
        no_data_hint_tv = (TextView) view.findViewById(R.id.no_data_hint_tv);
        no_data_button_tv = (TextView) view.findViewById(R.id.no_data_button_tv);

        progress_rl = view.findViewById(R.id.progress_rl);
        progress_ll = view.findViewById(R.id.progress_ll);

        // 初始化刷新控件
        initRefreshLayout();

        return view;
    }

    @Override
    protected void initData() {
        // 初始化Rv
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 创建适配器
        mAdapter = getAdapterImpl();
        // 设置适配器
        recyclerView.setAdapter(mAdapter);
        // 执行网络请求
        loadData(true);
    }

    @Override
    protected void initEvent() {
        no_data_button_tv.setOnClickListener(v -> {
            // 展示加载中视图
            showProgressLayout();
            // 加载数据
            loadData(true);
        });
    }

    /**
     * 初始化刷新控件
     */
    private void initRefreshLayout() {
        // 为BGARefreshLayout 设置代理
        mRecyclerViewRefresh.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGAMoocStyleRefreshViewHolder refreshViewHolder = new BGAMoocStyleRefreshViewHolder(getActivity(), isHasLoadingMore);
        refreshViewHolder.setOriginalImage(R.mipmap.ic_launcher);
        refreshViewHolder.setUltimateColor(R.color.colorAccent);
        //        refreshViewHolder.setOriginalImage(R.mipmap.logo);
        //        refreshViewHolder.setUltimateColor(R.color.colorPrimary);
        // 设置下拉刷新和上拉加载更多的风格
        mRecyclerViewRefresh.setRefreshViewHolder(refreshViewHolder);
        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        // 设置正在加载更多时不显示加载更多控件
        // mRefreshLayout.setIsShowLoadingMoreView(false);
        // 设置正在加载更多时的文本
        //        refreshViewHolder.setLoadingMoreText("正在加载更多...");
        // 设置整个加载更多控件的背景颜色资源 id
        //        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.colorWhite);
        // 设置整个加载更多控件的背景 drawable 资源 id
        //        refreshViewHolder.setLoadMoreBackgroundDrawableRes(loadMoreBackgroundDrawableRes);
        // 设置下拉刷新控件的背景颜色资源 id
        //        refreshViewHolder.setRefreshViewBackgroundColorRes(refreshViewBackgroundColorRes);
        // 设置下拉刷新控件的背景 drawable 资源 id
        //        refreshViewHolder.setRefreshViewBackgroundDrawableRes(refreshViewBackgroundDrawableRes);
        // 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
        if(hasHeaderView()){
            mRecyclerViewRefresh.setCustomHeaderView(getHeaderView(), isHeadLayoutScrollable);
        }
        // 可选配置  -------------END
    }

    /**
     * 加载数据,由子类来实现
     * @param page      需要加载的页码
     * @return      加载到的数据
     */
    protected abstract Observable<String> loadDataImpl(int page);

    /**
     * 获取适配器,由子类来实现
     * @return      适配器
     */
    protected abstract MoBaseRecyclerAdapter getAdapterImpl();

    /**
     * 解析数据,由子类来实现
     * @param json      加载到的数据json
     * @return      数据集合
     */
    protected abstract List<T> parseJson(String json);

    /**
     * 设置没有数据界面的提示文字
     * @param text      提示内容
     */
    protected void setNoDataHint(String text){
        no_data_hint_tv.setText(text);
    }

    /**
     * 设置加载更多开关
     * @param has       是否开启加载更多
     */
    public void setHasLoadingMore(boolean has){
        isHasLoadingMore = has;
    }

    /**
     * 获取适配器
     * @return      适配器
     */
    public MoBaseRecyclerAdapter getAdapter(){
        return mAdapter;
    }

    /**
     * 加载数据
     * @param isRefresh     是否是刷新
     */
    protected void loadData(final boolean isRefresh){
        // 判断是否是刷新
        if(isRefresh){
            // 重置页码
            mPage = 1;
        }else {
            mPage ++;
        }
        // 执行加载
        loadDataImpl(mPage)
            .map(this::parseJson)
            .doOnError(this::handlerLoadDataError)
            .doOnNext(list -> handlerLoadDataSuccess(list, isRefresh))
            .subscribe();
    }

    /**
     * 触发了刷新数据
     */
    public void onRefreshData(){

    }

    /**
     * 触发没有更多的数据了
     */
    public void onLoadMoreNoMoreData(){
        // Snackbar.make(mRecyclerViewRefresh, "已经加载完所有数据", Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 触发数据加载完成
     */
    protected void onDataLoadComplete() {

    }

    /**
     * 是否需要无数据布局
     * @return  true为需要
     */
    protected boolean needNoDataLayout() {
        return true;
    }


    /**
     * 处理加载数据错误
     * @param throwable     错误
     */
    protected void handlerLoadDataError(Throwable throwable){
        throwable.printStackTrace();

        mRecyclerViewRefresh.endRefreshing();
        mRecyclerViewRefresh.endLoadingMore();

        showToast("加载数据失败");

        showErrorLayout();
    }

    /**
     * 处理加载数据成功
     * @param list          数据集合
     * @param isRefresh     是否是刷新
     */
    protected void handlerLoadDataSuccess(List<T> list, boolean isRefresh){
        mRecyclerViewRefresh.endRefreshing();
        mRecyclerViewRefresh.endLoadingMore();
        if(list == null){
            list = new ArrayList<T>();
        }
        // 设置数据
        if(isRefresh){
            onRefreshData();
            mAdapter.setData(list);
            if(list.size() == 0 && needNoDataLayout()){
                showNoDataLayout();
                return;
            }else {
                showDataLayout();
            }
            mAdapter.setFooterNoMoreData(false);
        }else {
            if(list.size() == 0){
                onLoadMoreNoMoreData();
                mAdapter.setFooterNoMoreData(true);
                return;
            }
            mAdapter.addData(list);
        }
        mAdapter.notifyDataSetChanged();
        onDataLoadComplete();
    }



    /**
     * 设置头部布局是否可以滚动
     * @param isHeadLayoutScrollable        是否可以滚动
     */
    protected void setHeadLayoutScrollable(boolean isHeadLayoutScrollable){
        this.isHeadLayoutScrollable  = isHeadLayoutScrollable;
    }

    /**
     * 是否存在头,如果需要头,则重新该方法并返回true
     * @return  true为需要
     */
    protected boolean hasHeaderView() {
        return false;
    }

    /**
     * 返回头布局,当需要头布局却不返回时将报错
     * @return      头布局
     */
    protected View getHeaderView() {
        return null;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        App.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData(true);
            }
        }, 1000);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        // 没有更多时,关闭加载更多
        if(mAdapter.isFooterNoMoreData()){
            return false;
        }
        if(isHasLoadingMore){
            App.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadData(false);
                }
            }, 1000);
            return true;
        }
        return false;
    }

    /**
     * 展示数据加载中布局
     */
    protected void showProgressLayout(){
        mRecyclerViewRefresh.setVisibility(View.GONE);
        no_data_rl.setVisibility(View.GONE);
        progress_rl.setVisibility(View.VISIBLE);
    }


    /**
     * 展示出错布局
     */
    protected void showErrorLayout(){
        mRecyclerViewRefresh.setVisibility(View.GONE);
        no_data_rl.setVisibility(View.VISIBLE);
        progress_rl.setVisibility(View.GONE);
    }

    /**
     * 展示无数据布局
     */
    protected void showNoDataLayout(){
        mRecyclerViewRefresh.setVisibility(View.GONE);
        no_data_rl.setVisibility(View.VISIBLE);
        progress_rl.setVisibility(View.GONE);
    }

    /**
     * 展示数据布局
     */
    protected void showDataLayout(){
        mRecyclerViewRefresh.setVisibility(View.VISIBLE);
        no_data_rl.setVisibility(View.GONE);
        progress_rl.setVisibility(View.GONE);
    }

}
