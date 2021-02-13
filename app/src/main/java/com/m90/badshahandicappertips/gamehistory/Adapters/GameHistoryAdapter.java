package com.m90.badshahandicappertips.gamehistory.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.m90.badshahandicappertips.R;
import com.m90.badshahandicappertips.card.model.BettingResponce;

import java.util.ArrayList;

public class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryAdapter.MyViewHolder> {

    private static final String TAG = "CartAdapter";


    private Activity mContext;
    ArrayList<BettingResponce> list;
    ArrayList<Integer> listInteger;


   String amt;
    private  ItemClickListener itemClickListener;

    public GameHistoryAdapter(Activity context, ArrayList<BettingResponce> list) {
        mContext = context;
        this.list = list;
    }


    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gamehistory, null);

        Log.e(TAG, "onCreateViewHolder: "+amt );
        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final BettingResponce item = list.get(i);

        viewHolder.tv_amount.setText(String.valueOf(item.amount));
        viewHolder.tv_date.setText(String.valueOf(item.created));
        viewHolder.tv_time.setText(String.valueOf(item.id));
        Log.e("vlist", list.toString());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tv_date,tv_time,tv_amount;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_amount = itemView.findViewById(R.id.tv_amount);

        }
        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<BettingResponce> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}

