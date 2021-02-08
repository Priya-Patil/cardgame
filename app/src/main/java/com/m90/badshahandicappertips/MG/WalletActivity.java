package com.m90.badshahandicappertips.MG;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.m90.badshahandicappertips.R;
import com.m90.badshahandicappertips.home.Select_Nav_Menu;

public class WalletActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener   {
    TextView tvRecharge,tvWalletHistory,tv_RechargeNow;
    TextView tv_Ok,tv_rechOk,tv_Cancle;
    ImageView img_arrowback;

    RelativeLayout rl_WalltHistory,rl_Recharge;

    String[] amount = {"10 Rs", "20 Rs",
            "50 Rs", "100 Rs",
            "500 Rs", "1000 Rs"};
    Spinner spinAmt;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        setStatusbarColor();

        tvRecharge = findViewById(R.id.tvRecharge);
        tvWalletHistory = findViewById(R.id.tvWalletHistory);
        tv_RechargeNow = findViewById(R.id.tv_RechargeNow);
        img_arrowback = findViewById(R.id.img_arrowback);


        tv_RechargeNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialouge();
            }
        });

         img_arrowback.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 /*Frag_Nav_Home_Button fragment2 = new Frag_Nav_Home_Button();
                 FragmentManager fragmentManager = getFragmentManager();
                 FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                 fragmentTransaction.replace(R.id.Frame_container, fragment2);
                 fragmentTransaction.commit();*/

                 Intent intent = new Intent(getApplicationContext(), Select_Nav_Menu.class);
                 startActivity(intent);


               /*  Fragment Home = new Frag_Nav_Home_Button();
                 FragmentManager fragmentManager = getFragmentManager();

                 FragmentTransaction  fragmentTransaction;
                 fragmentManager.beginTransaction().replace(R.id.Frame_container,);
                 fragmentTransaction.commit();*/
             }
         });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tv_rechOk.setVisibility(View.VISIBLE);
        tv_rechOk.setText("recharge of "+amount[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void dialouge()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.customdialouge_recharge, null);
        dialogBuilder.setView(dialogView);

        spinAmt = dialogView.findViewById(R.id.sp_Amt);

        // spinAmt = root.findViewById(R.id.sp_Amt);
        spinAmt.setOnItemSelectedListener(this);
        builder = new AlertDialog.Builder(this);

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, amount);
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
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
        // EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
        //editText.setText("test label");
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        tv_Ok = dialogView.findViewById(R.id.tv_Ok);
        tv_rechOk = dialogView.findViewById(R.id.tv_rechOk);
        tv_Cancle = dialogView.findViewById(R.id.tv_Cancle);

        tv_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_rechOk.setVisibility(View.VISIBLE);
            }
        });

        tv_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }



    public void setStatusbarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.color_blue));

        }

    }


}