package com.m90.badshahandicappertips.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.m90.badshahandicappertips.MG.FourCardActivity;
import com.m90.badshahandicappertips.R;
import com.m90.badshahandicappertips.fragment.FourCardHomeActivity;
import com.m90.badshahandicappertips.fragment.Frag_Nav_Home13_cards;
import com.m90.badshahandicappertips.utils.SessionHelper;
import com.m90.badshahandicappertips.utils.Utilities;

public class HomeButtonsActivity extends AppCompatActivity {

    Button btnSlideUp;
    Animation animSlideUp;
    CardView slidecard;
    TextView txtSlideUp,btn_fourCards,btn_thirteenCards;

    LottieAnimationView lottieAnimationView;

    Activity activity;
    SessionHelper sessionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_button);


        activity = HomeButtonsActivity.this;
        sessionHelper = new SessionHelper(activity);
        View slideView =findViewById(R.id.slideupView);

        btnSlideUp = (Button) findViewById(R.id.btnSlideUp);
        txtSlideUp=(TextView)findViewById(R.id.txt_slide_up);

        btn_fourCards =(TextView) findViewById(R.id.btn_fourCards);
        btn_thirteenCards =(TextView) findViewById(R.id.btn_thirteenCards);

        btn_thirteenCards =(TextView) findViewById(R.id.btn_thirteenCards);

        lottieAnimationView =findViewById(R.id.lottieAnimationView);
        lottieAnimationView.playAnimation();

        // lottieAnimationView.animate().translationY(1400).setDuration(500000).setStartDelay(4000);

        animSlideUp = AnimationUtils.loadAnimation(activity.getApplicationContext(),
                R.anim.slide_up);
        slideView.startAnimation(animSlideUp);

        final MediaPlayer mp = MediaPlayer.create(activity, R.raw.sample);
        btn_fourCards.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               Utilities.launchActivity(activity, FourCardActivity.class,true);

               // Intent intent = new Intent(this,FourCardActivity.class);
                //startActivity(intent);

            }
        });

        btn_thirteenCards.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mp.start();
                //FragmentManager fm = getSupportFragmentManager();

            }
        });

        // animSlideUp = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
        //       R.anim.slide_up);
        // Slide Up
        btnSlideUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  slideView.startAnimation(animSlideUp);
                //  txtSlideUp.startAnimation(animSlideUp);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}