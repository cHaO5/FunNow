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
//    @BindView(R.id.news_web)
//    WebView webView;
//    @BindView(R.id.news_web_title)
//    TextView title;

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
//        title.setText(news.getTitle());
//        WebView webView = (WebView) findViewById(R.id.news_web);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl(news.getUrl_3w());
//
//        Uri uri = Uri.parse(news.getUrl_3w());   //指定网址
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);           //指定Action
//        intent.setData(uri);                            //设置Uri
//        startActivity(intent);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new CAConstrantLayoutManager(this));
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