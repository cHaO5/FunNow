package com.soa.FunNow.modules.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.soa.FunNow.common.utils.SharedPreferenceUtil;
import com.soa.FunNow.component.NotificationHelper;
import com.soa.FunNow.component.RetrofitSingleton;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;

public class AutoUpdateService extends Service {

    private final String TAG = AutoUpdateService.class.getSimpleName();
    private Disposable mDisposable;
    private boolean mIsUnSubscribed = true;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        synchronized (this) {
            unSubscribed();
            if (mIsUnSubscribed) {
                unSubscribed();
                if (SharedPreferenceUtil.getInstance().getAutoUpdate() != 0) {
                    mDisposable = Observable.interval(SharedPreferenceUtil.getInstance().getAutoUpdate(), TimeUnit.HOURS)
                        .doOnNext(aLong -> {
                            mIsUnSubscribed = false;
                            fetchDataByNetWork();
                        })
                        .subscribe();
                }
            }
        }
        return START_REDELIVER_INTENT;
    }

    private void unSubscribed() {
        mIsUnSubscribed = true;
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    private void fetchDataByNetWork() {
        String cityName = SharedPreferenceUtil.getInstance().getCityName();
        RetrofitSingleton.getInstance()
            .fetchWeather(cityName)
            .subscribe(weather -> NotificationHelper.showWeatherNotification(AutoUpdateService.this, weather));
    }
}
