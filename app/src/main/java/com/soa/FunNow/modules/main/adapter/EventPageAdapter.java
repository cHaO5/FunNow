package com.soa.FunNow.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
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
import com.soa.FunNow.common.utils.Util;
import com.soa.FunNow.component.PLog;
import com.soa.FunNow.modules.main.domain.Event;

import java.util.List;

public class EventPageAdapter extends RecyclerView.Adapter<EventPageAdapter.EventPageViewHolder> {
    private Context mContext;
    private EventPageAdapter.onEventPageClick mEventPageClick;
    private List<Event> mEventList;

    public void setEventPageClick(EventPageAdapter.onEventPageClick eventPageClick) {
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
    public EventPageAdapter.EventPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new EventPageAdapter.EventPageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_card, parent, false));
    }

    @Override
    public void onBindViewHolder(EventPageAdapter.EventPageViewHolder holder, int position) {
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
        @BindView(R.id.event_poster_string)
        TextView mPosterString;

        public EventPageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(Event event) {
            try {
                mEventTitle.setText(Util.safeText(event.getTitle()));
//                System.out.println(event.poster);
                Glide.with(mContext)
                        .load(event.getImage())
                        .into(mEventPoster);

                mPosterString.setText(Util.safeText(event.getAddress()));
            } catch (NullPointerException e) {
                PLog.e(e.getMessage());
            }
//            int code = 1;
//            new CardEventHelper().applyStatus(code, event.getTitle(), mCardView);
        }
    }

    public interface onEventPageClick {
        //        void longClick();
        void click(Event event);
    }

}