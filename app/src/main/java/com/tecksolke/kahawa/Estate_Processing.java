package com.tecksolke.kahawa;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static android.content.ContentValues.TAG;

/**
 * Created by techguy on 2/14/18.
 */

public class Estate_Processing extends AsyncTask<String, Void, String> {

    TemporaryDB temporaryDB;
    HttpURLConnection httpURLConnection;
    Context context;

    Estate_Processing(Context ctx) {
        context = ctx;
    }

    //create a notification for user
    private void notifyUser() {
        //message to display
        String notifMessage = "Submission Failed due to Internet Failure";
        //build a return activity
        Intent myIntent = new Intent(context, Login.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        //build a notification to the user
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.coffee)
                        .setContentTitle("Estate Data Submission!")
                        .setAutoCancel(true)
                        .setWhen(0)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notifMessage))
                        .setColor(Color.parseColor("#2e7d32"))
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setContentText(notifMessage);
        //get an instance of the notification service
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //post the notification to the bar
        notificationManager.notify(0, mBuilder.build());
    }

    @Override
    protected String doInBackground(String... strings) {
        String login_url = env.urlestate;
        try {
            URL url = new URL(login_url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(
                    URLEncoder.encode("growerCode", "UTF-8") + "=" +
                            URLEncoder.encode(strings[0], "UTF-8") + "&" +
                            URLEncoder.encode("coffeeHa", "UTF-8") + "=" +
                            URLEncoder.encode(strings[1], "UTF-8") + "&" +
                            URLEncoder.encode("coffeeOneMature", "UTF-8") + "=" +
                            URLEncoder.encode(strings[2], "UTF-8") + "&" +
                            URLEncoder.encode("coffeeOneYoung", "UTF-8") + "=" +
                            URLEncoder.encode(strings[3], "UTF-8") + "&" +
                            URLEncoder.encode("coffeeTwoMature", "UTF-8") + "=" +
                            URLEncoder.encode(strings[4], "UTF-8") + "&" +
                            URLEncoder.encode("coffeeTwoYoung", "UTF-8") + "=" +
                            URLEncoder.encode(strings[5], "UTF-8") + "&" +
                            URLEncoder.encode("coffeeThreeMature", "UTF-8") + "=" +
                            URLEncoder.encode(strings[6], "UTF-8") + "&" +
                            URLEncoder.encode("coffeeThreeYoung", "UTF-8") + "=" +
                            URLEncoder.encode(strings[7], "UTF-8") + "&" +
                            URLEncoder.encode("coffeeFourMature", "UTF-8") + "=" +
                            URLEncoder.encode(strings[8], "UTF-8") + "&" +
                            URLEncoder.encode("coffeeFourYoung", "UTF-8") + "=" +
                            URLEncoder.encode(strings[9], "UTF-8") + "&" +
                            URLEncoder.encode("coffeeFiveMature", "UTF-8") + "=" +
                            URLEncoder.encode(strings[10], "UTF-8") + "&" +
                            URLEncoder.encode("coffeeFiveYoung", "UTF-8") + "=" +
                            URLEncoder.encode(strings[11], "UTF-8") + "&" +
                            URLEncoder.encode("cherryQuantity", "UTF-8") + "=" +
                            URLEncoder.encode(strings[12], "UTF-8") + "&" +
                            URLEncoder.encode("mbuniQuantity", "UTF-8") + "=" +
                            URLEncoder.encode(strings[13], "UTF-8") + "&" +
                            URLEncoder.encode("cherryRate", "UTF-8") + "=" +
                            URLEncoder.encode(strings[14], "UTF-8") + "&" +
                            URLEncoder.encode("mbuniRate", "UTF-8") + "=" +
                            URLEncoder.encode(strings[15], "UTF-8") + "&" +
                            URLEncoder.encode("parchmentOne", "UTF-8") + "=" +
                            URLEncoder.encode(strings[16], "UTF-8") + "&" +
                            URLEncoder.encode("parchmentTwo", "UTF-8") + "=" +
                            URLEncoder.encode(strings[17], "UTF-8") + "&" +
                            URLEncoder.encode("parchmentThree", "UTF-8") + "=" +
                            URLEncoder.encode(strings[18], "UTF-8") + "&" +
                            URLEncoder.encode("parchmentFour", "UTF-8") + "=" +
                            URLEncoder.encode(strings[19], "UTF-8") + "&" +
                            URLEncoder.encode("parchmentL", "UTF-8") + "=" +
                            URLEncoder.encode(strings[20], "UTF-8") + "&" +
                            URLEncoder.encode("totalCleanCoffee", "UTF-8") + "=" +
                            URLEncoder.encode(strings[21], "UTF-8") + "&" +
                            URLEncoder.encode("fungicide", "UTF-8") + "=" +
                            URLEncoder.encode(strings[22], "UTF-8") + "&" +
                            URLEncoder.encode("insecticide", "UTF-8") + "=" +
                            URLEncoder.encode(strings[23], "UTF-8") + "&" +
                            URLEncoder.encode("herbicide", "UTF-8") + "=" +
                            URLEncoder.encode(strings[24], "UTF-8") + "&" +
                            URLEncoder.encode("fertilizerOne", "UTF-8") + "=" +
                            URLEncoder.encode(strings[25], "UTF-8") + "&" +
                            URLEncoder.encode("fertilizerTwo", "UTF-8") + "=" +
                            URLEncoder.encode(strings[26], "UTF-8") + "&" +
                            URLEncoder.encode("fertilizerThree", "UTF-8") + "=" +
                            URLEncoder.encode(strings[27], "UTF-8") + "&" +
                            URLEncoder.encode("fertilizerFour", "UTF-8") + "=" +
                            URLEncoder.encode(strings[28], "UTF-8") + "&" +
                            URLEncoder.encode("quantitySoldNurseryOne", "UTF-8") + "=" +
                            URLEncoder.encode(strings[29], "UTF-8") + "&" +
                            URLEncoder.encode("plantedNurseryOne", "UTF-8") + "=" +
                            URLEncoder.encode(strings[30], "UTF-8") + "&" +
                            URLEncoder.encode("quantitySoldNurseryTwo", "UTF-8") + "=" +
                            URLEncoder.encode(strings[31], "UTF-8") + "&" +
                            URLEncoder.encode("plantedNurseryTwo", "UTF-8") + "=" +
                            URLEncoder.encode(strings[32], "UTF-8") + "&" +
                            URLEncoder.encode("userID", "UTF-8") + "=" +
                            URLEncoder.encode(strings[33], "UTF-8")
            );
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            ///response from post request that comes from other end.
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "", line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

            //JSONObject object = new JSONObject(result);

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;
        } catch (MalformedURLException e) {
            notifyUser();
        } catch (IOException e) {
            notifyUser();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        EstateSector.buttonsubmit.setText("PROCESSING...");
        EstateSector.buttonsubmit.setEnabled(false);
        EstateSector.buttonprevious.setEnabled(false);
        EstateSector.spinner.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        EstateSector.buttonsubmit.setText("SUBMIT");
        EstateSector.buttonsubmit.setEnabled(true);
        EstateSector.buttonprevious.setEnabled(true);
        EstateSector.spinner.setVisibility(View.GONE);
        //dialog
        final NiftyDialogBuilder niftyDialogBuilder = NiftyDialogBuilder.getInstance(context);
        niftyDialogBuilder
                .withTitle("Estate Response")
                .withTitleColor("#ffffff")
                .withMessage(s)
                .withDialogColor("#2e7d32")
                .withButton1Text("OK")
                .withDuration(700)
                .isCancelable(false)
                .withEffect(Effectstype.Slidetop)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        niftyDialogBuilder.cancel();
                        //fetch userID from mySQLite
//                        Cursor res = temporaryDB.getAllData();
//                        if (res != null && res.getCount() > 0) {
//                            String userID = null;
//                            while (res.moveToNext()) {
//                                userID = String.valueOf((res.getString(2)));
//                            }
//                            //update data
//                            temporaryDB = new TemporaryDB(context);
//                            Boolean dbupdate = temporaryDB.updateData(userID,totaldata, estatedata, coopdata);
//                            if (dbupdate) {
//                                Log.v(TAG, "save ok.");
//                            } else {
//                                Log.v(TAG, "Failed.");
//                            }
//                        }
                        //Direct to Kahawa Activity
                        Intent intent = new Intent(context, Kahawa.class);
                        context.startActivity(intent);
                        ((EstateSector) context).finish();
                    }
                })
                .show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
