package com.soa.FunNow.modules.main.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import com.soa.FunNow.R;
import com.soa.FunNow.base.BaseActivity;
import com.soa.FunNow.base.ToolbarActivity;
import com.soa.FunNow.common.IntentKey;
import com.soa.FunNow.modules.main.adapter.MovieAdapter;
import com.soa.FunNow.modules.main.adapter.MoviePageAdapter;
import com.soa.FunNow.modules.main.adapter.WeatherAdapter;
import com.soa.FunNow.modules.main.domain.Movie;
import com.soa.FunNow.modules.main.domain.Weather;

public class DetailMovieActivity extends ToolbarActivity {

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
        Movie movie = (Movie) intent.getSerializableExtra(IntentKey.MOVIE);
        if (movie == null) {
            finish();
        }
        safeSetTitle(movie.getTitle());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MovieAdapter mAdapter = new MovieAdapter(movie);
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

    public static void launch(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(IntentKey.MOVIE, movie);
        context.startActivity(intent);
    }
}
