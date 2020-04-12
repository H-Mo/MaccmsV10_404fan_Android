package com.a404fan.video.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import moe.div.mobase.activity.MoBaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.a404fan.video.R;
import com.a404fan.video.adapter.BannerAdapter;
import com.a404fan.video.model.BannerItem;
import com.a404fan.video.model.VodList;
import com.a404fan.video.utils.AppUtils;
import com.a404fan.video.utils.HttpHelper;
import com.a404fan.video.utils.ImageUtils;
import com.a404fan.video.utils.JsonUtil;
import com.a404fan.video.utils.LogUtils;
import com.a404fan.video.utils.StringUtil;
import com.a404fan.video.widget.HomeVodListView;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * @author 林墨
 * [博客] http://div.moe
 * [时间] 20/3/22  17:12
 * [描述] 主界面
 */
public class MainActivity extends MoBaseActivity {

    private Toolbar toolbar;
    private RecyclerView banner_rv;
    private EditText search_et;
    private TextView search_tv;

    private LinearLayoutManager mBannerLM;
    private LinearSnapHelper mLinearSnapHelper;
    private BannerAdapter mBannerAdapter;

    private HomeVodListView new_movie_hvv;
    private HomeVodListView new_opera_hvv;
    private HomeVodListView new_anime_hvv;
    private HomeVodListView new_variety_hvv;

    private int mCurrentPosition;               // 当前的索引
    private boolean isSlidingByHand = false;    //表示是否是手在滑动
    private boolean isSlidingAuto = true;       //表示是否自动滑动
    private Disposable mBannerInterval;

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        banner_rv = findViewById(R.id.banner_rv);
        search_et = findViewById(R.id.search_et);
        search_tv = findViewById(R.id.search_tv);

        new_movie_hvv = findViewById(R.id.new_movie_hvv);
        new_movie_hvv.setTitle("最新电影");

        new_opera_hvv = findViewById(R.id.new_opera_hvv);
        new_opera_hvv.setTitle("最新连续剧");

        new_anime_hvv = findViewById(R.id.new_anime_hvv);
        new_anime_hvv.setTitle("最新动漫");

        new_variety_hvv = findViewById(R.id.new_variety_hvv);
        new_variety_hvv.setTitle("最新综艺");

        // 初始化 RecyclerView
        mBannerLM = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        banner_rv.setLayoutManager(mBannerLM);
        mLinearSnapHelper = new LinearSnapHelper();
        mLinearSnapHelper.attachToRecyclerView(banner_rv);
        mBannerAdapter = new BannerAdapter();
        banner_rv.setAdapter(mBannerAdapter);
    }

    @Override
    protected void initData() {
        // 请求轮播图数据
        HttpHelper.getHomeBanner()
            .map(s -> JsonUtil.parseList(s, BannerItem.class))
            .doOnError(this::handlerBannerError)
            .doOnNext(this::handlerBannerShow)
            .subscribe();
        // 获取最新电影
        HttpHelper.getNewMovieList()
            .map(s -> JsonUtil.parseObject(s, VodList.class))
            .map(VodList::getList)
            .doOnError(this::handlerNewVoidListError)
            .doOnNext(new_movie_hvv::show)
            .subscribe();
        // 获取最新连续剧
        HttpHelper.getNewOperaList()
            .map(s -> JsonUtil.parseObject(s, VodList.class))
            .map(VodList::getList)
            .doOnError(this::handlerNewVoidListError)
            .doOnNext(new_opera_hvv::show)
            .subscribe();
        // 获取最新动漫
        HttpHelper.getNewAnimeList()
            .map(s -> JsonUtil.parseObject(s, VodList.class))
            .map(VodList::getList)
            .doOnError(this::handlerNewVoidListError)
            .doOnNext(new_anime_hvv::show)
            .subscribe();
        // 获取最新综艺
        HttpHelper.getNewVarietyList()
            .map(s -> JsonUtil.parseObject(s, VodList.class))
            .map(VodList::getList)
            .doOnError(this::handlerNewVoidListError)
            .doOnNext(new_variety_hvv::show)
            .subscribe();
    }

    @Override
    protected void initEvent() {
        // 对滑动事件进行过滤
        banner_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                switch (newState) {
                    case SCROLL_STATE_IDLE:  //（静止没有滚动）
                        if(isSlidingByHand){
                            mCurrentPosition = firstVisibleItemPosition;
                        }
                        break;
                    case SCROLL_STATE_DRAGGING:  //（正在被外部拖拽,一般为用户正在用手指滚动）
                        isSlidingByHand = true;
                        isSlidingAuto = false;
                        break;
                    case SCROLL_STATE_SETTLING:  //（自动滚动）
                        isSlidingAuto = !isSlidingByHand;
                        break;
                }
            }
        });
        // 轮播图点击事件
        mBannerAdapter.setOnItemClickListener((adapterView, view, position, id) -> {
            // TODO: 20/4/10
        });
        // 搜索事件
        search_tv.setOnClickListener(view -> {
            // 关闭软键盘
            AppUtils.closeSoftInput(this);
            // 获取搜索关键字
            String key = search_et.getText().toString();
            if("".equals(key.trim())){
                showMoErrorToast("请输入搜索关键字");
                return;
            }
            // 跳转搜索界面
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putExtra(SearchActivity.Tag_Key, key);
            startActivity(intent);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // 启动一个定时器，用于轮播图自动轮播
        mBannerInterval = Observable.interval(4, 4, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .subscribe(aLong -> {
                if (mBannerAdapter != null && mBannerAdapter.getItemCount() != 0) {
                    LogUtils.i("Banner定时器进行中");
                    mCurrentPosition++;
                    banner_rv.smoothScrollToPosition(mCurrentPosition);
                }
            });
    }


    @Override
    public void onStop() {
        super.onStop();
        // 取消定时器
        if(mBannerInterval != null){
            mBannerInterval.dispose();
            mBannerInterval = null;
            LogUtils.i("mo--", "轮播图定时器取消");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBannerInterval != null){
            mBannerInterval.dispose();
            mBannerInterval = null;
            LogUtils.i("mo--", "轮播图定时器取消");
        }
    }

    /**
     * 处理获取最近更新列表数据时的错误
     * @param throwable     发生的错误
     */
    private void handlerNewVoidListError(Throwable throwable){
        throwable.printStackTrace();
    }

    /**
     * 处理获取首页轮播图时的请求错误
     * @param throwable     错误信息
     */
    private void handlerBannerError(Throwable throwable){
        throwable.printStackTrace();
    }

    /**
     * 处理展示首页轮播图数据
     * @param bannerList    轮播图数据
     */
    private void handlerBannerShow(List<BannerItem> bannerList){
        if(bannerList.size() == 0){
            // 没有轮播图
            return;
        }
        // 设置轮播图
        mBannerAdapter.setData(bannerList);
        // 通知更新
        mBannerAdapter.notifyDataSetChanged();
        // 设置展示到中间
        AppUtils.MoveToPosition(mBannerLM, banner_rv, mBannerAdapter.getInitPosition());
        mCurrentPosition = mBannerAdapter.getInitPosition();
    }

}
