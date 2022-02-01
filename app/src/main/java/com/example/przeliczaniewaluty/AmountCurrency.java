package com.example.przeliczaniewaluty;

import android.content.Context;
import android.widget.EditText;

public class AmountCurrency {
    ShowToast show_toast = new ShowToast();

    public Boolean Sprawdz(Context ctx, EditText kwotaWaluty){
        if (kwotaWaluty.getText().toString().isEmpty()){
            show_toast.showToast(ctx,"Wprowadź poprawną kwotę do konwersji");

            return true;
        }

        return false;

    }
}
