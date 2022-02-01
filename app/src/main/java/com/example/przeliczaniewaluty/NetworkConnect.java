package com.example.przeliczaniewaluty;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnect {

    private Context ctx;
    ShowToast show_toast = new ShowToast();
    public boolean Sprawdz(Context ctx){
        this.ctx = ctx;
        if (isNetworkConnected()){
            return true;
        }
        show_toast.showToast(this.ctx,"Brak Internetu - Sprawdz połączenie z internetem i spróbuj ponownie");
        return false;
    }

    private boolean isNetworkConnected() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
}
