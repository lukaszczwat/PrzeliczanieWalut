package com.example.przeliczaniewaluty;

import android.content.Context;
import android.widget.Spinner;

public class CheckCurrency {



    ShowToast show_toast = new ShowToast();


    public boolean Sprawdz(Context ctx, Spinner fromWaluta, Spinner toWaluta){

        if (toWaluta == fromWaluta){

            show_toast.showToast(ctx,"Zmień walutę do konwersji");
        }
        else {
            return false;
        }

        return true;
    }



}
