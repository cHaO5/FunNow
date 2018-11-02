package com.soa.FunNow.modules.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
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
import com.soa.FunNow.common.utils.Util;
import com.soa.FunNow.component.PLog;
import com.soa.FunNow.modules.main.domain.Movie;
import com.soa.FunNow.modules.main.domain.Weather;

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
        Movie testmovie = new Movie();
        testmovie.id = "111";
        testmovie.poster = "https://movie.douban.com/subject/26741061/photos?type=R";
        testmovie.title = "testtest";
        Movie testmovie2 = new Movie();
        testmovie2.id = "222";
        testmovie2.poster = "https://movie.douban.com/subject/26741061/photos?type=R";
        testmovie2.title = "test2";
        List<Movie> testlistmovie = new ArrayList<>();
        testlistmovie.add(testmovie);
        testlistmovie.add(testmovie2);
        for (Movie m : testlistmovie) {
            System.out.println(m.title);
            System.out.println(m.poster);
        }
        this.mMovieList = testlistmovie;
    }

    @Override
    public MoviePageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new MoviePageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_card, parent, false));
    }

    @Override
    public void onBindViewHolder(MoviePageViewHolder holder, int position) {
        System.out.println(position);
        holder.bind(mMovieList.get(position));
        holder.itemView.setOnClickListener(v -> mMoviePageClick.click(mMovieList.get(holder.getAdapterPosition())));
    }


    @Override
    public int getItemCount() {
        return 2;
    }

    public boolean isEmpty() {
        return 0 == getItemCount();
    }

    class MoviePageViewHolder extends BaseViewHolder<Movie> {

        @BindView(R.id.cardView)
        CardView mCardView;
        @BindView(R.id.movie_poster)
        ImageView mMoviePoster;
        @BindView(R.id.movie_title)
        TextView mMovieTitle;

        public MoviePageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(Movie movie) {
            try {
                mMovieTitle.setText(Util.safeText("aaa"));
            } catch (NullPointerException e) {
                PLog.e(e.getMessage());
            }

            Glide.with(mContext)
                    .load(SharedPreferenceUtil.getInstance().getInt("晴", R.mipmap.type_two_sunny))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mMoviePoster.setImageBitmap(resource);
                            mMoviePoster.setColorFilter(Color.WHITE);
                        }
                    });
            System.out.println(movie.title);
            System.out.println(movie.poster);
            int code = 1;
            new CardMovieHelper().applyStatus(code, movie.title, mCardView);
        }
    }

    public interface onMoviePageClick {
        void click(Movie movie);
    }

}
