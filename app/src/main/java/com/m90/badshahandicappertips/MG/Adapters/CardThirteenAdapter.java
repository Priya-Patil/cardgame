package com.m90.badshahandicappertips.MG.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.m90.badshahandicappertips.MG.Models.CardThirteenModel;
import com.m90.badshahandicappertips.R;

import java.util.ArrayList;

public class CardThirteenAdapter extends RecyclerView.Adapter<CardThirteenAdapter.MyViewHolder> {

    private static final String TAG = "CartAdapter";

    private Activity mContext;
    ArrayList<CardThirteenModel> list;
    private  ItemClickListener itemClickListener;

    public CardThirteenAdapter(Activity context, ArrayList<CardThirteenModel> list) {
        this.list = list;
        mContext = context;
    }

    public CardThirteenAdapter(Activity context, ArrayList<CardThirteenModel> list, ItemClickListener itemClickListener){
        mContext = context;
        this.list = list;
        this.itemClickListener=itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_array_thirteen, null);
        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final CardThirteenModel item = list.get(i);

        Log.e("vlist", list.toString());
       // viewHolder.tv_title.setText(item.getName());
     //   Toast.makeText(getContext(), "id " + i, Toast.LENGTH_SHORT).show();
        // viewHolder.img.setBackground(mContext.getResources().getDrawable(item.getImage_drawable()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_cart;
        RelativeLayout rl_delete;
        int count = 0;
      //  TextView tv_title, tv_freepaid;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
           // tv_title = itemView.findViewById(R.id.tv_title);
            img = itemView.findViewById(R.id.img_rvItem);
            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<CardThirteenModel> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}

