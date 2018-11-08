package com.soa.FunNow.modules.city.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.soa.FunNow.common.utils.Util;
import com.soa.FunNow.modules.city.domain.City;
import com.soa.FunNow.modules.city.domain.Province;
import java.util.ArrayList;
import java.util.List;

public class WeatherDB {

    public WeatherDB() {

    }

    public static List<Province> loadProvinces(SQLiteDatabase db) {

        List<Province> list = new ArrayList<>();

        Cursor cursor = db.query("T_Province", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.mProSort = cursor.getInt(cursor.getColumnIndex("ProSort"));
                province.mProName = cursor.getString(cursor.getColumnIndex("ProName"));
                list.add(province);
            } while (cursor.moveToNext());
        }
        Util.closeQuietly(cursor);
        return list;
    }

    public static List<City> loadCities(SQLiteDatabase db, int ProID) {
        List<City> list = new ArrayList<>();
        Cursor cursor = db.query("T_City", null, "ProID = ?", new String[] { String.valueOf(ProID) }, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.mCityName = cursor.getString(cursor.getColumnIndex("CityName"));
                city.mProID = ProID;
                city.mCitySort = cursor.getInt(cursor.getColumnIndex("CitySort"));
                list.add(city);
            } while (cursor.moveToNext());
        }
        Util.closeQuietly(cursor);
        return list;
    }
}
