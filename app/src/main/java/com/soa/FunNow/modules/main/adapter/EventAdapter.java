package com.soa.FunNow.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
    private static final int TYPE_TWO = 1;

    private Event mEventData;

    public EventAdapter(Event eventData) {
        this.mEventData = eventData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == EventAdapter.TYPE_ONE) {
            return EventAdapter.TYPE_ONE;
        }
        if (position == EventAdapter.TYPE_TWO) {
            return EventAdapter.TYPE_TWO;
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
            case TYPE_TWO:
                return new EventAdapter.EventConViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_event_content, parent, false));
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
            case TYPE_TWO:
                ((EventAdapter.EventConViewHolder) holder).bind(mEventData);
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
        return mEventData.getTitle() != null ? 2 : 0;
    }


    class EventInfoViewHolder extends BaseViewHolder<Event> {

        @BindView(R.id.item_event_poster)
        ImageView itemEventPoster;
        @BindView(R.id.item_event_title)
        TextView itemEventTitle;
        @BindView(R.id.item_event_tag)
        TextView itemEventTag;
        @BindView(R.id.item_event_begin)
        TextView itemEventBegin;
        @BindView(R.id.item_event_end)
        TextView itemEventEnd;
        @BindView(R.id.item_event_fee)
        TextView itemEventFee;


        EventInfoViewHolder(View itemView) {
            super(itemView);
        }

        protected void bind(Event event) {
            try {
                itemEventTitle.setText(event.getTitle());
                String begin = "开始时间: " + event.getBegin_time();
                itemEventBegin.setText(begin);
                itemEventTag.setText(event.getTags());
                String end = "结束时间: " + event.getEnd_time();
                itemEventEnd.setText(end);
                String fee = "门票: " + event.getFee_str();
                itemEventFee.setText(fee);
                Glide.with(mContext)
                        .load(event.getImage_hlarge())
                        .into(itemEventPoster);
            } catch (Exception e) {
                PLog.e(TAG, e.toString());
            }
        }
    }

    class EventConViewHolder extends BaseViewHolder<Event> {

        @BindView(R.id.item_event_content)
        TextView itemEventContent;

        EventConViewHolder(View itemView) {
            super(itemView);
        }

        protected void bind(Event event) {
            try {
                String raw = event.getContent();
                String con = raw.replaceAll("<(?!br)[^>]*>", "");
//                String con = raw.replaceAll("<div class=\"middle\"(.*)</div>", "");
                String con1 = con.replaceAll("<br>[<br>]+", "<br><br>");
                itemEventContent.setText(Html.fromHtml(con1));
//                itemEventContent.setText(con);

            } catch (Exception e) {
                PLog.e(TAG, e.toString());
            }
        }
    }
}
