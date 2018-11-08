package com.soa.FunNow.modules.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.soa.FunNow.R;
import com.soa.FunNow.base.BaseViewHolder;
import com.soa.FunNow.common.utils.SharedPreferenceUtil;
import com.soa.FunNow.common.utils.StringUtil;
import com.soa.FunNow.common.utils.Util;
import com.soa.FunNow.component.PLog;
import com.soa.FunNow.modules.main.domain.Movie;
import com.soa.FunNow.modules.main.domain.Weather;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MoviePageAdapter extends RecyclerView.Adapter<MoviePageAdapter.MoviePageViewHolder> {
    private Context mContext;
    private onMoviePageClick mMoviePageClick;
    private List<Movie> mMovieList;

    public void setMoviePageClick(onMoviePageClick moviePageClick) {
        this.mMoviePageClick = moviePageClick;
    }

    public MoviePageAdapter(List<Movie> movieList) {
        this.mMovieList = movieList;
    }

    public void updateList(List<Movie> data) {
        this.mMovieList = data;
        notifyDataSetChanged();
    }

    @Override
    public MoviePageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new MoviePageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_card, parent, false));
    }

    @Override
    public void onBindViewHolder(MoviePageViewHolder holder, int position) {
//        System.out.println(position);
        holder.bind(mMovieList.get(position));
        holder.itemView.setOnClickListener(v -> mMoviePageClick.click(mMovieList.get(holder.getAdapterPosition())));
    }


    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public boolean isEmpty() {
        return 0 == getItemCount();
    }

    class MoviePageViewHolder extends BaseViewHolder<Movie> {

        @BindView(R.id.cardView_movie)
        CardView mCardView;
        @BindView(R.id.movie_poster)
        ImageView mMoviePoster;
        @BindView(R.id.movie_title)
        TextView mMovieTitle;
        @BindView(R.id.movie_genres)
        TextView mGenres;

        public MoviePageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(Movie movie) {
            try {
                mMovieTitle.setText(Util.safeText(movie.getTitle()));
                Glide.with(mContext)
                        .load(movie.getImages().getLarge())
                        .into(mMoviePoster);

                mGenres.setText(StringUtil.getListString(movie.getGenres(), '/'));
            } catch (NullPointerException e) {
                PLog.e(e.getMessage());
            }
            int code = 1;
            new CardMovieHelper().applyStatus(code, movie.getTitle(), mCardView);
        }
    }

    public interface onMoviePageClick {
//        void longClick();
        void click(Movie movie);
    }

}
