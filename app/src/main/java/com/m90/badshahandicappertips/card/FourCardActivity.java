package com.m90.badshahandicappertips.card;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.m90.badshahandicappertips.card.adapter.FourCardAdapter;
import com.m90.badshahandicappertips.card.api.BettingApi;
import com.m90.badshahandicappertips.card.model.BettingResponce;
import com.m90.badshahandicappertips.card.model.CardThirteenModel;
import com.m90.badshahandicappertips.card.model.FourCardModel;
import com.m90.badshahandicappertips.R;
import com.m90.badshahandicappertips.card.adapter.CardThirteenAdapter;
import com.m90.badshahandicappertips.databinding.ActivityHomeCardsBinding;
import com.m90.badshahandicappertips.fragment.AmountSelectionAdapter;
import com.m90.badshahandicappertips.fragment.AmountSelectionModel;
import com.m90.badshahandicappertips.home.HomeActivity;
import com.m90.badshahandicappertips.retrofit.RetrofitClientInstance;
import com.m90.badshahandicappertips.utils.PrefManager;
import com.m90.badshahandicappertips.utils.Utilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FourCardActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityHomeCardsBinding binding;

    public static String TAG= FourCardActivity.class.getSimpleName();
    ImageView img_arrowback;


    private int[] cardListFour = new int[]{R.drawable.imagesace,R.drawable.imagesjack,
            R.drawable.imagesqueen,R.drawable.imagesking};

    private int[] cardListThirteen = new int[]{R.drawable.spadea, R.drawable.spade2,R.drawable.spade3,
            R.drawable.spade4,R.drawable.spade5, R.drawable.sapde6,R.drawable.spade7,
            R.drawable.spade8,R.drawable.spade9, R.drawable.spade10,R.drawable.imagesjack,
            R.drawable.imagesqueen,R.drawable.imagesking};

    private int[] myImageList = new int[]{R.drawable.img_coin, R.drawable.img_coin,R.drawable.img_coin,
            R.drawable.img_coin,R.drawable.img_coin};

    private String[] myImageList1 = new String[]{"1","2","3","4","5"};

    private int[] myImageListFour = new int[]{R.drawable.img_cardbg, R.drawable.img_cardbg,R.drawable.img_cardbg,
            R.drawable.img_cardbg,R.drawable.img_cardbg, R.drawable.img_cardbg,R.drawable.img_cardbg,
            R.drawable.img_cardbg,R.drawable.img_cardbg, R.drawable.img_cardbg,R.drawable.img_cardbg,
            R.drawable.img_cardbg,R.drawable.img_cardbg};

    CardThirteenAdapter adapterThirteen;
    ArrayList<CardThirteenModel> modelArrayListThirteen ;
    ArrayList<FourCardModel> fourModelsArralist ;

    FourCardAdapter adapterFour;
    ArrayList<FourCardModel> modelArrayListFour ;

    ProgressDialog progressDialog;
    RecyclerView rv_recycler,rv_cardThirtreen;
    RecyclerView rv_cardsFour;//13

    TextView tv_total;
    Activity activity;
    AmountSelectionAdapter adapter;         //4
    TextView tv_back;
    TextView tv_amount;
    ArrayList<AmountSelectionModel> modelArrayList ; //4
    RecyclerView rv_recycl13;//13
    int cardSelected = 0;

    int bettingAmt = 0;
    int userId = 0;
    String fourORThirteenCards = "";
    int cardType= 0;
    PrefManager prefManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_cards);
        activity = FourCardActivity.this;
        prefManager = new PrefManager(activity);
        setStatusbarColor();
        init();

        if (prefManager.getUserid()!=0) {
            Log.e(TAG, "onCreate: "+prefManager.getUserid() );
            userId = prefManager.getUserid();
        }

        if (getIntent().getStringExtra("FourORThirteenCards")!=null)
        {
            fourORThirteenCards = getIntent().getStringExtra("FourORThirteenCards");
            if (fourORThirteenCards.equals("FourCard"))
            {
                binding.rvCardsFour.setVisibility(View.VISIBLE);
                binding.rvCardThirtreen.setVisibility(View.GONE);
            }else if (fourORThirteenCards.equals("ThirteenCard")){
                binding.rvCardsFour.setVisibility(View.GONE);
                binding.rvCardThirtreen.setVisibility(View.VISIBLE);
            }
        }

        setFourCards();
        setAmountFourCard(tv_amount.getText().toString());
        setCardsThirteen();

        img_arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }
    public void init()
    {
        progressDialog= new ProgressDialog(activity);
        rv_recycler = findViewById(R.id.rv_recycler);
        rv_cardsFour = findViewById(R.id.rv_cardsFour);//13
        tv_back = findViewById(R.id.tv_back);//13
        tv_amount = findViewById(R.id.tv_amount);//13
        rv_recycler = findViewById(R.id.rv_recycler);//4
        rv_cardThirtreen = findViewById(R.id.rv_cardThirtreen);

        tv_total = findViewById(R.id.tv_total);
        img_arrowback = findViewById(R.id.iv_back);

        binding.tvContinue.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        tv_back.setOnClickListener(this);
    }

//code for FOUR CARDS
    void setFourCards()
    {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        fourModelsArralist = arrayFourCard();

        rv_cardsFour.setLayoutManager(new GridLayoutManager(this,2));
        rv_cardsFour.setItemAnimator(new DefaultItemAnimator());
        rv_cardsFour.setHasFixedSize(true);

        adapterFour = new FourCardAdapter(this, fourModelsArralist,
                new FourCardAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        cardType = 1;
                        cardDialog(activity,position+1);
                    }
                });
        rv_cardsFour.setAdapter(adapterFour);
        progressDialog.dismiss();
    }

    private ArrayList<FourCardModel> arrayFourCard(){
        ArrayList<FourCardModel> list = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            FourCardModel fourCardModel = new FourCardModel();
          //  homeModel.setName(cardListFour1[i]);//by sneha
            fourCardModel.setImage_drawable(cardListFour[i]);
            list.add(fourCardModel);
        }
        return list;
    }

    //code for Thirteen CARDS

    void setCardsThirteen()
    {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        modelArrayListThirteen = arrayCardThirteen();

        GridLayoutManager layoutManager=new GridLayoutManager(this,5);
        //  LinearLayout glm = new LinearLayout(getContext());
        //  rv_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_cardThirtreen.setItemAnimator(new DefaultItemAnimator());
        rv_cardThirtreen.setHasFixedSize(true);
        rv_cardThirtreen.setLayoutManager(layoutManager);
        // recyclerView.setAdapter(adapter);

        adapterThirteen = new CardThirteenAdapter(this, modelArrayListThirteen,
                new CardThirteenAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        cardType = 2;
                        cardDialog(activity,position+1);

                    }
                });
        rv_cardThirtreen.setAdapter(adapterThirteen);
        progressDialog.dismiss();
    }

    private ArrayList<CardThirteenModel> arrayCardThirteen(){
        ArrayList<CardThirteenModel> list = new ArrayList<>();
        for(int i = 0; i < 13; i++){
            CardThirteenModel cardThirteenModel = new CardThirteenModel();
            // homeModel.setName(myImageNameList[i]);
            cardThirteenModel.setImage_drawable(cardListThirteen[i]);
            list.add(cardThirteenModel);
        }
        return list;
    }


    void setAmountFourCard(String amt)
    {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        modelArrayList = arrayDailyQuiz();

        rv_recycler.setLayoutManager(new LinearLayoutManager(activity,
                LinearLayoutManager.HORIZONTAL, false));
        rv_recycler.setItemAnimator(new DefaultItemAnimator());
        rv_recycler.setHasFixedSize(true);

        adapter = new AmountSelectionAdapter(activity, modelArrayList,
                new AmountSelectionAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        if (tv_amount.getText().toString().equals("0"))
                        {
                            tv_amount.setText(modelArrayList.get(position).getName());
                            bettingAmtDialog(activity, "0", Integer.parseInt(tv_amount.getText().toString()));

                        }else{
                        int total = Integer.parseInt(tv_amount.getText().toString());

                                if (total != 0) {
                                    bettingAmtDialog(activity, modelArrayList.get(position).getName(), total);
                                }

                      }
                    }
                });
        rv_recycler.setAdapter(adapter);
        progressDialog.dismiss();
    }

    private ArrayList<AmountSelectionModel> arrayDailyQuiz(){
        ArrayList<AmountSelectionModel> list = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            AmountSelectionModel amountSelectionModel = new AmountSelectionModel();
            amountSelectionModel.setName(myImageList1[i]);//by sneha
            amountSelectionModel.setImage_drawable(myImageList[i]);
            list.add(amountSelectionModel);
        }
        return list;
    }


    private ArrayList<CardThirteenModel> arrayCardFour(){
        ArrayList<CardThirteenModel> list = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            CardThirteenModel card13Model = new CardThirteenModel();
            // homeModel.setName(myImageNameList[i]);
            card13Model.setImage_drawable(myImageListFour[i]);
            list.add(card13Model);
        }
        return list;
    }


    public void setStatusbarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.color_blue));

        }
    }

    public void dialog(Activity context, String message, int bettingAmt, int cardSelected) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setTitle(message);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                dialog.dismiss();
                Log.e(TAG, "onClick: "+cardType +" "+bettingAmt +" "+cardSelected );
                addBetting(userId,cardType, bettingAmt,cardSelected);
                //Utilities.launchActivity(context, LoginActivity.class,true);

            }
        });

        mBuilder.setNegativeButton("Cancel", null);
        AlertDialog mDialog = mBuilder.create();
        mDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        mDialog.show();
    }


     public void cardDialog(Activity context,int position) {

        String message ="You have selected card No. "+position;
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setTitle(message);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                cardSelected = position;
                dialog.dismiss();
                //Utilities.launchActivity(context, LoginActivity.class,true);
            }
        });

        mBuilder.setNegativeButton("Cancel", null);
        AlertDialog mDialog = mBuilder.create();
        mDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        mDialog.show();
    }


     public void bettingAmtDialog(Activity context, String name, int total) {

         int amt = total + Integer.parseInt(name);
         tv_amount.setText(String.valueOf(amt));
         Log.e(TAG, "total: "+total +" "+name +" "+amt );

         String message ="You have choosen Amount "+amt;
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setTitle(message);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                bettingAmt = amt;
                dialog.dismiss();
                //Utilities.launchActivity(context, LoginActivity.class,true);
            }
        });

        mBuilder.setNegativeButton("Cancel", null);
        AlertDialog mDialog = mBuilder.create();
        mDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        mDialog.show();
    }


    void addBetting(int uid,int gid,int amount,int card)
    {
        if(Utilities.isNetworkAvailable(activity))
        {
            BettingApi bettingApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(BettingApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            // pbLoading.setProgressStyle(R.id.abbreviationsBar);
            progressDialog.show();
            bettingApi.addBetting(uid,gid,amount,card).
                    enqueue(new Callback<BettingResponce>() {

                        @Override
                        public void onResponse(Call<BettingResponce> call, Response<BettingResponce> response) {

                            BettingResponce bettingResponce = response.body();
                            Log.e(TAG, "onResponse: "+bettingResponce.toString());
                            progressDialog.dismiss();
                            //Utilities.launchActivity(activity, HomeButtonsActivity.class, true);
                        }

                        @Override
                        public void onFailure(Call<BettingResponce> call, Throwable t) {

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

    @Override
    public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.tv_continue:
                    Log.e(TAG, "onClick: "+bettingAmt+" "+cardSelected );
                    if (cardSelected ==0)
                    {
                        Utilities.dialog(activity,"Please Select Card");
                    }else if (bettingAmt == 0){
                        Utilities.dialog(activity,"Please Select Betting Amount");
                    }else {
                        dialog(activity, "Your Betting Amount is " + bettingAmt + " and Selected Card is " + cardSelected,bettingAmt,cardSelected);
                    }
                    break;


                 case R.id.iv_back:
                    onBackPressed();
                    break;



            }
    }
}