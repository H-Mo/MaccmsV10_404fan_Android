package com.a404fan.video.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.a404fan.video.R;
import com.a404fan.video.fragment.SearchListFragment;
import com.a404fan.video.utils.FragmentUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import moe.div.mobase.activity.MoBaseActivity;

/**
 * @author 林墨
 * [博客] http://div.moe
 * [时间] 20/4/10  23:15
 * [描述] 搜索界面
 */
public class SearchActivity extends MoBaseActivity {

    public static final String Tag_Key = "Tag_Key";

    public Toolbar toolbar;
    public ImageView toolbar_icon;
    public TextView toolbar_title;

    private String mKey;

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        toolbar_icon = findViewById(R.id.toolbar_icon);
        toolbar_title = findViewById(R.id.toolbar_title);
    }

    @Override
    protected void initData() {
        // 接收传递过来的搜索关键字
        mKey = getIntent().getStringExtra(Tag_Key);
        if(mKey == null || "".equals(mKey.trim())){
            // 没有关键子，提示错误
            showErrorDialog("错误", "没有搜索词", "关闭", (dialog, which) -> finish());
            return;
        }
        // 设置到标题
        toolbar_title.setText(mKey);
        // 加载Fragment
        SearchListFragment searchListFragment = new SearchListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SearchActivity.Tag_Key, mKey);
        searchListFragment.setArguments(bundle);
        FragmentUtils.replaceFragment(R.id.fl, searchListFragment, this);
    }

    @Override
    protected void initEvent() {
        // 回退事件
        toolbar_icon.setOnClickListener(v -> onBackPressed());
    }

}
