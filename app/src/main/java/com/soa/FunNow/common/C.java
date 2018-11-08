package com.soa.FunNow.common;

import com.google.gson.reflect.TypeToken;
import com.soa.FunNow.BuildConfig;
import com.soa.FunNow.base.BaseApplication;
import com.soa.FunNow.modules.main.domain.Event;
import com.soa.FunNow.modules.main.domain.Movie;
import com.soa.FunNow.modules.main.domain.News;
//import com.soa.FunNow.modules.main.domain.Zhihu;
//import com.soa.FunNow.modules.main.domain.Zhihu;
//import com.soa.FunNow.modules.main.domain.ZhihuCon;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

public class C {

    public static final String API_TOKEN = BuildConfig.FirToken;
    public static final String KEY = BuildConfig.WeatherKey;// 和风天气 key

    public static final String MULTI_CHECK = "multi_check";

    public static final String ORM_NAME = "cities.db";

    public static final String UNKNOWN_CITY = "unknown city";

    public static final String NET_CACHE = BaseApplication.getAppCacheDir() + File.separator + "NetCache";

    public static final Type movieSubTypeList = new TypeToken<List<Movie>>() {
    }.getType();

    public static final Type eventSubTypeList = new TypeToken<List<Event>>() {
    }.getType();

//    public static final Type zhihuSubTypeList = new TypeToken<List<Zhihu>>() {
//    }.getType();
//
//    public static final Type zhihuSubCon = new TypeToken<ZhihuCon>() {
//    }.getType();
    public static final Type newsSubTypeList = new TypeToken<List<News>>() {
    }.getType();
}
