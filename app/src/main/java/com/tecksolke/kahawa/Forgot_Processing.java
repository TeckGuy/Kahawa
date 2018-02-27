package com.tecksolke.kahawa;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
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

/**
 * Created by techguy on 2/25/18.
 */

public class Forgot_Processing extends AsyncTask <String,Void,String>{

    HttpURLConnection httpURLConnection;
    Context context;

    //context constructor
    Forgot_Processing(Context ctx) {
        context = ctx;
    }

    //create a notification for user
    private void notifyUser() {
        //message to display
        String notifMessage = "Change Failed due to Internet Failure";
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
                        .setContentTitle("Password Change!")
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
        String login_url = env.urlChangePassword;
        try {
            URL url = new URL(login_url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("userID", "UTF-8") + "=" +
                    URLEncoder.encode(strings[0], "UTF-8") + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(strings[1], "UTF-8");
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
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ForgotPassword.breset.setText("PROCESSING...");
        ForgotPassword.breset.setEnabled(false);
        ForgotPassword.spinner.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            //get response here
            JSONObject object = new JSONObject(s);
            //
            JSONObject status = object.getJSONObject("response");
            String code = status.getString("code");
            String Message = status.getString("message");

            ForgotPassword.breset.setText("RESET");
            ForgotPassword.breset.setEnabled(true);
            ForgotPassword.spinner.setVisibility(View.GONE);
            ForgotPassword.Username.setText("");
            ForgotPassword.Password.setText("");
            ForgotPassword.confirmPasword.setText("");
            //dialog
            if(code.equalsIgnoreCase("200")){
                final NiftyDialogBuilder niftyDialogBuilder = NiftyDialogBuilder.getInstance(context);
                niftyDialogBuilder
                        .withTitle("Password Status")
                        .withTitleColor("#ffffff")
                        .withMessage(Message)
                        .withDialogColor("#2e7d32")
                        .withButton1Text("OK")
                        .withDuration(700)
                        .isCancelable(false)
                        .withEffect(Effectstype.Slidetop)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                niftyDialogBuilder.cancel();
                                //Direct tolGIN Activity
                                Intent intent = new Intent(context, Login.class);
                                context.startActivity(intent);
                                ((ForgotPassword) context).finish();
                            }
                        })
                        .show();
            }else{
                final NiftyDialogBuilder niftyDialogBuilder = NiftyDialogBuilder.getInstance(context);
                niftyDialogBuilder
                        .withTitle("Password Status")
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
