package com.soa.FunNow.common.utils;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;
import com.soa.FunNow.common.Irrelevant;
import io.reactivex.Observable;

public class RxDrawer {

    private static final float OFFSET_THRESHOLD = 0.03f;

    public static Observable<Irrelevant> close(final DrawerLayout drawer) {
        return Observable.create(emitter -> {
            drawer.closeDrawer(GravityCompat.START);
            DrawerListener listener = new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    if (slideOffset < OFFSET_THRESHOLD) {
                        emitter.onNext(Irrelevant.INSTANCE);
                        emitter.onComplete();
                        drawer.removeDrawerListener(this);
                    }
                }
            };
            drawer.addDrawerListener(listener);
        });
    }
}
