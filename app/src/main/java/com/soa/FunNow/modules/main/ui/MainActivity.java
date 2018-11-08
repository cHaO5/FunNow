package com.soa.FunNow.modules.main.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import butterknife.BindView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.bumptech.glide.Glide;
import com.soa.FunNow.R;
import com.soa.FunNow.base.BaseActivity;
import com.soa.FunNow.common.C;
import com.soa.FunNow.common.utils.*;
import com.soa.FunNow.modules.city.ui.ChoiceCityActivity;
import com.soa.FunNow.modules.main.adapter.HomePagerAdapter;
import com.soa.FunNow.modules.service.AutoUpdateService;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;


    private WeatherFragment mWeatherFragment;
    private MovieFragment mMovieFragment;
    private EventFragment mEventFragment;
    private NewsFragment mNewsFragment;

    public LocationClient mLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        requestLocation();
        initView();
        initDrawer();
        initIcon();
        startService(new Intent(this, AutoUpdateService.class));
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            SharedPreferenceUtil.getInstance().setLA((float) bdLocation.getLatitude());
            SharedPreferenceUtil.getInstance().setLONG((float) bdLocation.getLongitude());
            if (SharedPreferenceUtil.getInstance().getAutoLoc()) {
                SharedPreferenceUtil.getInstance().setCityName(Util.replaceCity(bdLocation.getCity()));
            }
        }
    }


    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initIcon();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
//        mapView.onDestroy();
//        baiduMap.setMyLocationEnabled(false);
    }

    /**
     * 初始化基础View
     */
    private void initView() {
        setSupportActionBar(mToolbar);
        HomePagerAdapter mAdapter = new HomePagerAdapter(getSupportFragmentManager());
        mWeatherFragment = new WeatherFragment();
        mMovieFragment = new MovieFragment();
        mEventFragment = new EventFragment();
        mNewsFragment = new NewsFragment();


        mAdapter.addTab(mMovieFragment, "正在热映");
        mAdapter.addTab(mEventFragment, "同城");
        mAdapter.addTab(mNewsFragment, "热闻");
        mAdapter.addTab(mWeatherFragment, "天气");
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager, false);
    }


    /**
     * 初始化抽屉
     */
    private void initDrawer() {
        if (mNavView != null) {
            mNavView.setNavigationItemSelectedListener(this);
            mNavView.inflateHeaderView(R.layout.nav_header_main);
            ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            mDrawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }
    }

    /**
     * 初始化 Icons
     */
    private void initIcon() {
        if (SharedPreferenceUtil.getInstance().getIconType() == 0) {
            SharedPreferenceUtil.getInstance().putInt("未知", R.mipmap.none);
            SharedPreferenceUtil.getInstance().putInt("晴", R.mipmap.type_sunny);
            SharedPreferenceUtil.getInstance().putInt("阴", R.mipmap.type_cloudy);
            SharedPreferenceUtil.getInstance().putInt("多云", R.mipmap.type_cloudy);
            SharedPreferenceUtil.getInstance().putInt("少云", R.mipmap.type_cloudy);
            SharedPreferenceUtil.getInstance().putInt("晴间多云", R.mipmap.type_cloudytosunny);
            SharedPreferenceUtil.getInstance().putInt("小雨", R.mipmap.type_light_rain);
            SharedPreferenceUtil.getInstance().putInt("中雨", R.mipmap.type_light_rain);
            SharedPreferenceUtil.getInstance().putInt("大雨", R.mipmap.type_heavy_rain);
            SharedPreferenceUtil.getInstance().putInt("阵雨", R.mipmap.type_thunderstorm);
            SharedPreferenceUtil.getInstance().putInt("雷阵雨", R.mipmap.type_thunder_rain);
            SharedPreferenceUtil.getInstance().putInt("霾", R.mipmap.type_fog);
            SharedPreferenceUtil.getInstance().putInt("雾", R.mipmap.type_fog);
        } else {
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        RxDrawer.close(mDrawerLayout)
            .doOnNext(o -> { ChoiceCityActivity.launch(MainActivity.this);
            })
            .subscribe();
        return false;
    }


    private void showShareDialog() {
        // wait to do
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (!DoubleClickExit.check()) {
                ToastUtil.showShort(getString(R.string.double_exit));
            } else {
                finish();
            }
        }
    }
}
