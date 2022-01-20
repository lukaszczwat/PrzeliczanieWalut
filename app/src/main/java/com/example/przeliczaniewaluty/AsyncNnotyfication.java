package com.example.przeliczaniewaluty;

import android.app.Notification;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AsyncNnotyfication extends AsyncTask<Object, Void, Void> {
    MainActivity activity;
    NotyficationShow notyficationShow;
    Notification.Builder nb;
    String error;

    public AsyncNnotyfication(MainActivity activity){
        this.activity = activity;
    }
    @Override
    protected Void doInBackground(Object... params) {
        int numerNotyfication = (int)params[0];
        String valueNotyfication = (String)params[1];
        DataTime dataTime = new DataTime(new Date());
        int x = 1;

        try {
            do{
                if(dataTime.DodanieCzasu(new Date(), x)) {
                    notyficationShow = new NotyficationShow(activity);
                    nb = notyficationShow.
                            getAndroidChannelNotification("Przelicz Walutę", valueNotyfication);

                    notyficationShow.getManager().notify(numerNotyfication, nb.build());
                }
            }while (!dataTime.DodanieCzasu(new Date(), x));


        } catch (Exception e) {
            error = e.toString();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void v) {

        try {


        } catch (Exception e) {
            error = e.toString();
            //show_toast.showToast(activity,"Coś poszło źle w Konwertowaniu " + error);
        }

        super.onPostExecute(v);
    }

}
