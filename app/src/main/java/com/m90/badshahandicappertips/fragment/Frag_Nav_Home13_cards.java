package com.m90.badshahandicappertips.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m90.badshahandicappertips.R;
import com.m90.badshahandicappertips.home.Select_Nav_Menu;
import com.m90.badshahandicappertips.home.adapter.CardThirteenAdapter;
import com.m90.badshahandicappertips.home.adapter.CitynameHomeAdapter;
import com.m90.badshahandicappertips.model.CardThirteenModel;
import com.m90.badshahandicappertips.model.HomeModel;
import com.m90.badshahandicappertips.utils.Utilities;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

public class Frag_Nav_Home13_cards extends Fragment implements View.OnClickListener {
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home_13cards, container, false);
        progressDialog= new ProgressDialog(getActivity());
        rv_recycler = view.findViewById(R.id.rv_recycler);
        rv_recycl13 = view.findViewById(R.id.rv_cardThirtreen);//13
        tv_back = view.findViewById(R.id.tv_back);//13


        tv_total = view.findViewById(R.id.tv_total);//4
        tv_back.setOnClickListener(this);

        setCity(tv_total.getText().toString());
        setCards13();
        return view;
    }

    void setCity(String amt)
    {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        modelArrayList = arrayDailyQuiz();

        rv_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        rv_recycler.setItemAnimator(new DefaultItemAnimator());
        rv_recycler.setHasFixedSize(true);

        adapter = new CitynameHomeAdapter(getActivity(), modelArrayList,amt,
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

        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),5);
        //  LinearLayout glm = new LinearLayout(getContext());
        //  rv_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_recycl13.setItemAnimator(new DefaultItemAnimator());
        rv_recycl13.setHasFixedSize(true);
        rv_recycl13.setLayoutManager(layoutManager);
       // recyclerView.setAdapter(adapter);


        adapter13 = new CardThirteenAdapter(getActivity(), modelArrayList13,
                new CardThirteenAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        Toast.makeText(getContext(), "Position " + position, Toast.LENGTH_SHORT).show();

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
                Utilities.launchActivity(getActivity(), FourCardHomeActivity.class,false);
                break;
        }
    }
}
