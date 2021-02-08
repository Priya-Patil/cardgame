package com.m90.badshahandicappertips.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.m90.badshahandicappertips.databinding.ActivityHomeBinding;
import com.m90.badshahandicappertips.R;
import com.m90.badshahandicappertips.retrofit.RetrofitClientInstance;
import com.m90.badshahandicappertips.utils.PrefManager;
import com.m90.badshahandicappertips.utils.Utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import datamodels.PWEStaticDataModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static datamodels.PWEStaticDataModel.TXN_BACKPRESSED_CODE;
import static datamodels.PWEStaticDataModel.TXN_BANK_BACK_PRESSED_CODE;
import static datamodels.PWEStaticDataModel.TXN_ERROR_SERVER_ERROR_CODE;
import static datamodels.PWEStaticDataModel.TXN_ERROR_TXN_NOT_ALLOWED_CODE;
import static datamodels.PWEStaticDataModel.TXN_TIMEOUT_CODE;
import static datamodels.PWEStaticDataModel.TXN_USERCANCELLED_CODE;

public class HomeActivity extends AppCompatActivity implements  View.OnClickListener{

    ActivityHomeBinding binding;
    // quiz
    private int[] myImageList = new int[]{R.drawable.logo1, R.drawable.logo1,R.drawable.logo1,R.drawable.logo1,R.drawable.logo1, R.drawable.logo1};

    private String[] myImageNameList = new String[]{"Add User","User History", "Event Message","Event History","Payment History", "Contact" };

    Activity activity;
    ProgressDialog progressDialog;
    PrefManager prefManager;

    String merchant_trxnId;
    Double merchant_payment_amount;
    String merchant_productInfo="Eureka" ;
    String customer_firstName ;
    String customer_email_id ;
    String customer_phone ;
    String merchant_key="03M4QISVM0";  //production
    // String merchant_key = "2PBP7IABZ2";
    String merchant_udf1 = "";
    String merchant_udf2 = "";
    String merchant_udf3 = "";
    String merchant_udf4 = "";
    String merchant_udf5 = "";
    String customer_address1 = "";
    String customer_address2 = "";
    String customer_city = "";
    String customer_state = "";
    String customer_country = "";
    String customer_zipcode = "";
    String salt="5NVGFAOE8Z"; //production
    // String salt = "DAH88E3UWQ";
    int customers_unique_id = 1;
    // String payment_mode = "test";
    String payment_mode="production";
    StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        activity=HomeActivity.this;
        progressDialog= new ProgressDialog(HomeActivity.this);
        prefManager= new PrefManager(HomeActivity.this);
        TrxnId();
        customer_firstName="priya";
        customer_email_id="priyapalaskar545@gmail.com";
        customer_phone=prefManager.getMobile();
        //customers_unique_id= Integer.parseInt(prefManager.getUserid());
        customers_unique_id= 10;
        binding.submit1.setOnClickListener(this);
        binding.txt1.setOnClickListener(this);
        binding.txt2.setOnClickListener(this);
        //checkIsUserPaid(Integer.parseInt(prefManager.getUserid()));
       // checkIsUserPaid(9);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;

           case R.id.submit1:
               merchant_payment_amount=1000.0;
               String hash = merchant_key + "|" + merchant_trxnId + "|" + merchant_payment_amount + "|" +
                       merchant_productInfo + "|"+ customer_firstName + "|" + customer_email_id + "|"
                       + merchant_udf1 + "|" + merchant_udf2 + "|" + merchant_udf3 + "|" + merchant_udf4 + "|"
                       + merchant_udf5 + "||||||" + salt + "|"+ merchant_key;
               Log.e( "hashchk: ",hash );
               hashgeneration(hash);
               payment(merchant_trxnId, merchant_payment_amount, merchant_productInfo,
                       customer_firstName,customer_email_id, customer_phone,
                       merchant_key, merchant_udf1, merchant_udf2,
                       merchant_udf3, merchant_udf4, merchant_udf5, customer_address1, customer_address2,
                       customer_city, customer_state,
                       customer_country, customer_zipcode, sb, customers_unique_id, payment_mode);
               break;

           case R.id.txt1:

               // Utility.launchActivity(HomeActivity.this, PrivacyActivity.class,false);
                break;

            case R.id.txt2:
                //Utility.launchActivity(HomeActivity.this, AboutActivity.class,false);
                break;


        }
    }
    private void showDialogNoRecord() {
        // this.correct = correct;
        final Dialog resultbox = new Dialog(this);
        resultbox.setContentView(R.layout.custom_dialog_home);
        resultbox.setCanceledOnTouchOutside(false);
        Button btn_finish = (Button) resultbox.findViewById(R.id.btn_finish);
        Button btn_cancel = (Button) resultbox.findViewById(R.id.btn_resume);

        btn_finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resultbox.cancel();
               // onBackPressed();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultbox.cancel();
            }
        });

        resultbox.show();
    }


    void payment(String merchant_trxnId, Double merchant_payment_amount, String merchant_productInfo,
                 String customer_firstName,
                 String customer_email_id, String customer_phone, String merchant_key, String merchant_udf1,
                 String merchant_udf2, String merchant_udf3, String merchant_udf4, String merchant_udf5,
                 String customer_address1, String customer_address2, String customer_city,
                 String customer_state, String customer_country, String customer_zipcode, StringBuilder sb,
                 int customers_unique_id, String payment_mode) {
        Intent intentProceed = new Intent(HomeActivity.this, PWECouponsActivity.class);
        intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // This is mandatory flag
        intentProceed.putExtra("txnid", merchant_trxnId);
        intentProceed.putExtra("amount", merchant_payment_amount);
        intentProceed.putExtra("productinfo", merchant_productInfo);
        intentProceed.putExtra("firstname", customer_firstName);
        intentProceed.putExtra("email", customer_email_id);
        intentProceed.putExtra("phone", customer_phone);
        intentProceed.putExtra("key", merchant_key);
        intentProceed.putExtra("udf1", merchant_udf1);
        intentProceed.putExtra("udf2", merchant_udf2);
        intentProceed.putExtra("udf3", merchant_udf3);
        intentProceed.putExtra("udf4", merchant_udf4);
        intentProceed.putExtra("udf5", merchant_udf5);
        intentProceed.putExtra("address1", customer_address1);
        intentProceed.putExtra("address2", customer_address2);
        intentProceed.putExtra("city", customer_city);
        intentProceed.putExtra("state", customer_state);
        intentProceed.putExtra("country", customer_country);
        intentProceed.putExtra("zipcode", customer_zipcode);
        intentProceed.putExtra("hash", sb.toString());
        intentProceed.putExtra("unique_id", customers_unique_id);
        intentProceed.putExtra("pay_mode", payment_mode);
        startActivityForResult(intentProceed, PWEStaticDataModel.PWE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == PWEStaticDataModel.PWE_REQUEST_CODE) {
                try {
                    String result = data.getStringExtra("result");
                    String payment_response = data.getStringExtra("payment_response");
                    if (result.contains(PWEStaticDataModel.TXN_SUCCESS_CODE)) {

                        Log.e( "merchant_payment: ", String.valueOf(merchant_payment_amount));
                        Log.e( "merchant_payment: ", String.valueOf(prefManager.getUserid()));
                       // addPayment(Integer.parseInt(prefManager.getUserid()), merchant_payment_amount);
//PWEStaticDataModel. TXN_SUCCESS_CODE is a string constant and its value is “ payment_successfull ”
//Code here will execute if the payment transaction completed successfully.
// here merchant can show the payment success message.
                    } else if (result.contains(TXN_TIMEOUT_CODE)) {

                        Log.e( "merchant_payment: ", TXN_TIMEOUT_CODE);

//PWEStaticDataModel. TXN_TIMEOUT_CODE is a string constant and its value is “ txn_session_timeout ”
//Code here will execute if the payment transaction failed because of the transaction time out.
// here merchant can show the payment failed message.
                    } else if (result.contains(TXN_BACKPRESSED_CODE)) {
                        Log.e( "merchant_payment: ", TXN_BACKPRESSED_CODE);

//PWEStaticDataModel. TXN_BACKPRESSED_CODE is a string constant and its value is “ back_pressed ”
//Code here will execute if the user pressed the back button on coupons Activity.
// here merchant can show the payment failed message.
                    } else if (result.contains(TXN_USERCANCELLED_CODE)) {
                        Log.e( "merchant_payment: ", TXN_USERCANCELLED_CODE);

//PWEStaticDataModel. TXN_USERCANCELLED_CODE is a string constant and its value is “ user_cancelled ”
//Code here will execute if the the user pressed the cancel button during the payment process.
// here merchant can show the payment failed message.
                    } else if (result.contains(TXN_ERROR_SERVER_ERROR_CODE)) {
                        Log.e( "merchant_payment: ", TXN_ERROR_SERVER_ERROR_CODE);

//PWEStaticDataModel. TXN_ERROR_SERVER_ERROR_CODE is a string constant and its value  is “error_server_error ”
//Code here will execute if the server side error occured during payment process.
// here merchant can show the payment failed message.
                    } else if (result.contains(TXN_ERROR_TXN_NOT_ALLOWED_CODE)) {
                        Log.e( "merchant_payment: ", TXN_ERROR_TXN_NOT_ALLOWED_CODE);

//PWEStaticDataModel. TXN_ERROR_TXN_NOT_ALLOWED_CODE is a string constant and its value is trxn_not_allowed ”
//Code here will execute if the the transaction is not allowed.
// here merchant can show the payment failed message.
                    } else if (result.contains(TXN_BANK_BACK_PRESSED_CODE)) {
                        Log.e( "merchant_payment: ", TXN_BANK_BACK_PRESSED_CODE);

//PWEStaticDataModel. TXN_BANK_BACK_PRESSED_CODE is a string constant and its value is “bank_back_pressed”
//Code here will execute if the the customer press the back button on bank screen.
// here merchant can show the payment failed message.
                    } else {
                        Log.e( "merchant_payment: ", "else");

// Here the value of result is “ payment_failed ” or “ error_noretry ” or “ retry_fail_error ”
//Code here will execute if payment is failed some other reasons.
// here merchant can show the payment failed message.
                    }
                } catch (Exception e) {
                    Log.e( "merchant_payment: ", "error"+e.getMessage());

//Handle exceptions here
                }
            }
        }
    }

    void TrxnId() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        merchant_trxnId = ft.format(dNow);
        Log.e("TrxnId: ", merchant_trxnId);
    }


    void hashgeneration(String hash1) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = md.digest(hash1.getBytes());
        sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        Log.e("hashgeneration: ", sb.toString());
    }


    public void addFees(final String user_id, final String amount, String transaction_id){

        if (Utilities.isNetworkAvailable(this)) {

        }
        else {
            Toast.makeText(this, getResources().getString(R.string.checkinternaet), Toast.LENGTH_SHORT).show();

        }
    }


}