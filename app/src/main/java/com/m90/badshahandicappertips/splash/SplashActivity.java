package com.m90.badshahandicappertips.splash;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.m90.badshahandicappertips.R;
import com.m90.badshahandicappertips.home.Select_Nav_Menu;
import com.m90.badshahandicappertips.utils.SessionHelper;

import com.m90.badshahandicappertips.utils.PrefManager;
import com.m90.badshahandicappertips.utils.Utilities;
import com.m90.badshahandicappertips.welcome.WelcomeActivity;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    Handler handler;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    SessionHelper sessionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        prefManager = new PrefManager(this);
        progressDialog = new ProgressDialog(this);
        sessionHelper = new SessionHelper(this);

        animation();
    }

    public void animation() {
       // progressDialog.show();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sessionHelper.isLoggedIn()) {
                    Utilities.launchActivity(SplashActivity.this, Select_Nav_Menu.class, true);
                }
                else {
                    Utilities.launchActivity(SplashActivity.this, WelcomeActivity.class, true);
                }
          }
        }, 3000);
    }

}