package com.soa.FunNow.modules.main.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import butterknife.BindView;
import com.soa.FunNow.R;
import com.soa.FunNow.base.ToolbarActivity;
import com.soa.FunNow.common.IntentKey;
import com.soa.FunNow.modules.main.adapter.EventAdapter;
import com.soa.FunNow.modules.main.domain.Event;

public class DetailEventActivity extends ToolbarActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

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
        initViewWithData();
    }

    private void initViewWithData() {
        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra(IntentKey.MOVIE);
        if (event == null) {
            finish();
        }
        safeSetTitle(event.getTitle());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        EventAdapter mAdapter = new EventAdapter(event);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public static void launch(Context context, Event event) {
        Intent intent = new Intent(context, DetailEventActivity.class);
        intent.putExtra(IntentKey.MOVIE, event);
        context.startActivity(intent);
    }
}