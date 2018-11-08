package com.soa.FunNow.base;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import com.facebook.stetho.Stetho;
import com.github.moduth.blockcanary.BlockCanary;
import com.hugo.watcher.Watcher;
import com.squareup.leakcanary.LeakCanary;
import com.soa.FunNow.BuildConfig;
import com.soa.FunNow.component.CrashHandler;
import com.soa.FunNow.component.PLog;
import im.fir.sdk.FIR;
import io.reactivex.plugins.RxJavaPlugins;

public class BaseApplication extends Application {

    private static String sCacheDir;
    private static Context sAppContext;

    static {
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
        CrashHandler.init(new CrashHandler(getApplicationContext()));
        if (!BuildConfig.DEBUG) {
            FIR.init(this);
        } else {
            Watcher.getInstance().start(this);
            Stetho.initializeWithDefaults(this);
        }
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        LeakCanary.install(this);
        RxJavaPlugins.setErrorHandler(throwable -> {
            if (throwable != null) {
                PLog.e(throwable.toString());
            } else {
                PLog.e("call onError but exception is null");
            }
        });
        /*
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */
        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            sCacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            sCacheDir = getApplicationContext().getCacheDir().toString();
        }
    }

    private boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    public static String getAppCacheDir() {
        return sCacheDir;
    }
}
