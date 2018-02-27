package com.tecksolke.kahawa;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

public class CoopSector extends AppCompatActivity {

    TemporaryDB temporaryDB;
    NiftyDialogBuilder niftyDialogBuilder;
    public static ProgressBar spinner;
    static Button buttonbgnext, buttonpdnext, buttonsubmit, buttonprevious, bverify;
    ViewFlipper viewFlipper;

    EditText tgrowercode, tcoffeearea,
            tvarietymature1, tvarietyyoung1, tvarietymature2, tvarietyyoung2, tvarietymature3, tvarietyyoung3, tvarietymature4, tvarietyyoung4, tvarietymature5, tvarietyyoung5, tquantitycherry, tquantitymbuni, tratescherry, tratesmbuni, tpachment1, tpachment2, tpachment3, tpachment4, tpachmentL, ttotalcoffee, tfungicide, tinsecticide, therbicide, tfertilizer1, tfertilizer2, tfertilizer3, tfertilizer4, tnursery_A_quantity, tnursery_A_planted, tnursery_B_quantity, tnursery_B_planted;

    String growercode, coffeearea, varietymature1, varietyyoung1, varietymature2, varietyyoung2, varietymature3, varietyyoung3, varietymature4, varietyyoung4, varietymature5, varietyyoung5, quantitycherry, quantitymbuni, ratescherry, ratesmbuni, pachment1, pachment2, pachment3, pachment4, pachmentl, totalcoffee, fungicide, insecticide, herbicide, fertilizer1, fertilizer2, fertilizer3, fertilizer4, nursery_A_quantity, nursery_A_planted, nursery_B_quantity, nursery_B_planted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coop_sector);

        //import other vreated libraries to use
        temporaryDB = new TemporaryDB(this);
        niftyDialogBuilder = NiftyDialogBuilder.getInstance(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //calling back to home
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        //getting components
        tgrowercode = findViewById(R.id.coop_growercode);
        tcoffeearea = findViewById(R.id.coop_area);
        tvarietymature1 = findViewById(R.id.coop_mature_1);
        tvarietymature2 = findViewById(R.id.coop_mature_2);
        tvarietymature3 = findViewById(R.id.coop_mature_3);
        tvarietymature4 = findViewById(R.id.coop_mature_4);
        tvarietymature5 = findViewById(R.id.coop_mature_5);
        tvarietyyoung1 = findViewById(R.id.coop_young_1);
        tvarietyyoung2 = findViewById(R.id.coop_young_2);
        tvarietyyoung3 = findViewById(R.id.coop_young_3);
        tvarietyyoung4 = findViewById(R.id.coop_young_4);
        tvarietyyoung5 = findViewById(R.id.coop_young_5);
        tquantitycherry = findViewById(R.id.coop_cherry_quantity);
        tquantitymbuni = findViewById(R.id.coop_mbuni_quantity);
        tratescherry = findViewById(R.id.coop_cherry_rates);
        tratesmbuni = findViewById(R.id.coop_mbuni_rates);
        tpachment1 = findViewById(R.id.coop_patchment_p1);
        tpachment2 = findViewById(R.id.coop_patchment_p2);
        tpachment3 = findViewById(R.id.coop_patchment_p3);
        tpachment4 = findViewById(R.id.coop_patchment_p4);
        tpachmentL = findViewById(R.id.coop_patchment_pl);
        ttotalcoffee = findViewById(R.id.coop_clean_coffee);
        //another window
        tfungicide = findViewById(R.id.coop_Fungicide);
        tinsecticide = findViewById(R.id.coop_Insecticide);
        therbicide = findViewById(R.id.coop_Herbicide);
        tfertilizer1 = findViewById(R.id.coop_Fertilizer_1);
        tfertilizer2 = findViewById(R.id.coop_Fertilizer_2);
        tfertilizer3 = findViewById(R.id.coop_Fertilizer_3);
        tfertilizer4 = findViewById(R.id.coop_Fertilizer_4);
        tnursery_A_quantity = findViewById(R.id.coop_variety_sold_1);
        tnursery_A_planted = findViewById(R.id.coop_variety_planted_1);
        tnursery_B_quantity = findViewById(R.id.coop_variety_sold_2);
        tnursery_B_planted = findViewById(R.id.coop_variety_planted_2);


        //buttons and viewFlipper
        viewFlipper = findViewById(R.id.viewpoint);
        buttonbgnext = findViewById(R.id.btn_bg_next);
        buttonpdnext = findViewById(R.id.btn_pd_next);
        buttonprevious = findViewById(R.id.coop_previous);
        buttonsubmit = findViewById(R.id.coop_submit);
        bverify = findViewById(R.id.btn_coop_verify);
        spinner = findViewById(R.id.progressBarCoop);

        //progress bar
        spinner.setVisibility(View.GONE);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#0dd613"), PorterDuff.Mode.MULTIPLY);
        buttonbgnext.setVisibility(View.GONE);


        bverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tgrowercode.getText().toString().equalsIgnoreCase("")) {
                    tgrowercode.setError("Enter Grower Code");
                } else {
                    buttonbgnext.setVisibility(View.VISIBLE);
                }
            }
        });


        buttonbgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(CoopSector.this, "Production Data Section", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                spinner.setVisibility(View.VISIBLE);
                viewFlipper.showNext();
                spinner.setVisibility(View.GONE);
            }
        });
        buttonpdnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodGetData();
                String[] validateData = {coffeearea, varietymature1, varietyyoung1, varietymature2, varietyyoung2, varietymature3, varietyyoung3, varietymature4, varietyyoung4, varietymature5, varietyyoung5, quantitycherry, quantitymbuni, ratescherry, ratesmbuni, pachment1, pachment2, pachment3, pachment4, pachmentl, totalcoffee};

                EditText[] testEmpty = {
                        tgrowercode = findViewById(R.id.coop_growercode),
                        tcoffeearea = findViewById(R.id.coop_area),
                        tvarietymature1 = findViewById(R.id.coop_mature_1),
                        tvarietymature2 = findViewById(R.id.coop_mature_2),
                        tvarietymature3 = findViewById(R.id.coop_mature_3),
                        tvarietymature4 = findViewById(R.id.coop_mature_4),
                        tvarietymature5 = findViewById(R.id.coop_mature_5),
                        tvarietyyoung1 = findViewById(R.id.coop_young_1),
                        tvarietyyoung2 = findViewById(R.id.coop_young_2),
                        tvarietyyoung3 = findViewById(R.id.coop_young_3),
                        tvarietyyoung4 = findViewById(R.id.coop_young_4),
                        tvarietyyoung5 = findViewById(R.id.coop_young_5),
                        tquantitycherry = findViewById(R.id.coop_cherry_quantity),
                        tquantitymbuni = findViewById(R.id.coop_mbuni_quantity),
                        tratescherry = findViewById(R.id.coop_cherry_rates),
                        tratesmbuni = findViewById(R.id.coop_mbuni_rates),
                        tpachment1 = findViewById(R.id.coop_patchment_p1),
                        tpachment2 = findViewById(R.id.coop_patchment_p2),
                        tpachment3 = findViewById(R.id.coop_patchment_p3),
                        tpachment4 = findViewById(R.id.coop_patchment_p4),
                        tpachmentL = findViewById(R.id.coop_patchment_pl),
                        ttotalcoffee = findViewById(R.id.coop_clean_coffee),

                };

                int verified = 0;
                for (String avalidateData : validateData) {
                    if (avalidateData.equalsIgnoreCase("") || avalidateData.equalsIgnoreCase(null)) {
                        for (EditText aTestEmpty : testEmpty) {
                            if (aTestEmpty.getText().toString().equalsIgnoreCase("")) {
                                aTestEmpty.setError("Please Enter Data");
                            }
                        }
                        Snackbar.make(v, "All fields have to be filled", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        verified = 1;
                        break;
                    }
                }
                if (verified == 0) {
                    Toast toast = Toast.makeText(CoopSector.this, "Farm Input Section", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    spinner.setVisibility(View.VISIBLE);
                    viewFlipper.showNext();
                    spinner.setVisibility(View.GONE);
                }
            }
        });
        buttonprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(CoopSector.this, "Production Data Section", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                spinner.setVisibility(View.VISIBLE);
                viewFlipper.showPrevious();
                spinner.setVisibility(View.GONE);
            }
        });
    }

    //calling back to home
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //get values from the textile's
    public void methodGetData() {
        growercode = tgrowercode.getText().toString();
        coffeearea = tcoffeearea.getText().toString();
        varietymature1 = tvarietymature1.getText().toString();
        varietymature2 = tvarietymature2.getText().toString();
        varietymature3 = tvarietymature3.getText().toString();
        varietymature4 = tvarietymature4.getText().toString();
        varietymature5 = tvarietymature5.getText().toString();
        varietyyoung1 = tvarietyyoung1.getText().toString();
        varietyyoung2 = tvarietyyoung2.getText().toString();
        varietyyoung3 = tvarietyyoung3.getText().toString();
        varietyyoung4 = tvarietyyoung4.getText().toString();
        varietyyoung5 = tvarietyyoung5.getText().toString();
        quantitycherry = tquantitycherry.getText().toString();
        quantitymbuni = tquantitymbuni.getText().toString();
        ratescherry = tratescherry.getText().toString();
        ratesmbuni = tratesmbuni.getText().toString();
        pachment1 = tpachment1.getText().toString();
        pachment2 = tpachment2.getText().toString();
        pachment3 = tpachment3.getText().toString();
        pachment4 = tpachment4.getText().toString();
        pachmentl = tpachmentL.getText().toString();
        totalcoffee = ttotalcoffee.getText().toString();
        fungicide = tfungicide.getText().toString();
        insecticide = tinsecticide.getText().toString();
        herbicide = therbicide.getText().toString();
        fertilizer1 = tfertilizer1.getText().toString();
        fertilizer2 = tfertilizer2.getText().toString();
        fertilizer3 = tfertilizer3.getText().toString();
        fertilizer4 = tfertilizer4.getText().toString();
        nursery_A_quantity = tnursery_A_quantity.getText().toString();
        nursery_A_planted = tnursery_A_planted.getText().toString();
        nursery_B_quantity = tnursery_B_quantity.getText().toString();
        nursery_B_planted = tnursery_B_planted.getText().toString();
    }

    //process the data got
    public void coopDataProcessing(View view) {
        EditText[] testEmpty = {
                tfungicide = findViewById(R.id.coop_Fungicide),
                tinsecticide = findViewById(R.id.coop_Insecticide),
                therbicide = findViewById(R.id.coop_Herbicide),
                tfertilizer1 = findViewById(R.id.coop_Fertilizer_1),
                tfertilizer2 = findViewById(R.id.coop_Fertilizer_2),
                tfertilizer3 = findViewById(R.id.coop_Fertilizer_3),
                tfertilizer4 = findViewById(R.id.coop_Fertilizer_4),
                tnursery_A_quantity = findViewById(R.id.coop_variety_sold_1),
                tnursery_A_planted = findViewById(R.id.coop_variety_planted_1),
                tnursery_B_quantity = findViewById(R.id.coop_variety_sold_2),
                tnursery_B_planted = findViewById(R.id.coop_variety_planted_2),
        };

        //getting data from text fields
        methodGetData();
        String[] sendData = {growercode, coffeearea, varietymature1, varietyyoung1, varietymature2, varietyyoung2, varietymature3, varietyyoung3, varietymature4, varietyyoung4, varietymature5, varietyyoung5, quantitycherry, quantitymbuni, ratescherry, ratesmbuni, pachment1, pachment2, pachment3, pachment4, pachmentl, totalcoffee, fungicide, insecticide, herbicide, fertilizer1, fertilizer2, fertilizer3, fertilizer4, nursery_A_quantity, nursery_A_planted, nursery_B_quantity, nursery_B_planted};

        int verified = 0;
        for (String aSendData : sendData) {
            if (aSendData.equalsIgnoreCase("") || aSendData.equalsIgnoreCase(null)) {
                for (EditText aTestEmpty : testEmpty) {
                    if (aTestEmpty.getText().toString().equalsIgnoreCase("")) {
                        aTestEmpty.setError("Please Enter Data");
                    }
                }
                Snackbar.make(view, "All fields have to be filled", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                verified = 1;
                break;
            }
        }
        if (verified == 0) {
            niftyDialogBuilder
                    .withTitle("Confirm Submission")
                    .withTitleColor("#ffffff")
                    .withMessage("Are you sure you want to submit this data")
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
            // Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
            //if connection is true
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI || activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                methodGetData();
                //read data from mySQLite
                Cursor res = temporaryDB.getAllData();
                String userID = null;
                if (res != null && res.getCount() > 0) {
                    while (res.moveToNext()) {
                        userID = String.valueOf((res.getString(2)));
                    }
                }
                String[] sendData = {growercode, coffeearea, varietymature1, varietyyoung1, varietymature2, varietyyoung2, varietymature3, varietyyoung3, varietymature4, varietyyoung4, varietymature5, varietyyoung5, quantitycherry, quantitymbuni, ratescherry, ratesmbuni, pachment1, pachment2, pachment3, pachment4, pachmentl, totalcoffee, fungicide, insecticide, herbicide, fertilizer1, fertilizer2, fertilizer3, fertilizer4, nursery_A_quantity, nursery_A_planted, nursery_B_quantity, nursery_B_planted, userID};
                //submit data
                Coop_Processing coop = new Coop_Processing(this);
                coop.execute(sendData);
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
