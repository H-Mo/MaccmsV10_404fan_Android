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
        // 模拟数据的轮播图
        String json = "[\n" +
            "        {\n" +
            "            \"click\": \"因为太怕痛就全点防御力了\",\n" +
            "            \"img\": \"https://i2.hdslb.com/bfs/archive/d362ead28d2da7f402bb0cd96196eda3341fc421.jpg\",\n" +
            "            \"type\": \"key\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"click\": \"某科学的超电磁炮T\",\n" +
            "            \"img\": \"https://img01.sogoucdn.com/app/a/100520146/F28E7F01DFB7B9CA38FE69F8FB282590\",\n" +
            "            \"type\": \"key\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"click\": \"虚构推理\",\n" +
            "            \"img\": \"https://i0.hdslb.com/bfs/archive/5da4508e162add594aef189940583a1362b1c9b8.jpg\",\n" +
            "            \"type\": \"key\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"click\": \"花牌情缘\",\n" +
            "            \"img\": \"https://i0.hdslb.com/bfs/archive/fa7021417f61af884bbf67b9668f49bc43951525.jpg\",\n" +
            "            \"type\": \"key\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"click\": \"异种族风俗娘评鉴指南\",\n" +
            "            \"img\": \"https://img01.sogoucdn.com/app/a/100520146/FCF6892A008C2A1DD851A903647E2F3D\",\n" +
            "            \"type\": \"key\"\n" +
            "        }\n" +
            "    ]";
        List<BannerItem> bannerList = JsonUtil.parseList(json, BannerItem.class);
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
        // 获取最新电影
        Disposable newMovieSubscribe = HttpHelper.getNewMovieList()
            .map(s -> JsonUtil.parseObject(s, VodList.class))
            .map(VodList::getList)
            .doOnError(this::handlerNewVoidListError)
            .doOnNext(new_movie_hvv::show)
            .subscribe();
        // 获取最新连续剧
        Disposable newOperaSubscribe = HttpHelper.getNewOperaList()
            .map(s -> JsonUtil.parseObject(s, VodList.class))
            .map(VodList::getList)
            .doOnError(this::handlerNewVoidListError)
            .doOnNext(new_opera_hvv::show)
            .subscribe();
        // 获取最新动漫
        Disposable newAnimeSubscribe = HttpHelper.getNewAnimeList()
            .map(s -> JsonUtil.parseObject(s, VodList.class))
            .map(VodList::getList)
            .doOnError(this::handlerNewVoidListError)
            .doOnNext(new_anime_hvv::show)
            .subscribe();
        // 获取最新综艺
        Disposable newVarietySubscribe = HttpHelper.getNewVarietyList()
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
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
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

}
