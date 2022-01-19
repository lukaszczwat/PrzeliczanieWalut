package com.example.przeliczaniewaluty;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ShowToast extends Activity {

    public void showToast(Context mContext, String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

}
