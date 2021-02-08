package com.m90.badshahandicappertips.fragment;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.m90.badshahandicappertips.R;


import java.util.ArrayList;

public class AmountSelectionAdapter extends RecyclerView.Adapter<AmountSelectionAdapter.MyViewHolder> {

    private static final String TAG = "FourCardAdapter";

    int count = 0;
    int total;


    private Activity mContext;
    ArrayList<AmountSelectionModel> list;
    ArrayList<Integer> listInteger;
    String amt;
    private  ItemClickListener itemClickListener;

    //declare interface
    private OnItemClicked onClick;

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position, String name);
    }

    public AmountSelectionAdapter(Activity context, ArrayList<AmountSelectionModel> list, ItemClickListener itemClickListener) {
        this.list = list;
        mContext = context;
        this.itemClickListener=itemClickListener;

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
        final AmountSelectionModel item = list.get(i);

        Log.e("vlist", list.toString());

        /// for total
      viewHolder.tv_randomColour.setText(item.getName());
      viewHolder.tv_amt.setText(String.valueOf(item.getName()));
      /* viewHolder.tv_randomColour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              viewHolder.tv_randomColour.getText().toString();         //////by sneha
                Toast.makeText(mContext, "Position " + i, Toast.LENGTH_SHORT).show();


            }
        });
*/

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_randomColour,tv_amt;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_randomColour = itemView.findViewById(R.id.tv_randomColour);
            img = itemView.findViewById(R.id.img_rvItem);
            tv_amt = itemView.findViewById(R.id.tv_amt);
            itemView.setOnClickListener(this); // bind the listener

        }
        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());

                     tv_randomColour.setText("2");

        }
    }

    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<AmountSelectionModel> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}

