package com.soa.FunNow.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.soa.FunNow.R;
import com.soa.FunNow.base.BaseViewHolder;
import com.soa.FunNow.common.utils.Util;
import com.soa.FunNow.component.PLog;
import com.soa.FunNow.modules.main.domain.Event;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventPageAdapter extends RecyclerView.Adapter<EventPageAdapter.EventPageViewHolder> {
    private Context mContext;
    private onEventPageClick mEventPageClick;
    private List<Event> mEventList;

    public void setEventPageClick(onEventPageClick eventPageClick) {
        this.mEventPageClick = eventPageClick;
    }

    public EventPageAdapter(List<Event> eventList) {
        this.mEventList = eventList;
//        this.mEventList = testlistmovie;
    }

    public void updateList(List<Event> data) {
        this.mEventList = data;
        notifyDataSetChanged();
    }


    @Override
    public EventPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new EventPageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_card, parent, false));
    }

    @Override
    public void onBindViewHolder(EventPageViewHolder holder, int position) {
//        System.out.println(position);
        holder.bind(mEventList.get(position));
        holder.itemView.setOnClickListener(v -> mEventPageClick.click(mEventList.get(holder.getAdapterPosition())));
    }


    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public boolean isEmpty() {
        return 0 == getItemCount();
    }

    class EventPageViewHolder extends BaseViewHolder<Event> {

        @BindView(R.id.cardView_event)
        CardView mCardView;
        @BindView(R.id.event_poster)
        ImageView mEventPoster;
        @BindView(R.id.event_title)
        TextView mEventTitle;
        @BindView(R.id.event_tag)
        TextView mEventTag;

        public EventPageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(Event event) {
            try {
                mEventTitle.setText(Util.safeText(event.getTitle()));
                Glide.with(mContext)
                        .load(event.getImage_hlarge())
                        .into(mEventPoster);
                String tag = "";
                if (!event.getTags().split(",")[0].equals("")) {
                    tag = " " + event.getTags().split(",")[0] + " ";
                }
                mEventTag.setText(Util.safeText(tag));
            } catch (NullPointerException e) {
                PLog.e(e.getMessage());
            }

        }
    }

    public interface onEventPageClick {
        void click(Event event);
    }

}