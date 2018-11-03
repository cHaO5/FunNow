package com.soa.FunNow.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.soa.FunNow.R;
import com.soa.FunNow.base.BaseViewHolder;
import com.soa.FunNow.common.utils.SharedPreferenceUtil;
import com.soa.FunNow.component.AnimRecyclerViewAdapter;
import com.soa.FunNow.component.PLog;
import com.soa.FunNow.modules.main.domain.Event;
import com.soa.FunNow.modules.main.domain.Movie;

public class EventAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static String TAG = EventAdapter.class.getSimpleName();

    private Context mContext;

    private static final int TYPE_ONE = 0;

    private Event mEventData;

    public EventAdapter(Event eventData) {
        this.mEventData = eventData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == EventAdapter.TYPE_ONE) {
            return EventAdapter.TYPE_ONE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case TYPE_ONE:
                return new EventAdapter.EventInfoViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_event_main_info, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_ONE:
                ((EventAdapter.EventInfoViewHolder) holder).bind(mEventData);
                break;
            default:
                break;
        }
        if (SharedPreferenceUtil.getInstance().getMainAnim()) {
            showItemAnim(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        return mEventData.getTitle() != null ? 1 : 0;
    }

    /**
     * 当前天气情况
     */
    class EventInfoViewHolder extends BaseViewHolder<Event> {

        @BindView(R.id.item_event_poster)
        ImageView itemEventPoster;
        @BindView(R.id.item_event_title)
        TextView itemEventTitle;

        EventInfoViewHolder(View itemView) {
            super(itemView);
        }

        protected void bind(Event event) {
            try {
                itemEventTitle.setText(event.getTitle());
                Glide.with(mContext)
                        .load(event.getImage())
                        .into(itemEventPoster);
            } catch (Exception e) {
                PLog.e(TAG, e.toString());
            }
        }
    }
}
