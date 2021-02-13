package com.m90.badshahandicappertips.gamehistory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m90.badshahandicappertips.card.FourCardActivity;
import com.m90.badshahandicappertips.card.api.BettingApi;
import com.m90.badshahandicappertips.card.model.BettingResponce;
import com.m90.badshahandicappertips.gamehistory.Adapters.GameHistoryAdapter;
import com.m90.badshahandicappertips.gamehistory.Models.GameHistoryModel;
import com.m90.badshahandicappertips.R;
import com.m90.badshahandicappertips.retrofit.RetrofitClientInstance;
import com.m90.badshahandicappertips.utils.PrefManager;
import com.m90.badshahandicappertips.utils.Utilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameHistoryActivity extends AppCompatActivity {

    public static String TAG= GameHistoryActivity.class.getSimpleName();

    private String[] historydateList = new String[]{"11/2/2021", "12/2/2021","13/2/2021",
            "14/2/2021","15/2/2021"};
    private String[] historyTimeList = new String[]{"11.30 am","12.30 pm","11.30 am","1.30 pm","2.30 am"};
    private String[] historyAmountList = new String[]{"1000","2000","3000","4000","5000"};


    RecyclerView rv_gameHistory;

    GameHistoryAdapter gameHistoryAdapter;
    ArrayList<BettingResponce> modelArrayListHistory ;
    Activity activity;
    ProgressDialog progressDialog;
    int userId = 0;
    PrefManager prefManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gamehistory);
        setStatusbarColor();
        activity = GameHistoryActivity.this;
        rv_gameHistory=findViewById(R.id.rv_gameHistory);
        progressDialog =  new ProgressDialog(activity);
        prefManager = new PrefManager(activity);

        if (prefManager.getUserid()!=0) {
            Log.e(TAG, "onCreate: "+prefManager.getUserid() );
            userId = prefManager.getUserid();
        }

        getBetting(userId);

    }





    void getBetting(int uid)
    {
        if(Utilities.isNetworkAvailable(activity))
        {
            BettingApi bettingApi = RetrofitClientInstance.getRetrofitInstanceServer().
                    create(BettingApi.class);

            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            // pbLoading.setProgressStyle(R.id.abbreviationsBar);
            progressDialog.show();
            bettingApi.getBetting(uid).
                    enqueue(new Callback<ArrayList<BettingResponce>>() {

                        @Override
                        public void onResponse(Call<ArrayList<BettingResponce>>call, Response <ArrayList<BettingResponce>> response) {

                            ArrayList<BettingResponce> bettingResponce = response.body();
                            Log.e(TAG, "getBetting: "+bettingResponce.toString());
                            setGameHistory(bettingResponce);
                            progressDialog.dismiss();
                            //Utilities.launchActivity(activity, HomeButtonsActivity.class, true);
                        }

                        @Override
                        public void onFailure(Call <ArrayList<BettingResponce>> call, Throwable t) {

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

     void setGameHistory(ArrayList<BettingResponce> bettingResponce)
   {
       modelArrayListHistory = bettingResponce;

       rv_gameHistory.setLayoutManager(new LinearLayoutManager(this,
               LinearLayoutManager.VERTICAL, false));
       rv_gameHistory.setItemAnimator(new DefaultItemAnimator());
       rv_gameHistory.setHasFixedSize(true);
       gameHistoryAdapter = new GameHistoryAdapter(GameHistoryActivity.this,modelArrayListHistory);

       rv_gameHistory.setAdapter(gameHistoryAdapter);
   }

    private ArrayList<GameHistoryModel> arraygameHIstory(){
        ArrayList<GameHistoryModel> list = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            GameHistoryModel gameHistoryModel = new GameHistoryModel();
            gameHistoryModel.setDate(historydateList[i]);//by sneha
            gameHistoryModel.setTime(historyTimeList[i]);//by sneha
            gameHistoryModel.setAmount(historyAmountList[i]);//by sneha
            list.add(gameHistoryModel);
        }
        return list;
    }


    public void setStatusbarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.color_blue1));

        }

    }

}
