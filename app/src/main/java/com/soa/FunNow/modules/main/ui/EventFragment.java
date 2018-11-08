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
import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;
import com.google.gson.Gson;
import com.soa.FunNow.R;
import com.soa.FunNow.base.BaseFragment;
import com.soa.FunNow.common.utils.SharedPreferenceUtil;
import com.soa.FunNow.modules.main.adapter.EventAdapter;
import com.soa.FunNow.modules.main.adapter.EventPageAdapter;
import com.soa.FunNow.modules.main.domain.Event;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.soa.FunNow.common.C.eventSubTypeList;

public class EventFragment extends BaseFragment {
    private static final String JSON_TOTAL = "total";
    private static final String JSON_SUBJECTS = "events";

    @BindView(R.id.recyclerView_event)
    RecyclerView mRecyclerView;

    private EventPageAdapter mAdapter;
    private List<Event> mEvent = new ArrayList<>();
    private String mRequestUrl;
    private int mTotalItem;
    private String mDataString;
    private boolean isGetData = false;


    private View view;


    @Override
    protected void lazyLoad() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_event, container, false);
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

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }

    @Override
    public void onResume() {
        if (!isGetData) {
            initData();
            initView();
        }
        super.onResume();
    }


    private void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(getContext())));
        String city = Pinyin.toPinyin(SharedPreferenceUtil.getInstance().getCityName(), "").toLowerCase();
        mRequestUrl = "https://api.douban.com/v2/event/list?loc=" + city + "&count=60";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mRequestUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mTotalItem = response.getInt(JSON_TOTAL);
                            mDataString = response.getString(JSON_SUBJECTS);
                            mEvent = new Gson().fromJson(mDataString, eventSubTypeList);
                            mAdapter.updateList(mEvent);
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
        mAdapter = new EventPageAdapter(mEvent);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEventPageClick(new EventPageAdapter.onEventPageClick() {
            @Override
            public void click(Event event) {
                DetailEventActivity.launch(getActivity(), event);
            }
        });

    }

}
