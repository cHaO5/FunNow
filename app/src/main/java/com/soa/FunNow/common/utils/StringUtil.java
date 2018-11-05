package com.soa.FunNow.common.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.List;

public class StringUtil {

    public static String getListString(List<String> list, char s) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            str.append(i == 0 ? "" : s).append(list.get(i));
        }
        return str.toString();
    }
}
