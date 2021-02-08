package com.m90.badshahandicappertips.home.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.m90.badshahandicappertips.R;
import com.m90.badshahandicappertips.model.HomeModel;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class CitynameHomeAdapter extends RecyclerView.Adapter<CitynameHomeAdapter.MyViewHolder> {

    private static final String TAG = "CitynameHomeAdapter";

    int count = 0;
    int total;


    private Activity mContext;
    ArrayList<HomeModel> list;
    ArrayList<Integer> listInteger;
    String amt;
    private  ItemClickListener itemClickListener;

    public CitynameHomeAdapter(Activity context, ArrayList<HomeModel> list, String amt, ItemClickListener itemClickListener) {
        this.list = list;
        mContext = context;
        amt = amt;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.arraylist_item, null);


        Log.e(TAG, "onCreateViewHolder: "+amt );

        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final HomeModel item = list.get(i);

        Log.e("vlist", list.toString());

        /// for total
      viewHolder.tv_randomColour.setText(item.getName());
       viewHolder.tv_randomColour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              viewHolder.tv_randomColour.getText().toString();         //////by sneha
                Toast.makeText(mContext, "Position " + i, Toast.LENGTH_SHORT).show();


            }
        });


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_randomColour;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_randomColour = itemView.findViewById(R.id.tv_randomColour);
            img = itemView.findViewById(R.id.img_rvItem);
            itemView.setOnClickListener(this); // bind the listener

        }
        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());

                     tv_randomColour.setText("2");

        }
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<HomeModel> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}

