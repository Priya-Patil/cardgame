package com.m90.badshahandicappertips.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import android.transition.TransitionManager;
import android.transition.AutoTransition;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.m90.badshahandicappertips.R;

public class Frag_Nav_WalletHistory extends Fragment  implements AdapterView.OnItemSelectedListener{

    View root;
    TextView tvRecharge,tvWalletHistory,tv_RechargeNow;

    RelativeLayout hiddenView;
    CardView cardview_Recharge;

    String[] amount = { "10 Rs", "20 Rs",
            "50 Rs", "100 Rs",
            "500 Rs", "1000 Rs" };
    Spinner  spinAmt;
    AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_wallet_history, container, false);

        tvRecharge = root.findViewById(R.id.tvRecharge);
        tvWalletHistory = root.findViewById(R.id.tvWalletHistory);


        tv_RechargeNow = root.findViewById(R.id.tv_RechargeNow);
        tvRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvWalletHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        cardview_Recharge = root.findViewById(R.id.cardview_Recharge);//expand
        hiddenView = root.findViewById(R.id.hidden_view);//expamd

        tv_RechargeNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // If the CardView is already expanded, set its visibility
                //  to gone and change the expand less icon to expand more.
                if (hiddenView.getVisibility() == View.VISIBLE) {

                    // The transition of the hiddenView is carried out
                    //  by the TransitionManager class.
                    // Here we use an object of the AutoTransition
                    // Class to create a default transition.
                    TransitionManager.beginDelayedTransition(cardview_Recharge,
                            new AutoTransition());
                    hiddenView.setVisibility(View.GONE);
                    // tv_RechargeNow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }

                // If the CardView is not expanded, set its visibility
                // to visible and change the expand more icon to expand less.
                else {

                    TransitionManager.beginDelayedTransition(cardview_Recharge,
                            new AutoTransition());
                    hiddenView.setVisibility(View.VISIBLE);
                    // arrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });

        spinAmt = root.findViewById(R.id.sp_Amt);
        spinAmt.setOnItemSelectedListener(this);
        builder = new AlertDialog.Builder(getContext());

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad
                = new ArrayAdapter(
                getContext(),
                android.R.layout.simple_spinner_item,
                amount);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spinAmt.setAdapter(ad);

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}


