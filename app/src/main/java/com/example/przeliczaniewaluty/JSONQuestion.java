package com.example.przeliczaniewaluty;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JSONQuestion extends AsyncTask<String, Void, String> {
    final private ProgressDialog dialog;

    public JSONQuestion(MainActivity activity) {

        dialog = new ProgressDialog(activity);
    }

    private Exception mError;
    private String answer = null;
    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(params[0])
                .get()
                .addHeader("x-rapidapi-host", "currency-converter5.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "c341a9bf54msh3f470a0bcd1555dp1f7b3ajsn7276c464ad58")
                .build();
        try {
            Response response = client.newCall(request).execute();
            answer = Objects.requireNonNull(response.body()).string();
        } catch (Exception e) {
            setmError(e);
        }
        return answer;
    }


    @Override
    protected void onPreExecute() {
        dialog.setMessage("Pobieranie waluty...");
        dialog.show();
        super.onPreExecute();
    }



    @Override
    protected void onPostExecute(String answer) {
        dialog.dismiss();
        super.onPostExecute(answer);





    }

    public Exception getmError() {
        return mError;
    }

    public void setmError(Exception mError) {
        this.mError = mError;
    }
}
