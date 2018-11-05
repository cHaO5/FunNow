package com.soa.FunNow.modules.main.adapter;

import android.content.Context;
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
import com.soa.FunNow.common.utils.SharedPreferenceUtil;
import com.soa.FunNow.common.utils.StringUtil;
import com.soa.FunNow.component.AnimRecyclerViewAdapter;
import com.soa.FunNow.component.PLog;
import com.soa.FunNow.modules.main.domain.CelebrityEntity;
import com.soa.FunNow.modules.main.domain.Movie;

public class MovieAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static String TAG = MovieAdapter.class.getSimpleName();

    private Context mContext;

    private static final int TYPE_ONE = 0;
    private static final int TYPE_TWO = 1;
    private static final int TYPE_THREE = 2;

    private Movie mMovieData;

    public MovieAdapter(Movie movieData) {
        this.mMovieData = movieData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == MovieAdapter.TYPE_ONE) {
            return MovieAdapter.TYPE_ONE;
        }
        if (position == MovieAdapter.TYPE_TWO) {
            return MovieAdapter.TYPE_TWO;
        }
        if (position == MovieAdapter.TYPE_THREE) {
            return MovieAdapter.TYPE_THREE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case TYPE_ONE:
                return new MovieAdapter.MovieInfoViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_movie_main_info, parent, false));
            case TYPE_TWO:
                return new MovieAdapter.MovieCastViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_movie_cast_info, parent, false));
            case TYPE_THREE:
                return new MovieAdapter.MovieButtonViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_movie_button, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_ONE:
                ((MovieAdapter.MovieInfoViewHolder) holder).bind(mMovieData);
                break;
            case TYPE_TWO:
                ((MovieAdapter.MovieCastViewHolder) holder).bind(mMovieData);
                break;
            case TYPE_THREE:
                ((MovieAdapter.MovieButtonViewHolder) holder).bind(mMovieData);
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
        return mMovieData.getTitle() != null ? 3 : 0;
    }

    class MovieInfoViewHolder extends BaseViewHolder<Movie> {

        @BindView(R.id.item_movie_poster)
        ImageView moviePoster;
        @BindView(R.id.item_movie_title)
        TextView movieTitle;
        @BindView(R.id.item_movie_origin_title)
        TextView movieOriginTitle;
        @BindView(R.id.item_movie_rate)
        TextView movieRate;
        @BindView(R.id.item_movie_genres)
        TextView movieGenres;

        MovieInfoViewHolder(View itemView) {
            super(itemView);
        }

        protected void bind(Movie movie) {
            try {
                movieTitle.setText(movie.getTitle());
                movieOriginTitle.setText(movie.getOriginal_title());
                String rating = String.format("%.1f", movie.getRating().getAverage())  + " / 10";
                movieGenres.setText(StringUtil.getListString(movie.getGenres(), '/'));
                movieRate.setText(rating);
                Glide.with(mContext)
                        .load(movie.getImages().getLarge())
                        .into(moviePoster);
            } catch (Exception e) {
                PLog.e(TAG, e.toString());
            }
        }
    }

    class MovieCastViewHolder extends BaseViewHolder<Movie> {

        @BindView(R.id.item_movie_casts)
        TextView movieCast;
        @BindView(R.id.item_movie_directors)
        TextView movieDir;

        MovieCastViewHolder(View itemView) {
            super(itemView);
        }

        protected void bind(Movie movie) {
            try {
                StringBuilder cast = new StringBuilder();
                cast.append("导演: ");
                for (int i = 0; i < movie.getCasts().size(); i++) {
                    cast.append(i == 0 ? "" : ", ").append(movie.getCasts().get(i).getName());
                }
                System.out.println(cast);
                movieCast.setText(cast);

                StringBuilder dir = new StringBuilder();
                dir.append("演员: ");
                for (int n = 0; n < movie.getDirectors().size(); n++) {
                    dir.append(n == 0 ? "" : ", ").append(movie.getDirectors().get(n).getName());
                }
                System.out.println(dir);
                movieDir.setText(dir);
            } catch (Exception e) {
                PLog.e(TAG, e.toString());
            }
        }
    }

    class MovieButtonViewHolder extends BaseViewHolder<Movie> {

        @BindView(R.id.item_movie_button)
        TextView movieButton;

        MovieButtonViewHolder(View itemView) {
            super(itemView);
        }

        protected void bind(Movie movie) {
        }
    }

}
