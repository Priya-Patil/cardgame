package com.m90.badshahandicappertips.fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.m90.badshahandicappertips.R;
import com.m90.badshahandicappertips.card.FourCardActivity;
import com.m90.badshahandicappertips.utils.Utilities;


///   avtivity for select button
public class Fragment_NavHomeButton extends Fragment {

    View root;

    Button btnSlideUp;
    Animation animSlideUp;
    CardView slidecard;
    TextView txtSlideUp,btn_fourCards,btn_thirteenCards;

    LottieAnimationView lottieAnimationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_home_button, container, false);

        View slideView =root.findViewById(R.id.slideupView);

        btnSlideUp = (Button) root.findViewById(R.id.btnSlideUp);
        txtSlideUp=(TextView)root.findViewById(R.id.txt_slide_up);

        btn_fourCards =(TextView) root.findViewById(R.id.btn_fourCards);
        btn_thirteenCards =(TextView) root.findViewById(R.id.btn_thirteenCards);

        btn_thirteenCards =(TextView) root.findViewById(R.id.btn_thirteenCards);

        lottieAnimationView =root.findViewById(R.id.lottieAnimationView);
        lottieAnimationView.playAnimation();

       // lottieAnimationView.animate().translationY(1400).setDuration(500000).setStartDelay(4000);

        animSlideUp = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.slide_up);
        slideView.startAnimation(animSlideUp);

        final MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.sample);
        btn_fourCards.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mp.start();
               /* Frag_Nav_Home13_cards fragment2 = new Frag_Nav_Home13_cards();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Frame_container, fragment2);
                fragmentTransaction.commit();*/

                Bundle bundle =  new Bundle();
                bundle.putString("FourORThirteenCards","FourCard");
                Utilities.launchActivity(getActivity(),FourCardActivity.class,false,bundle);

            }
        });

        btn_thirteenCards.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mp.start();
                Bundle bundle =  new Bundle();
                bundle.putString("FourORThirteenCards","ThirteenCard");
                Utilities.launchActivity(getActivity(),FourCardActivity.class,false,bundle);
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

        return root;
    }


}
