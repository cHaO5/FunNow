package com.soa.FunNow.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.soa.FunNow.R;
import com.soa.FunNow.base.BaseViewHolder;
import com.soa.FunNow.common.utils.SharedPreferenceUtil;
import com.soa.FunNow.common.utils.TimeUitl;
import com.soa.FunNow.common.utils.Util;
import com.soa.FunNow.component.AnimRecyclerViewAdapter;
import com.soa.FunNow.component.ImageLoader;
import com.soa.FunNow.component.PLog;
import com.soa.FunNow.modules.main.domain.Movie;
import com.soa.FunNow.modules.main.domain.Weather;

public class MovieAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static String TAG = MovieAdapter.class.getSimpleName();

    private Context mContext;

    private static final int TYPE_ONE = 0;

    private Movie mMovieData;

    public MovieAdapter(Movie movieData) {
        this.mMovieData = movieData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == MovieAdapter.TYPE_ONE) {
            return MovieAdapter.TYPE_ONE;
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
            default:
                break;
        }
        if (SharedPreferenceUtil.getInstance().getMainAnim()) {
            showItemAnim(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        return mMovieData.title != null ? 1 : 0;
    }

    /**
     * 当前天气情况
     */
    class MovieInfoViewHolder extends BaseViewHolder<Movie> {

        @BindView(R.id.item_movie_poster)
        ImageView itemMoviePoster;
        @BindView(R.id.item_movie_title)
        TextView itemMovieTitle;

        MovieInfoViewHolder(View itemView) {
            super(itemView);
        }

        protected void bind(Movie movie) {
            try {
                itemMoviePoster = (ImageView) itemView.findViewById(R.id.item_movie_poster);
                itemMovieTitle = (TextView) itemView.findViewById(R.id.item_movie_title);
            } catch (Exception e) {
                PLog.e(TAG, e.toString());
            }
        }
    }

}
