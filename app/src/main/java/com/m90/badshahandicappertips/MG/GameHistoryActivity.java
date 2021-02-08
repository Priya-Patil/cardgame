package com.m90.badshahandicappertips.MG;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m90.badshahandicappertips.MG.Adapters.CardThirteenAdapter;
import com.m90.badshahandicappertips.MG.Adapters.FourCardAdapter;
import com.m90.badshahandicappertips.MG.Adapters.GameHistoryAdapter;
import com.m90.badshahandicappertips.MG.Models.FourCardModel;
import com.m90.badshahandicappertips.MG.Models.GameHistoryModel;
import com.m90.badshahandicappertips.R;

import java.util.ArrayList;

public class GameHistoryActivity extends AppCompatActivity {

    private String[] historydateList = new String[]{"11/2/2021", "12/2/2021","13/2/2021",
            "14/2/2021","15/2/2021"};
    private String[] historyTimeList = new String[]{"11.30 am","12.30 pm","11.30 am","1.30 pm","2.30 am"};
    private String[] historyAmountList = new String[]{"1000","2000","3000","4000","5000"};


 //   ProgressDialog progressDialog;
   // RecyclerView rv_recycler,rv_cardThirtreen;
    //TextView tv_total;
    RecyclerView rv_gameHistory;

    GameHistoryAdapter gameHistoryAdapter;
    ArrayList<GameHistoryModel> modelArrayListHistory ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamehistory);
        setStatusbarColor();

        rv_gameHistory=findViewById(R.id.rv_gameHistory);



        setGameHistory();

    }



    void setGameHistory()
    {
   //     progressDialog.setMessage("Please Wait...");
     //   progressDialog.setCancelable(false);
       // progressDialog.show();
        modelArrayListHistory = arraygameHIstory();

        rv_gameHistory.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rv_gameHistory.setItemAnimator(new DefaultItemAnimator());
        rv_gameHistory.setHasFixedSize(true);
        gameHistoryAdapter = new GameHistoryAdapter(GameHistoryActivity.this,modelArrayListHistory);

        rv_gameHistory.setAdapter(gameHistoryAdapter);
       // progressDialog.dismiss();
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
