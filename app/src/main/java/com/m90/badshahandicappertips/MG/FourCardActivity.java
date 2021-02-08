package com.m90.badshahandicappertips.MG;

import android.app.Activity;
import android.app.ProgressDialog;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.m90.badshahandicappertips.MG.Adapters.CardThirteenAdapter;
import com.m90.badshahandicappertips.MG.Adapters.FourCardAdapter;
import com.m90.badshahandicappertips.MG.Models.CardThirteenModel;
import com.m90.badshahandicappertips.MG.Models.FourCardModel;
import com.m90.badshahandicappertips.R;
import com.m90.badshahandicappertips.fragment.AmountSelectionAdapter;
import com.m90.badshahandicappertips.fragment.AmountSelectionModel;
import com.m90.badshahandicappertips.home.Select_Nav_Menu;

import java.util.ArrayList;

public class FourCardActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG= FourCardActivity.class.getSimpleName();
    ImageView img_arrowback;


    private int[] cardListFour = new int[]{R.drawable.img_coin, R.drawable.img_coin,R.drawable.img_coin,
            R.drawable.img_coin,R.drawable.img_coin};

    private int[] cardListThirteen = new int[]{R.drawable.img_cardbg, R.drawable.img_cardbg,R.drawable.img_cardbg,
            R.drawable.img_cardbg,R.drawable.img_cardbg, R.drawable.img_cardbg,R.drawable.img_cardbg,
            R.drawable.img_cardbg,R.drawable.img_cardbg, R.drawable.img_cardbg,R.drawable.img_cardbg,
            R.drawable.img_cardbg,R.drawable.img_cardbg};

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
    TextView tv_total;
    Activity activity;
    AmountSelectionAdapter adapter;         //4
    TextView tv_back;
    TextView tv_amount;
    ArrayList<AmountSelectionModel> modelArrayList ; //4
    RecyclerView rv_recycl13;//13
    RecyclerView rv_cardsFour;//13

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_13cards);
        activity = FourCardActivity.this;
        setStatusbarColor();

        progressDialog= new ProgressDialog(activity);
        rv_recycler = findViewById(R.id.rv_recycler);
        rv_recycl13 = findViewById(R.id.rv_recycl13);//13
        rv_cardsFour = findViewById(R.id.rv_cardsFour);//13
        tv_back = findViewById(R.id.tv_back);//13
        tv_amount = findViewById(R.id.tv_amount);//13

        tv_back.setOnClickListener(this);
        init();
        setFourCards();
        setAmountFourCard(tv_amount.getText().toString());
        setCardsThirteen();


    }
    public void init()
    {
        progressDialog= new ProgressDialog(this);
        rv_recycler = findViewById(R.id.rv_recycler);//4
        rv_cardThirtreen = findViewById(R.id.rv_cardThirtreen);

        tv_total = findViewById(R.id.tv_total);
        img_arrowback = findViewById(R.id.img_back);
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

                        Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_SHORT).show();

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
                        }

                        int total = Integer.parseInt(tv_amount.getText().toString());
                        if (total!=0)
                        {
                            int showAmt = total + Integer.parseInt(modelArrayList.get(position).getName());
                            tv_amount.setText(String.valueOf(showAmt));
                            Log.e(TAG, "total: "+total +" "+modelArrayList.get(position).getName() +" "+showAmt );
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

    @Override
    public void onClick(View v) {

    }
}