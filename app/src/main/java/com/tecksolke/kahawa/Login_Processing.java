package com.tecksolke.kahawa;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by techguy on 2/10/18.
 */

public class Login_Processing extends AsyncTask<String, Void, String> {

    //calling database class temporary
    TemporaryDB myDB;

    HttpURLConnection httpURLConnection;
    Context context;

    Login_Processing(Context ctx) {
        context = ctx;
    }

    //create a notification for user
    private void notifyUser() {
        //message to display
        String notifMessage = "Login Failed due to Internet Failure";
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
                        .setContentTitle("Login Status!")
                        .setContentIntent(pendingIntent)
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
        String param = strings[0];
        String login_url = env.urlLogin;
        if (param.equalsIgnoreCase("Login")) {
            String userID = strings[1];
            String password = strings[2];
            try {
                URL url = new URL(login_url);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("userID", "UTF-8") + "=" +
                        URLEncoder.encode(userID, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                ///response from post requets
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
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Login.blogin.setText("PROCESSING...");
        Login.blogin.setEnabled(false);
        Login.spinner.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            //json convertion
            JSONObject object = new JSONObject(s);

            //check login response
            JSONObject status = object.getJSONObject("response");
            String code = status.getString("code");
            String Message = status.getString("message");

            //check object user
            JSONObject foundResponse = object.getJSONObject("user");
            String ID = foundResponse.getString("id");
            String name = foundResponse.getString("name");
            String uID = foundResponse.getString("userID");
            String region = foundResponse.getString("region");

            //get this data from analyzed data
            JSONObject dataServer = object.getJSONObject("data");
            String coopdata = dataServer.getString("coopServed");
            String estatedata = dataServer.getString("estateServed");
            String totaldata = dataServer.getString("total");


            if (code.equalsIgnoreCase("200")) {
                Login.blogin.setText("LOGIN");
                Login.blogin.setEnabled(true);
                Login.spinner.setVisibility(View.GONE);

                //getting results and insert into Sqlite after deleting previous data
                myDB = new TemporaryDB(context);
                Boolean dbResults = myDB.insertData(name, uID, region, totaldata, estatedata, coopdata);
                if (dbResults) {
                    Log.v(TAG, "save ok.");
                } else {
                    Log.v(TAG, "Failed.");
                }

                //fetch data on update data
                myDB = new TemporaryDB(context);
                Boolean dbupdate = myDB.updateData(name, uID, region, totaldata, estatedata, coopdata);
                if (dbupdate) {
                    Log.v(TAG, "save ok.");
                } else {
                    Log.v(TAG, "Failed.");
                }

                //Direct to Kahawa Activity
                Intent intent = new Intent(context, Kahawa.class);
                context.startActivity(intent);
                ((Login) context).finish();
            }
            if (code.equalsIgnoreCase("401")) {
                Login.blogin.setText("LOGIN");
                Login.blogin.setEnabled(true);
                Login.spinner.setVisibility(View.GONE);
                //dialog notification
                final NiftyDialogBuilder niftyDialogBuilder = NiftyDialogBuilder.getInstance(context);
                niftyDialogBuilder
                        .withTitle("Login Status")
                        .withTitleColor("#ffffff")
                        .withMessage(Message)
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
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}