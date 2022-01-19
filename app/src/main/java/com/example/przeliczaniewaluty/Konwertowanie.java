package com.example.przeliczaniewaluty;

import android.app.Activity;

import org.json.JSONObject;

public class Konwertowanie extends Activity {

    private String odpowiedz = null;
    private String toOutput, toCurrency, toRate;
    ShowToast show_toast = new ShowToast();
    public Konwertowanie(String odpowiedz, String toCurrency){
        this.odpowiedz = odpowiedz;
        this.toCurrency = toCurrency;
        Run();
    }
    public String getToOutput(){
        return toOutput;
    }

    public String getToRate(){
        return toRate;
    }


    private void Run(){
        try {
            JSONObject movieObject = new JSONObject(odpowiedz).getJSONObject("rates");
            String currency = (String) movieObject.getString(toCurrency);


            this.toOutput = new JSONObject(currency).getString("rate_for_amount");
            this.toRate = new JSONObject(currency).getString("rate");






        } catch (Exception e) {
            show_toast.showToast(getApplicationContext(),"Coś poszło źle w Konwertowaniu " + e.toString());
        }
    }

}
