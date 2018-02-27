package com.tecksolke.kahawa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

public class ForgotPassword extends AppCompatActivity {


    NiftyDialogBuilder niftyDialogBuilder;

    static Button bback,breset;
    static EditText Username, Password, confirmPasword;
    public static ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        niftyDialogBuilder = NiftyDialogBuilder.getInstance(this);

        bback = findViewById(R.id.btn_back);
        breset = findViewById(R.id.btn_Reset);
        Username = findViewById(R.id.usernameReset);
        Password = findViewById(R.id.passwordReset);
        confirmPasword = findViewById(R.id.confirmpassReset);
        spinner = findViewById(R.id.progressBarReset);


        //set color to progressbar
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#0dd613"), android.graphics.PorterDuff.Mode.MULTIPLY);
        spinner.setVisibility(View.GONE);


        //back to login page
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this,Login.class));
                finish();
            }
        });

    }

    //process password
    public void resetPassword(View view){
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
            checkConnection(this);
        }
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
               Forgot_Processing forgot_processing = new Forgot_Processing(this);
               forgot_processing.execute(details);
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
