package com.example.przeliczaniewaluty;

import android.app.Activity;
import android.content.Context;

import org.json.JSONObject;


public class Konwertowanie{

    final private String odpowiedz;
    final private String toCurrency;
    private String toOutput, toRate;
    final Context ctx;
    ShowToast show_toast = new ShowToast();
    public Konwertowanie(String odpowiedz, String toCurrency, Context ctx){
        this.ctx = ctx;
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
            show_toast.showToast(this.ctx,"Coś poszło źle w Konwertowaniu " + e.toString());
        }
    }

}
