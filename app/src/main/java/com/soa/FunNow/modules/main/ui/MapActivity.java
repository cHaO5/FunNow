package com.soa.FunNow.modules.main.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import butterknife.BindView;
import com.soa.FunNow.R;
import com.soa.FunNow.base.ToolbarActivity;
import com.soa.FunNow.common.IntentKey;
import com.soa.FunNow.common.utils.SharedPreferenceUtil;
import com.soa.FunNow.component.PLog;
import com.soa.FunNow.modules.main.adapter.MapAdapter;
import com.soa.FunNow.modules.main.domain.Map;
import com.soa.FunNow.modules.main.domain.Movie;
import okhttp3.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends ToolbarActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private String mRequestUrl;
    private List<Map> mMap = new ArrayList<>();
    private MapAdapter mAdapter;
//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    sendRequestWithOkHttp();
//                    mAdapter.updateList(mMap);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @Override
    protected int layoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("ooooooooooooooooooooooooooo");
        initViewWithData();
    }

    private void initViewWithData() {
        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra(IntentKey.MAP);
        if (movie == null) {
            finish();
        }
        sendRequestWithOkHttp();

        safeSetTitle("附近的电影院");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println(mMap);
        mAdapter = new MapAdapter(mMap);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String loc = SharedPreferenceUtil.getInstance().getLA() + "," + SharedPreferenceUtil.getInstance().getLONG();
                System.out.println(loc);
                mRequestUrl = "http://api.map.baidu.com/place/v2/search?query=电影院$电影城$影院$影城&location="
                        + loc + "&radius=10000&page_size=20&output=xml&ak=sKAkzRSOLM0RT1tVgp4e2by8O4oCQub4";
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(mRequestUrl)
                            .build();
//                    Response response = client.newCall(request).execute();
//                    String responseData = response.body().string();
//                    System.out.println(responseData);
//                    parseXMLWithPull(responseData);
//                    mAdapter.updateList(mMap);
//                    mHandler.sendEmptyMessage(0);
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseData = response.body().string();
                            System.out.println(responseData);
                            parseXMLWithPull(responseData);
//                            mAdapter.updateList(mMap);

                            MapActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.updateList(mMap);
                                }
                            });
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String name = "";
            String address = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("address".equals(nodeName)) {
                            address = xmlPullParser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        if ("result".equals(nodeName)) {
                            Map temp = new Map();
                            temp.setName(name);
                            temp.setAddress(address);
                            mMap.add(temp);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(mMap);
//        mAdapter.updateList(mMap);
    }

    public static void launch(Context context, Movie movie) {
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(IntentKey.MAP, movie);
        context.startActivity(intent);
    }
}
