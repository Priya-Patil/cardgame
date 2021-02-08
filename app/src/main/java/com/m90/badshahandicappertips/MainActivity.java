package com.m90.badshahandicappertips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import android.os.Bundle;

import com.instamojo.android.Instamojo;
import com.instamojo.android.helpers.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity   {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final HashMap<Instamojo.Environment, String> env_options = new HashMap<>();

    static {
        env_options.put(Instamojo.Environment.TEST, "https://test.instamojo.com/");
        env_options.put(Instamojo.Environment.PRODUCTION, "https://api.instamojo.com/");
    }

    private AlertDialog dialog;
    private EditText nameBox, emailBox, phoneBox, amountBox, descriptionBox;
    private Instamojo.Environment mCurrentEnv = Instamojo.Environment.TEST;
    private boolean mCustomUIFlow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


}
