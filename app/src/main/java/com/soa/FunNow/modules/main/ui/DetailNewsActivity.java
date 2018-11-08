package com.soa.FunNow.modules.main.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import butterknife.BindView;
import com.soa.FunNow.R;
import com.soa.FunNow.base.ToolbarActivity;
import com.soa.FunNow.common.IntentKey;
import com.soa.FunNow.modules.main.adapter.NewsAdapter;
import com.soa.FunNow.modules.main.domain.News;

public class DetailNewsActivity extends ToolbarActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected int layoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewWithData();
    }

    private void initViewWithData() {
        Intent intent = getIntent();
        News news = (News) intent.getSerializableExtra(IntentKey.NEWS);
        if (news == null) {
            finish();
        }
        safeSetTitle(news.getTitle());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        NewsAdapter mAdapter = new NewsAdapter(news);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public static void launch(Context context, News news) {
        Intent intent = new Intent(context, DetailNewsActivity.class);
        intent.putExtra(IntentKey.NEWS, news);
        context.startActivity(intent);
    }
}