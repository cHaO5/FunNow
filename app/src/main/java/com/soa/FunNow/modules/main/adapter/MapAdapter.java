package com.soa.FunNow.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.soa.FunNow.R;
import com.soa.FunNow.base.BaseViewHolder;
import com.soa.FunNow.common.utils.SharedPreferenceUtil;
import com.soa.FunNow.component.AnimRecyclerViewAdapter;
import com.soa.FunNow.component.PLog;
import com.soa.FunNow.modules.main.domain.Event;
import com.soa.FunNow.modules.main.domain.Map;
import com.soa.FunNow.modules.main.domain.Movie;

import java.util.List;

public class MapAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static String TAG = MovieAdapter.class.getSimpleName();

    private Context mContext;

    private static final int TYPE_ONE = 0;

//    private Movie mMovieData;
    private List<Map> mMap;

    public MapAdapter(List<Map> mMap) {
        this.mMap = mMap;
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == MapAdapter.TYPE_ONE) {
//            return MapAdapter.TYPE_ONE;
//        }
//        return super.getItemViewType(position);
//    }

    public void updateList(List<Map> data) {
        this.mMap = data;
        System.out.println(mMap);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
//        switch (viewType) {
//            case TYPE_ONE:
//                return new MapAdapter.MapViewHolder(
//                        LayoutInflater.from(mContext).inflate(R.layout.item_map, parent, false));
//        }
        return new MapAdapter.MapViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_map_card, parent, false));
//        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        int itemType = getItemViewType(position);
//        switch (itemType) {
//            case TYPE_ONE:
//                ((MapAdapter.MapViewHolder) holder).bind(mMap.get(position));
//                break;
//            default:
//                break;
//        }
        ((MapAdapter.MapViewHolder) holder).bind(mMap.get(position));
//        if (SharedPreferenceUtil.getInstance().getMainAnim()) {
//            showItemAnim(holder.itemView, position);
//        }
    }

    @Override
    public int getItemCount() {
        if (mMap != null) {
            return mMap.size();
        } else  {
            return 0;
        }
    }

    public boolean isEmpty() {
        return 0 == getItemCount();
    }

    class MapViewHolder extends BaseViewHolder<Map> {
        @BindView(R.id.map_name)
        TextView mapName;
        @BindView(R.id.map_address)
        TextView mapAddress;


        MapViewHolder(View itemView) {
            super(itemView);
        }

        protected void bind(Map Map) {
            try {
                mapName.setText(Map.getName());
                mapAddress.setText(Map.getAddress());
            } catch (Exception e) {
                PLog.e(TAG, e.toString());
            }
        }
    }

}
