package com.soa.FunNow.modules.main.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import com.soa.FunNow.R;

import java.util.HashMap;
import java.util.Map;

public class CardMovieHelper {

    public static final int TEST_CODE = 1;

    private static final String OTHER = "其他";

    private static Map<MovieInfo, Integer> sMap = new HashMap<>();

    static {
        sMap.put(new CardMovieHelper.MovieInfo(TEST_CODE, OTHER), R.mipmap.city_other_rainy);

    }

    void applyStatus(int code, String title, View view) {
        if (code == 1) { code = TEST_CODE; }
        if (!title.matches(String.format("(?:%s)", "bbb"))) {
            title = OTHER;
        }
        Integer mipRes = sMap.get(new CardMovieHelper.MovieInfo(code, title));
        if (mipRes != null) {
            view.setBackground(ContextCompat.getDrawable(view.getContext(), mipRes));
        }
    }

    private static class MovieInfo {
        int weatherCode;
        String city;

        public MovieInfo(int weatherCode, String city) {
            this.weatherCode = weatherCode;
            this.city = city;
        }

        private String code() {
            return String.valueOf(String.format("%s%s", weatherCode, city));
        }

        @Override
        public int hashCode() {
            return 31 * 17 + code().hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof CardMovieHelper.MovieInfo) {
                return this.code().equals(((CardMovieHelper.MovieInfo) o).code());
            }
            return super.equals(o);
        }
    }
}

