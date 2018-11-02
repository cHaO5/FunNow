package com.soa.FunNow.modules.main.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.soa.FunNow.R;
import com.soa.FunNow.base.BaseFragment;
import com.soa.FunNow.common.C;
import com.soa.FunNow.common.utils.RxUtil;
import com.soa.FunNow.common.utils.Util;
import com.soa.FunNow.component.OrmLite;
import com.soa.FunNow.component.RetrofitSingleton;
import com.soa.FunNow.component.RxBus;
import com.soa.FunNow.modules.main.adapter.MoviePageAdapter;
import com.soa.FunNow.modules.main.adapter.MultiCityAdapter;
import com.soa.FunNow.modules.main.domain.CityORM;
import com.soa.FunNow.modules.main.domain.Movie;
import com.soa.FunNow.modules.main.domain.MultiUpdateEvent;
import com.soa.FunNow.modules.main.domain.Weather;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout mRefreshLayout;
//    @BindView(R.id.change_waterFall)
//    LinearLayout change_waterFall;

    private MoviePageAdapter mAdapter;
    private List<Movie> mMovie;

    private View view;


    @Override
    protected void lazyLoad() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_multicity, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().hide();
//        }
//        setContentView(R.layout.activity_main);
//        buttonInit();
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_main);
//        MoviePageAdapter adapter = new MoviePageAdapter(this);
//        recyclerView.setAdapter(adapter);
//    }

    /**
     * 按钮点击事件监听及方法
     */
//    private void buttonInit() {
//        change_listView = (Button) findViewById(R.id.change_listView);
//        change_gridView = (Button) findViewById(R.id.change_gridView);
//        change_waterFall = (Button) findViewById(R.id.change_waterFall);
//
//        change_listView.setOnClickListener(this);
//        change_gridView.setOnClickListener(this);
//        change_waterFall.setOnClickListener(this);
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        RxBus.getDefault()
//                .toObservable(MultiUpdateEvent.class)
//                .doOnNext(event -> multiLoad())
//                .subscribe();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
//        multiLoad();
    }

    private void initView() {
        mMovie = new ArrayList<>();
        mAdapter = new MoviePageAdapter(mMovie);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setMoviePageClick(new MoviePageAdapter.onMoviePageClick() {
//            @Override
//            public void longClick(String city) {
//                new AlertDialog.Builder(getActivity())
//                        .setMessage("是否删除该城市?")
//                        .setPositiveButton("删除", (dialog, which) -> {
//                            OrmLite.getInstance().delete(new WhereBuilder(CityORM.class).where("name=?", city));
//                            multiLoad();
//                            Snackbar.make(getView(), String.format(Locale.CHINA, "已经将%s删掉了 Ծ‸ Ծ", city), Snackbar.LENGTH_LONG)
//                                    .setAction("撤销",
//                                            v -> {
//                                                OrmLite.getInstance().save(new CityORM(city));
//                                                multiLoad();
//                                            }).show();
//                        })
//                        .show();
//            }

            @Override
            public void click(Movie movie) {
                DetailMovieActivity.launch(getActivity(), movie);
            }
        });

        if (mRefreshLayout != null) {
            mRefreshLayout.setColorSchemeResources(
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light,
                    android.R.color.holo_green_light,
                    android.R.color.holo_blue_bright
            );
//            mRefreshLayout.setOnRefreshListener(() -> mRefreshLayout.postDelayed(this::multiLoad, 1000));
        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.change_listView:
//                recyclerView.setLayoutManager(new LinearLayoutManager(this));
//                break;
//            case R.id.change_gridView:
//                recyclerView.setLayoutManager(new GridLayoutManager(MovieFragment.this,2));
//                break;
//            case R.id.change_waterFall:
//                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//                break;
//            default:
//                break;
//        }
//    }
}
