package com.soa.FunNow.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.soa.FunNow.R;
import com.soa.FunNow.base.BaseViewHolder;
import com.soa.FunNow.common.utils.StringUtil;
import com.soa.FunNow.common.utils.Util;
import com.soa.FunNow.component.PLog;
import com.soa.FunNow.modules.main.domain.News;

import java.util.List;

public class NewsPageAdapter extends RecyclerView.Adapter<NewsPageAdapter.NewsPageViewHolder> {
    private Context mContext;
    private NewsPageAdapter.onNewsPageClick mNewsPageClick;
    private List<News> mNewsList;

    public void setNewsPageClick(NewsPageAdapter.onNewsPageClick newsPageClick) {
        this.mNewsPageClick = newsPageClick;
    }

    public NewsPageAdapter(List<News> newsList) {
        this.mNewsList = newsList;
    }

    public void updateList(List<News> data) {
        this.mNewsList = data;
        notifyDataSetChanged();
    }

    @Override
    public NewsPageAdapter.NewsPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new NewsPageAdapter.NewsPageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_card, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsPageAdapter.NewsPageViewHolder holder, int position) {
        holder.bind(mNewsList.get(position));
        holder.itemView.setOnClickListener(v -> mNewsPageClick.click(mNewsList.get(holder.getAdapterPosition())));
    }


    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public boolean isEmpty() {
        return 0 == getItemCount();
    }

    class NewsPageViewHolder extends BaseViewHolder<News> {

        @BindView(R.id.cardView_news)
        CardView mCardView;
        @BindView(R.id.news_poster)
        ImageView mNewsPoster;
        @BindView(R.id.news_title)
        TextView mNewsTitle;
        @BindView(R.id.news_digest)
        TextView mNewsDigest;
        @BindView(R.id.news_source)
        TextView mNewsSource;
        @BindView(R.id.news_time)
        TextView mNewsTime;

        public NewsPageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(News news) {
            try {
                mNewsTitle.setText(news.getTitle());
                String digest = news.getDigest();
                if (!digest.equals("")) {
                    digest += "...";
                }
                mNewsDigest.setText(digest);
                mNewsSource.setText(news.getSource());
                mNewsTime.setText(news.getMtime());
                Glide.with(mContext)
                        .load(news.getImgsrc())
                        .into(mNewsPoster);
            } catch (NullPointerException e) {
                PLog.e(e.getMessage());
            }
        }
    }

    public interface onNewsPageClick {
        void click(News news);
    }

}
