package com.m90.badshahandicappertips.wallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.m90.badshahandicappertips.R;

import com.m90.badshahandicappertips.databinding.ActivityWalletBinding;
import com.m90.badshahandicappertips.home.HomeActivity;
import com.m90.badshahandicappertips.retrofit.RetrofitClientInstance;
import com.m90.badshahandicappertips.utils.PrefManager;
import com.m90.badshahandicappertips.utils.Utilities;
import com.m90.badshahandicappertips.wallet.Adapters.WalletHistoryAdapter;
import com.m90.badshahandicappertips.wallet.api.WalletApi;
import com.m90.badshahandicappertips.wallet.model.WalletHistoryResponce;
import com.m90.badshahandicappertips.wallet.model.WalletRechargeResponce;
import com.m90.badshahandicappertips.wallet.model.WalletResponce;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener   {

    public static String TAG= WalletActivity.class.getSimpleName();

    ActivityWalletBinding binding;
    TextView tv_RechargeNow;
    TextView tv_Ok,tv_rechOk,tv_Cancle;
    ImageView img_arrowback;

    RelativeLayout rl_WalltHistory,rl_Recharge;

    String[] amount = {"10 Rs", "20 Rs",
            "50 Rs", "100 Rs",
            "500 Rs", "1000 Rs"};
    Spinner spinAmt;
    AlertDialog.Builder builder;
    Activity activity;
    ProgressDialog progressDialog;
    ArrayList<WalletHistoryResponce> walletHistoryResponces ;
    WalletHistoryAdapter walletHistoryAdapter;
    int walletIdForRecharge = 0;
    int userId = 0;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_wallet);
        setStatusbarColor();
        activity = WalletActivity.this;
        progressDialog =  new ProgressDialog(activity);
        prefManager = new PrefManager(activity);
        tv_RechargeNow = findViewById(R.id.tv_RechargeNow);
        img_arrowback = findViewById(R.id.img_arrowback);

        if (prefManager.getUserid()!=0) {
            Log.e(TAG, "onCreate: "+prefManager.getUserid() );
            userId = prefManager.getUserid();
        }

        getrechargeWalletHistory(userId);
        tv_RechargeNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialouge();
            }
        });

         img_arrowback.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                 startActivity(intent);

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
        spinAmt.setOnItemSelectedListener(this);
        builder = new AlertDialog.Builder(this);

        getWalletType(spinAmt);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        tv_Ok = dialogView.findViewById(R.id.tv_Ok);
        tv_rechOk = dialogView.findViewById(R.id.tv_rechOk);
        tv_Cancle = dialogView.findViewById(R.id.tv_Cancle);

        tv_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_rechOk.setVisibility(View.VISIBLE);
                    Log.e(TAG, "onClick: "+walletIdForRecharge );
                    rechargeWallet(userId, walletIdForRecharge);



            }
        });

        tv_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }


    void getWalletType(Spinner spinAmt)
    {
        if(Utilities.isNetworkAvailable(activity))
        {
            WalletApi walletApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(WalletApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            walletApi.getWalletType().
                    enqueue(new Callback<ArrayList<WalletResponce>>() {

                        @Override
                        public void onResponse(Call<ArrayList<WalletResponce>> call, Response<ArrayList<WalletResponce>> response) {

                            ArrayList<WalletResponce> walletResponces = response.body();
                            Log.e(TAG, "getWalletType: "+walletResponces.toString());


                                Log.e(TAG, "onResponse: "+walletResponces.toString() );

                                Integer[] spinnerArray = new Integer[walletResponces.size()];
                                HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
                                for (int i = 0; i < walletResponces.size(); i++)
                                {
                                    spinnerMap.put(i, String.valueOf(walletResponces.get(i).id));
                                    spinnerArray[i] = walletResponces.get(i).amount;
                                }
                                ArrayAdapter<Integer> adapter =new ArrayAdapter<Integer>(WalletActivity.this,
                                        android.R.layout.simple_spinner_item, spinnerArray);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinAmt.setAdapter(adapter);
                                //spinAmt.setSelection(0,true);;


                                spinAmt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

                                        String name = spinAmt.getSelectedItem().toString();
                                        String id1 = spinnerMap.get(spinAmt.getSelectedItemPosition());
                                        Toast.makeText(activity,""+id1 +" "+name,Toast.LENGTH_SHORT).show();
                                        walletIdForRecharge = Integer.parseInt(id1);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                            
                            progressDialog.dismiss();
                            //Utilities.launchActivity(activity, HomeButtonsActivity.class, true);
                        }

                        @Override
                        public void onFailure(Call <ArrayList<WalletResponce>> call, Throwable t) {

                            progressDialog.dismiss();
                            //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                            Log.d("errorchk",t.getMessage());

                        }
                    });
        }
        else {

            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();

        }
    }

    void getrechargeWalletHistory(int uid)
    {
        Log.e(TAG, "getrechargeWalletHistory: calleddd" );
        if(Utilities.isNetworkAvailable(activity))
        {
            WalletApi walletApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(WalletApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            walletApi.getrechargeWalletHistory(uid).
                    enqueue(new Callback<ArrayList<WalletHistoryResponce>>() {

                        @Override
                        public void onResponse(Call<ArrayList<WalletHistoryResponce>> call, Response<ArrayList<WalletHistoryResponce>> response) {

                            ArrayList<WalletHistoryResponce> walletResponces = response.body();

                            Log.e(TAG, "getWalletHistoryResponce: "+walletResponces.toString() );

                            setWalletHistory(walletResponces);
                            progressDialog.dismiss();
                            //Utilities.launchActivity(activity, HomeButtonsActivity.class, true);
                        }

                        @Override
                        public void onFailure(Call <ArrayList<WalletHistoryResponce>> call, Throwable t) {

                            progressDialog.dismiss();
                            //Utilities.setError(layout1,layout2,txt_error,getResources().getString(R.string.something_went_wrong));
                            Log.d("errorchk",t.getMessage());

                        }
                    });
        }
        else {
            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    void setWalletHistory(ArrayList<WalletHistoryResponce> walletHistory)
    {
        walletHistoryResponces = walletHistory;

        binding.rvWalletHistory.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        binding.rvWalletHistory.setItemAnimator(new DefaultItemAnimator());
        binding.rvWalletHistory.setHasFixedSize(true);
        walletHistoryAdapter = new WalletHistoryAdapter(activity,walletHistoryResponces);

        binding.rvWalletHistory.setAdapter(walletHistoryAdapter);
    }


    public void setStatusbarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.color_blue));

        }

    }


    void rechargeWallet(int userId,int walletId)
    {
        if(Utilities.isNetworkAvailable(activity))
        {
            WalletApi walletApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(WalletApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            // pbLoading.setProgressStyle(R.id.abbreviationsBar);
            progressDialog.show();
            walletApi.rechargeWallet(userId,walletId).
                    enqueue(new Callback<WalletRechargeResponce>() {

                        @Override
                        public void onResponse(Call<WalletRechargeResponce> call, Response<WalletRechargeResponce> response) {

                            WalletRechargeResponce walletRechargeResponce = response.body();

                            finish();
                            startActivity(getIntent());
                            Log.e(TAG, "onResponse: "+walletRechargeResponce.toString() );
                            progressDialog.dismiss();

                        }

                        @Override
                        public void onFailure(Call<WalletRechargeResponce> call, Throwable t) {

                            progressDialog.dismiss();
                            Log.d("errorchk",t.getMessage());

                        }
                    });
        }
        else {

            Toast.makeText(activity, getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();

        }
    }


    /*void setGameHistory(ArrayList<BettingResponce> bettingResponce)
    {
        modelArrayListHistory = bettingResponce;

        binding.rvWalletHistory.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        binding.rvWalletHistory.setItemAnimator(new DefaultItemAnimator());
        binding.rvWalletHistory.setHasFixedSize(true);
        gameHistoryAdapter = new GameHistoryAdapter(GameHistoryActivity.this,modelArrayListHistory);

        binding.rvWalletHistory.setAdapter(gameHistoryAdapter);
    }*/
}