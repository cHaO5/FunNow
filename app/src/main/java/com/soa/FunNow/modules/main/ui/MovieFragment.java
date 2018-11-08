package com.soa.FunNow.modules.main.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.soa.FunNow.R;
import com.soa.FunNow.base.BaseFragment;
import com.soa.FunNow.modules.main.adapter.MoviePageAdapter;
import com.soa.FunNow.modules.main.domain.Movie;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.soa.FunNow.common.C.movieSubTypeList;

public class MovieFragment extends BaseFragment {

    private static final String JSON_TOTAL = "total";
    private static final String JSON_SUBJECTS = "subjects";

    private static RequestQueue requestQueue;

    @BindView(R.id.recyclerView_movie)
    RecyclerView mRecyclerView;

    private MoviePageAdapter mAdapter;
    private List<Movie> mMovie = new ArrayList<>();
    private String mRequestUrl;
    private int mTotalItem;
    private String mDataString;

    private View view;


    @Override
    protected void lazyLoad() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_movie, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
//        multiLoad();
    }

    private void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        mRequestUrl = "http://api.douban.com/v2/movie/in_theaters";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mRequestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mTotalItem = response.getInt(JSON_TOTAL);
                            mDataString = response.getString(JSON_SUBJECTS);
                            mMovie = new Gson().fromJson(mDataString, movieSubTypeList);
                            System.out.println("qqqqqqqqqqqqqqqqqq " + mMovie.size());
                            mAdapter.updateList(mMovie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
//                            mRefresh.setRefreshing(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
//                        mRefresh.setRefreshing(false);
                    }
                });
        requestQueue.add(request);
    }

    private void initView() {
        mAdapter = new MoviePageAdapter(mMovie);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setMoviePageClick(new MoviePageAdapter.onMoviePageClick() {
            @Override
            public void click(Movie movie) {
                DetailMovieActivity.launch(getActivity(), movie);
            }
        });

    }
}
