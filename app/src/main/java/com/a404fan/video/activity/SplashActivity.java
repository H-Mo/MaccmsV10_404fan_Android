package com.a404fan.video.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a404fan.video.R;
import com.a404fan.video.utils.AppUtils;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import moe.div.mobase.activity.MoBaseActivity;

/**
 * @author 林墨
 * [博客] http://div.moe
 * [时间] 20/3/22  17:26
 * [描述] 开屏页
 */
public class SplashActivity extends MoBaseActivity {

    private ImageView banner_iv;
    private TextView skip_tv;
    private Disposable mDisposable;

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        // 全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // APP在后台,用户又点击图标启动APP时
        if (!isTaskRoot()) {
            finish();
            return;
        }
        // 设置布局并找到控件
        setContentView(R.layout.avtivity_splash);
        banner_iv = findViewById(R.id.banner_iv);
        skip_tv = findViewById(R.id.skip_tv);
    }

    @Override
    protected void initData() {
        // 获取最新缓存的广告图
        Drawable drawable = getResources().getDrawable(R.mipmap.splash_banner);
        // 设置给控件
        banner_iv.setImageDrawable(drawable);
    }

    @Override
    protected void initEvent() {
        // 启动一个定时器
        mDisposable = Flowable.intervalRange(1, 5, 1, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(this::changeIntervalText)
            .doOnComplete(this::intentMainActivity)
            .subscribe();
        // 跳过事件
        skip_tv.setOnClickListener(view -> intentMainActivity());
        // 广告点击事件
        banner_iv.setOnClickListener(this::bannerClick);
    }

    /**
     * 广告图点击事件
     * @param view      被点击视图
     */
    private void bannerClick(View view){
        String url = "https://404fan.com/index.php/vod/detail/id/26622.html";
        AppUtils.openWebUrl(this, url);
    }

    /**
     * 改变倒计时显示文本
     * @param aLong     定时器计时数值
     */
    private void changeIntervalText(long aLong){
        // 改变倒计时显示文本
        String hint = "跳过(" + (5 - aLong) + "秒)";
        skip_tv.setText(hint);
    }

    /**
     * 跳转主界面并关闭当前界面
     */
    private void intentMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取掉定时器
        if(mDisposable != null){
            mDisposable.dispose();
        }
    }
}
