package com.soa.FunNow.modules.main.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import com.soa.FunNow.R;
import java.util.HashMap;
import java.util.Map;

public class CardCityHelper {

    public static final int SUNNY_CODE = 100;
    public static final int RAINY_CODE = 300;
    public static final int CLOUDY_CODE = 500;

    private static final String SHANG_HAI = "上海";
    private static final String BEI_JING = "北京";
    private static final String SU_ZHOU = "苏州";
    private static final String OTHER = "其他";

    private static Map<WeatherInfo, Integer> sMap = new HashMap<>();

    void applyStatus(int code, String city, View view) {
        if (code >= 300 && code < 408) {
            code = RAINY_CODE;
        } else if (code > 100 && code < 300) {
            code = CLOUDY_CODE;
        } else {
            code = SUNNY_CODE;
        }
        if (!city.matches(String.format("(?:%s|%s|%s)", SU_ZHOU, SHANG_HAI, BEI_JING))) {
            city = OTHER;
        }
        Integer mipRes = sMap.get(new WeatherInfo(code, city));
        if (mipRes != null) {
            view.setBackground(ContextCompat.getDrawable(view.getContext(), mipRes));
        }
    }

    private static class WeatherInfo {
        int weatherCode;
        String city;

        public WeatherInfo(int weatherCode, String city) {
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
            if (o instanceof WeatherInfo) {
                return this.code().equals(((WeatherInfo) o).code());
            }
            return super.equals(o);
        }
    }
}
