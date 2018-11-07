package com.soa.FunNow.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import butterknife.BindView;
import com.soa.FunNow.R;
import com.soa.FunNow.base.BaseViewHolder;
import com.soa.FunNow.common.utils.SharedPreferenceUtil;
import com.soa.FunNow.component.AnimRecyclerViewAdapter;
import com.soa.FunNow.component.PLog;
import com.soa.FunNow.modules.main.domain.News;

import static android.icu.text.Normalizer.NO;

public class NewsAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static String TAG = NewsAdapter.class.getSimpleName();

    private Context mContext;

    private static final int TYPE_ONE = 0;


    private News mNewsData;

    public NewsAdapter(News newsData) {
        this.mNewsData = newsData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == NewsAdapter.TYPE_ONE) {
            return NewsAdapter.TYPE_ONE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case TYPE_ONE:
                return new NewsAdapter.NewsViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_news_detail, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_ONE:
                ((NewsAdapter.NewsViewHolder) holder).bind(mNewsData);
                break;
            default:
                break;
        }
        if (SharedPreferenceUtil.getInstance().getMainAnim()) {
            showItemAnim(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        return mNewsData.getTitle() != null ? 1 : 0;
    }

    class NewsViewHolder extends BaseViewHolder<News> {

        @BindView(R.id.news_web)
        WebView webView;
//        @BindView(R.id.news_web_title)
//        TextView text;


        NewsViewHolder(View itemView) {
            super(itemView);
        }

        protected void bind(News news) {
            try {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(news.getUrl_3w());
//                text.setText(news.getTitle());
            } catch (Exception e) {
                PLog.e(TAG, e.toString());
            }
        }
    }
}