package com.m90.badshahandicappertips.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.m90.badshahandicappertips.R;
import com.m90.badshahandicappertips.home.HomeButtonsActivity;
import com.m90.badshahandicappertips.home.adapter.CardThirteenAdapter;
import com.m90.badshahandicappertips.home.adapter.CitynameHomeAdapter;
import com.m90.badshahandicappertips.model.CardThirteenModel;
import com.m90.badshahandicappertips.model.HomeModel;
import com.m90.badshahandicappertips.utils.Utilities;

import java.util.ArrayList;

public class FourCardHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] myImageList = new int[]{R.drawable.img_coin, R.drawable.img_coin,R.drawable.img_coin,
            R.drawable.img_coin,R.drawable.img_coin};

// private int[] myImageList1 = new int[]{1,2,3,4,5};

    private String[] myImageList1 = new String[]{"1","2","3","4","5"};
    TextView tv_total;

    private int[] myImageList13 = new int[]{R.drawable.img_cardbg, R.drawable.img_cardbg,R.drawable.img_cardbg,
            R.drawable.img_cardbg,R.drawable.img_cardbg, R.drawable.img_cardbg,R.drawable.img_cardbg,
            R.drawable.img_cardbg,R.drawable.img_cardbg, R.drawable.img_cardbg,R.drawable.img_cardbg,
            R.drawable.img_cardbg,R.drawable.img_cardbg};

    CitynameHomeAdapter adapter;         //4
    ArrayList<HomeModel> modelArrayList ; //4

    CardThirteenAdapter adapter13;         //13
    ArrayList<CardThirteenModel> modelArrayList13 ; //13

    TextView tv_randomColour;
    View view;
    ProgressDialog progressDialog;
    RecyclerView rv_recycler;
    TextView tv_back;

    RecyclerView rv_recycl13;//13
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_13cards);
        activity = FourCardHomeActivity.this;

        progressDialog= new ProgressDialog(activity);
        rv_recycler = findViewById(R.id.rv_recycler);
        rv_recycl13 = findViewById(R.id.rv_cardThirtreen);//13
        tv_back = findViewById(R.id.tv_back);//13


        tv_total = findViewById(R.id.tv_total);//4
        tv_back.setOnClickListener(this);

        setCity(tv_total.getText().toString());
        setCards13();
    }

    void setCity(String amt)
    {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        modelArrayList = arrayDailyQuiz();

        rv_recycler.setLayoutManager(new LinearLayoutManager(activity,
                LinearLayoutManager.HORIZONTAL, false));
        rv_recycler.setItemAnimator(new DefaultItemAnimator());
        rv_recycler.setHasFixedSize(true);

        adapter = new CitynameHomeAdapter(activity, modelArrayList,amt,
                new CitynameHomeAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        rv_recycler.setAdapter(adapter);
        progressDialog.dismiss();
    }

    private ArrayList<HomeModel> arrayDailyQuiz(){
        ArrayList<HomeModel> list = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            HomeModel homeModel = new HomeModel();
            homeModel.setName(myImageList1[i]);//by sneha
            homeModel.setImage_drawable(myImageList[i]);
            list.add(homeModel);
        }
        return list;
    }


    //code for 13 cards
    void setCards13()
    {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        modelArrayList13 = arrayCard13();

        GridLayoutManager layoutManager=new GridLayoutManager(activity,5);
        //  LinearLayout glm = new LinearLayout(getContext());
        //  rv_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_recycl13.setItemAnimator(new DefaultItemAnimator());
        rv_recycl13.setHasFixedSize(true);
        rv_recycl13.setLayoutManager(layoutManager);
        // recyclerView.setAdapter(adapter);


        adapter13 = new CardThirteenAdapter(activity, modelArrayList13,
                new CardThirteenAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        Toast.makeText(activity, "Position " + position, Toast.LENGTH_SHORT).show();

                    }
                });
        rv_recycl13.setAdapter(adapter13);
        progressDialog.dismiss();
    }
    private ArrayList<CardThirteenModel> arrayCard13(){
        ArrayList<CardThirteenModel> list = new ArrayList<>();
        for(int i = 0; i < 13; i++){
            CardThirteenModel card13Model = new CardThirteenModel();
            // homeModel.setName(myImageNameList[i]);
            card13Model.setImage_drawable(myImageList13[i]);
            list.add(card13Model);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_back:
                Utilities.launchActivity(activity, HomeButtonsActivity.class,false);
                break;
        }
    }
}