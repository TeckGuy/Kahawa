package com.tecksolke.kahawa;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.io.BufferedReader;
import java.nio.channels.ByteChannel;

public class ChangePassword extends AppCompatActivity {

    static Button bchangepassword;
    static EditText Username, Password, confirmPasword;
    public static ProgressBar spinner;
    NiftyDialogBuilder niftyDialogBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        niftyDialogBuilder = NiftyDialogBuilder.getInstance(this);

        //calling back to home
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        bchangepassword = findViewById(R.id.btn_changePassword);
        Username = findViewById(R.id.usernameChangePassword);
        Password = findViewById(R.id.changePassword);
        confirmPasword = findViewById(R.id.confirmChange);
        spinner = findViewById(R.id.progressBarChange);


        //set color to progressbar
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#0dd613"), android.graphics.PorterDuff.Mode.MULTIPLY);
        spinner.setVisibility(View.GONE);


    }

    //calling back to home
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //method for change password
    public void passwordCahnge(View view) {
        if(Username.getText().toString().equalsIgnoreCase("") || Password.getText().toString().equalsIgnoreCase("") || confirmPasword.getText().toString().equalsIgnoreCase("")){
            Username.setError("Enter Username");
            Password.setError("Enter Password");
            confirmPasword.setError("Confirm Password");
        }else if(!Password.getText().toString().equalsIgnoreCase(confirmPasword.getText().toString())){
            niftyDialogBuilder
                    .withTitle("Validation")
                    .withTitleColor("#ffffff")
                    .withMessage("Passwords Don't Match")
                    .withDialogColor("#2e7d32")
                    .withButton1Text("OK")
                    .withDuration(700)
                    .isCancelable(false)
                    .withEffect(Effectstype.RotateBottom)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            niftyDialogBuilder.cancel();
                        }
                    })
                    .show();
        }else{
            niftyDialogBuilder
                    .withTitle("Password Confirmation")
                    .withTitleColor("#ffffff")
                    .withMessage("Are you sure you want to change your current password")
                    .withDialogColor("#2e7d32")
                    .withButton1Text("OK")
                    .withButton2Text("Cancel")
                    .withDuration(700)
                    .isCancelable(false)
                    .withEffect(Effectstype.Flipv)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            niftyDialogBuilder.cancel();
                            confirmData();
                        }
                    })
                    .setButton2Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            niftyDialogBuilder.cancel();
                        }
                    })
                    .show();
        }
    }
    //call method checkConnection here
    public void confirmData(){
        checkConnection(this);
    }


    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     */
    public void checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            //Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            //if connection is true
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI || activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String username = Username.getText().toString();
                String pass = Password.getText().toString();
                String[]details = {username,pass};

                //CALL CLASS PROCESSING OF PASSWORD
                PasswordProcessing passwordProcessing = new PasswordProcessing(this);
                passwordProcessing.execute(details);
            }
        } else {
            niftyDialogBuilder
                    .withTitle("Network Status")
                    .withTitleColor("#ffffff")
                    .withMessage("Please Connect To A Network To Proceed")
                    .withDialogColor("#2e7d32")
                    .withButton1Text("OK")
                    .withDuration(700)
                    .isCancelable(false)
                    .withEffect(Effectstype.Shake)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            niftyDialogBuilder.cancel();
                        }
                    })
                    .show();
        }
    }
}
