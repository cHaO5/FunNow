package com.soa.FunNow.modules.city.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.soa.FunNow.R;
import com.soa.FunNow.common.C;
import com.soa.FunNow.base.ToolbarActivity;
import com.soa.FunNow.common.Irrelevant;
import com.soa.FunNow.common.utils.RxUtil;
import com.soa.FunNow.common.utils.SharedPreferenceUtil;
import com.soa.FunNow.common.utils.ToastUtil;
import com.soa.FunNow.common.utils.Util;
import com.soa.FunNow.component.OrmLite;
import com.soa.FunNow.component.RxBus;
import com.soa.FunNow.modules.city.adapter.CityAdapter;
import com.soa.FunNow.modules.city.db.DBManager;
import com.soa.FunNow.modules.city.db.WeatherDB;
import com.soa.FunNow.modules.city.domain.City;
import com.soa.FunNow.modules.city.domain.Province;
import com.soa.FunNow.modules.main.domain.ChangeCityEvent;
import com.soa.FunNow.modules.main.domain.CityORM;
import com.soa.FunNow.modules.main.domain.MultiUpdateEvent;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;

public class ChoiceCityActivity extends ToolbarActivity {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private ArrayList<String> dataList = new ArrayList<>();
    private Province selectedProvince;
    private List<Province> provincesList = new ArrayList<>();
    private List<City> cityList;
    private CityAdapter mAdapter;

    public static final int LEVEL_PROVINCE = 1;
    public static final int LEVEL_CITY = 2;
    private int currentLevel;

    private boolean isChecked = false;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        Observable.create(emitter -> {
            DBManager.getInstance().openDatabase();
            emitter.onNext(Irrelevant.INSTANCE);
            emitter.onComplete();
        })
            .compose(RxUtil.io())
            .compose(RxUtil.activityLifecycle(this))
            .doOnNext(o -> {
                initRecyclerView();
                queryProvinces();
            })
            .subscribe();

        Intent intent = getIntent();
        isChecked = intent.getBooleanExtra(C.MULTI_CHECK, false);
        if (isChecked && SharedPreferenceUtil.getInstance().getBoolean("Tips", true)) {
            showTips();
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_choice_city;
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CityAdapter(this, dataList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, pos) -> {
            if (currentLevel == LEVEL_PROVINCE) {
                selectedProvince = provincesList.get(pos);
                mRecyclerView.smoothScrollToPosition(0);
                queryCities();
            } else if (currentLevel == LEVEL_CITY) {
                String city = Util.replaceCity(cityList.get(pos).mCityName);
                if (isChecked) {
                    OrmLite.getInstance().save(new CityORM(city));
                    RxBus.getDefault().post(new MultiUpdateEvent());
                } else {
                    SharedPreferenceUtil.getInstance().setCityName(city);
                    SharedPreferenceUtil.getInstance().setAutoLoc(false);
                    RxBus.getDefault().post(new ChangeCityEvent());
                }
                quit();
            }
        });
    }

    /**
     * 查询全国所有的省，从数据库查询
     */
    private void queryProvinces() {
        getToolbar().setTitle("选择省份");
        Flowable.create((FlowableOnSubscribe<String>) emitter -> {
            if (provincesList.isEmpty()) {
                provincesList.addAll(WeatherDB.loadProvinces(DBManager.getInstance().getDatabase()));
            }
            dataList.clear();
            for (Province province : provincesList) {
                emitter.onNext(province.mProName);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER)
            .compose(RxUtil.ioF())
            .compose(RxUtil.activityLifecycleF(this))
            .doOnNext(proName -> dataList.add(proName))
            .doOnComplete(() -> {
                mProgressBar.setVisibility(View.GONE);
                currentLevel = LEVEL_PROVINCE;
                mAdapter.notifyDataSetChanged();
            })
            .subscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.multi_city_menu, menu);
//        menu.getItem(0).setChecked(isChecked);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.multi_check) {
//            if (isChecked) {
//                item.setChecked(false);
//            } else {
//                item.setChecked(true);
//            }
//            isChecked = item.isChecked();
//        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 查询选中省份的所有城市，从数据库查询
     */
    private void queryCities() {
        getToolbar().setTitle("选择城市");
        dataList.clear();
        mAdapter.notifyDataSetChanged();

        Flowable.create((FlowableOnSubscribe<String>) emitter -> {
            cityList = WeatherDB.loadCities(DBManager.getInstance().getDatabase(), selectedProvince.mProSort);
            for (City city : cityList) {
                emitter.onNext(city.mCityName);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER)
            .compose(RxUtil.ioF())
            .compose(RxUtil.activityLifecycleF(this))
            .doOnNext(proName -> dataList.add(proName))
            .doOnComplete(() -> {
                currentLevel = LEVEL_CITY;
                mAdapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(0);
            })
            .subscribe();
    }

    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_PROVINCE) {
            quit();
        } else {
            queryProvinces();
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ChoiceCityActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBManager.getInstance().closeDatabase();
    }

    private void showTips() {
    }

    private void quit() {
        ChoiceCityActivity.this.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
