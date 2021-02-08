package com.m90.badshahandicappertips.home;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.m90.badshahandicappertips.MG.Fragment_NavHomeButton;
import com.m90.badshahandicappertips.MG.GameHistoryActivity;
import com.m90.badshahandicappertips.MG.WalletActivity;
import com.m90.badshahandicappertips.R;
import com.m90.badshahandicappertips.fragment.Frag_Nav_AboutUs;
import com.m90.badshahandicappertips.fragment.Frag_Nav_History;
import com.m90.badshahandicappertips.fragment.Frag_Nav_Home_Button;
import com.m90.badshahandicappertips.fragment.Frag_Nav_TnC;
import com.m90.badshahandicappertips.fragment.Frag_Nav_WalletHistory;
import com.m90.badshahandicappertips.utils.Utilities;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;
import java.util.zip.Inflater;

public class Select_Nav_Menu extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener,View.OnClickListener {

    private static final int POS_HOME = 0;
    private static final int POS_WALLET = 1;
    private static final int POS_HISTORY = 2;
    private static final int POS_ABOUT_US = 3;
    private static final int POS_TnC = 4;
    // private static final int NAV6 = 5;
    //private static final int NAV7 = 6;
    // Button Btn_logout;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav__home);

        setStatusbarColor();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)//true
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_WALLET),
                createItemFor(POS_HISTORY),
                createItemFor(POS_ABOUT_US),
                createItemFor(POS_TnC),
                // createItemFor(NAV6),
                new SpaceItem(5)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        adapter.setSelected(POS_HOME);


    }

    @Override
    public void onItemSelected(int position) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (position == POS_HOME) {
            getSupportActionBar().setTitle("Home");
            Fragment Home = new Fragment_NavHomeButton();
            fragmentTransaction.replace(R.id.Frame_container, Home);
            fragmentTransaction.commit();
        }

        else if (position == POS_WALLET) {
            getSupportActionBar().setTitle("WALLET HISTORY");

            Intent intent = new Intent(getApplicationContext(), WalletActivity.class);
            startActivity(intent);


         //   Fragment Home = new Frag_Nav_WalletHistory();
           // fragmentTransaction.replace(R.id.Frame_container, Home);
            //fragmentTransaction.commit();

        } else if (position == POS_HISTORY) {
            getSupportActionBar().setTitle("GAME HISTORY");
            Intent intent = new Intent(getApplicationContext(), GameHistoryActivity.class);
            startActivity(intent);

        } else if (position == POS_ABOUT_US) {
            getSupportActionBar().setTitle("ABOUT US");
            Fragment Home = new Frag_Nav_AboutUs();
            fragmentTransaction.replace(R.id.Frame_container, Home);
            fragmentTransaction.commit();

        } else if (position == POS_TnC) {
            getSupportActionBar().setTitle("TERMS AND CONDITIONS");
            Fragment Home = new Frag_Nav_TnC();
            fragmentTransaction.replace(R.id.Frame_container, Home);
            fragmentTransaction.commit();

        }

        slidingRootNav.closeMenu();
    }


    @SuppressWarnings("rawtypes")/////to set color
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.white))
                .withTextTint(color(R.color.white))
                .withSelectedIconTint(color(R.color.white))
                .withSelectedTextTint(color(R.color.white));//black
    }

    /////load tite
    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    /////array
    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcon);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    public void onBackPressed() {
        finish();
        moveTaskToBack(true);
    }

    //color
    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    public void setStatusbarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

            LayoutInflater inflater = getLayoutInflater();
            View tmpView;
            tmpView = inflater.inflate(R.layout.item_option, null);
            getWindow().addContentView(tmpView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));



        }

    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
    }


    @Override
    public void onClick(View v) {

    }


}