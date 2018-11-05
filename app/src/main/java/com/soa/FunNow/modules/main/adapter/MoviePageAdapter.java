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
//        Movie testmovie = new Movie();
////        testmovie.id = "111";
//        testmovie.setOriginal_title("https://www.google.com/url?sa=i&sour");
//        testmovie.setTitle("test1");
//        Movie testmovie2 = new Movie();
////        testmovie2.id = "222";
//        testmovie2.setOriginal_title("");
//        testmovie2.setTitle("test2");
//        Movie testmovie3 = new Movie();
////        testmovie3.id = "333";
//        testmovie3.setOriginal_title("ce=images&cd=&cad=rja&uact=8&ved=2ahUKEwit96r5jrXeAhVMHjQIHS");
//        testmovie3.setTitle("test3");
//        List<Movie> testlistmovie = new ArrayList<>();
//        testlistmovie.add(testmovie);
//        testlistmovie.add(testmovie2);
//        testlistmovie.add(testmovie3);
//        for (Movie m : testlistmovie) {
//            System.out.println(m.title);
//            System.out.println(m.poster);
//        }
        this.mMovieList = movieList;
//        this.mMovieList = testlistmovie;
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
//                System.out.println(movie.poster);
                Glide.with(mContext)
                        .load(movie.getImages().getLarge())
                        .into(mMoviePoster);

                mGenres.setText(StringUtil.getListString(movie.getGenres(), '/'));
//                final OkHttpClient client  = new OkHttpClient();
//                String url = movie.poster;
//                //创建请求
//                final Request request = new Request.Builder()
//                        .get()
//                        .url(url)
//                        .build();
//                //使用异步加载，判断连接网络是否成功
//                Call newCall = client.newCall(request);
//                newCall.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        //连接失败
////                        Toast.makeText(getApplicationContext(),"连接网络失败！请检查网络！",Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        System.out.println("qpqqqqqqqqqqqqqqqqqqqqqq");
//                        InputStream inputStream = response.body().byteStream();//得到图片的流
//                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                        mMoviePoster.setImageBitmap(bitmap);
//                    }
//                });
            } catch (NullPointerException e) {
                PLog.e(e.getMessage());
            }

//            Glide.with(mContext)
//                    .load(SharedPreferenceUtil.getInstance().getInt("晴", R.mipmap.type_two_sunny))
//                    .asBitmap()
//                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                            mMoviePoster.setImageBitmap(resource);
//                            mMoviePoster.setColorFilter(Color.WHITE);
//                        }
//                    });
//            System.out.println(movie.title);
//            System.out.println(movie.poster);
            int code = 1;
            new CardMovieHelper().applyStatus(code, movie.getTitle(), mCardView);
        }
    }

    public interface onMoviePageClick {
//        void longClick();
        void click(Movie movie);
    }

}
