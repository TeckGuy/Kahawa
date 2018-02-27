package com.tecksolke.kahawa;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

public class Splash extends AppCompatActivity {

    //calling sqlite data
    TemporaryDB temporaryDB;
    //get components
    TextView splash, tecksol;
    private ProgressBar spinner;
    NiftyDialogBuilder niftyDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //call this instance
        temporaryDB = new TemporaryDB(this);
        niftyDialogBuilder = NiftyDialogBuilder.getInstance(this);

        //call components
        splash = findViewById(R.id.splashtext);
        tecksol = findViewById(R.id.tecksolke);
        spinner = findViewById(R.id.progressBar);


        //set color to progressbar
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#0dd613"), android.graphics.PorterDuff.Mode.MULTIPLY);

        //create animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splashtranstion);
        //start animation with the variables
        splash.startAnimation(animation);
        tecksol.startAnimation(animation);
        spinner.startAnimation(animation);
//
        //start a new activity after splash screen
        final Intent intent = new Intent(this, Login.class);

        //start a thread to give a counter
        Thread timer = new Thread() {
            public void run() {
                try {
                    //give your delay timer here
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //do nothing...
                    startActivity(intent);
                    finish();
                }
            }
        };
        //start the timer
        timer.start();
        checkConnection(this);
        deleteData();
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
                //start a new activity after splash screen if condition is true
            }
        } else {
            niftyDialogBuilder
                    .withTitle("Network Status")
                    .withTitleColor("#ffffff")
                    .withMessage("You Are Offline")
                    .withDialogColor("#2e7d32")
                    .withButton1Text("OK")
                    .withDuration(700)
                    .withEffect(Effectstype.Fall)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            niftyDialogBuilder.cancel();
                        }
                    })
                    .show();
        }
    }

    //delete data from SQlite
    public void deleteData(){
        Cursor res = temporaryDB.getAllData();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                temporaryDB.deleteData(res.getString(0));
            }
        }
    }
}
