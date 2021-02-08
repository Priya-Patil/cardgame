package com.m90.badshahandicappertips.MG.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.m90.badshahandicappertips.MG.Models.FourCardModel;
import com.m90.badshahandicappertips.R;

import java.util.ArrayList;

public class FourCardAdapter extends RecyclerView.Adapter<FourCardAdapter.MyViewHolder> {

    private static final String TAG = "CartAdapter";

    int count = 0;
    int total;

//  TextView tv_randomColour;

    private Activity mContext;
    ArrayList<FourCardModel> list;
    ArrayList<Integer> listInteger;


    String amt;
    private  ItemClickListener itemClickListener;

    public FourCardAdapter(Activity context, ArrayList<FourCardModel> list, String amt, ItemClickListener itemClickListener) {
        this.list = list;
        mContext = context;
        amt = amt;
    }

    public FourCardAdapter(Activity context, ArrayList<FourCardModel> list, ItemClickListener itemClickListener){
        mContext = context;
        this.list = list;
        this.itemClickListener=itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, null);

     //   tv_randomColour = view.findViewById(R.id.tv_randomColour);//4
       // Random random = new Random();
        //int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        //tv_randomColour.setBackgroundColor(color);
        Log.e(TAG, "onCreateViewHolder: "+amt );


        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final FourCardModel item = list.get(i);

        Log.e("vlist", list.toString());

        viewHolder.img.setBackground(mContext.getResources().getDrawable(item.getImage_drawable()));

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
            //tv_randomColour = itemView.findViewById(R.id.tv_randomColour);
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
    public void setFilter(ArrayList<FourCardModel> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}

