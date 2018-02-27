package com.tecksolke.kahawa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

public class Login extends AppCompatActivity {
    //mysqlite database class
    TemporaryDB db;

    NiftyDialogBuilder niftyDialogBuilder;

    static Button blogin,bforgot;
    EditText username, pass;
    public static ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        niftyDialogBuilder = NiftyDialogBuilder.getInstance(this);

        blogin = findViewById(R.id.btn_login);
        bforgot = findViewById(R.id.btn_forgot);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        spinner = findViewById(R.id.progressBarLogin);
        db = new TemporaryDB(this);

        //progress bar
        spinner.setVisibility(View.GONE);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#0dd613"), PorterDuff.Mode.MULTIPLY);

        bforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
                finish();
            }
        });
    }

    public void login(View view) {
        if (username.getText().toString().equalsIgnoreCase("")) {
            username.setError("Please Enter Username");
        } else if (pass.getText().toString().equalsIgnoreCase("")) {
            pass.setError("Please Enter Password");
        } else {
            checkConnection(this);
        }
    }
    //check for internet connection

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
                String userID = username.getText().toString();
                String password = pass.getText().toString();
                String type = "Login";
                //call instance of backgroundworker
                Login_Processing processdata = new Login_Processing(this);
                processdata.execute(type, userID, password);
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
