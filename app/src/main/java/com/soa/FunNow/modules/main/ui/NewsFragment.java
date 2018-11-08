package com.soa.FunNow.modules.main.ui;

import android.content.Intent;
import android.net.Uri;
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
import com.soa.FunNow.modules.main.adapter.NewsPageAdapter;
import com.soa.FunNow.modules.main.domain.News;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.soa.FunNow.common.C.newsSubTypeList;

public class NewsFragment extends BaseFragment {

    private static final String JSON_SUBJECTS = "T1467284926140";

    private static RequestQueue requestQueue;

    @BindView(R.id.recyclerView_news)
    RecyclerView mRecyclerView;

    private NewsPageAdapter mAdapter;
    private List<News> mNews = new ArrayList<>();
    private String mRequestUrl;
    private String mDataString;

    private View view;


    @Override
    protected void lazyLoad() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_news, container, false);
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
    }

    private void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        mRequestUrl = "http://c.3g.163.com/nc/article/list/T1467284926140/0-20.html";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mRequestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mDataString = response.getString(JSON_SUBJECTS);
                            mNews = new Gson().fromJson(mDataString, newsSubTypeList);
                            mAdapter.updateList(mNews);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
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
        mAdapter = new NewsPageAdapter(mNews);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        System.out.println("111111111111111111111111");
        mAdapter.setNewsPageClick(new NewsPageAdapter.onNewsPageClick() {
            @Override
            public void click(News news) {
                DetailNewsActivity.launch(getActivity(), news);
            }
        });
    }
}

