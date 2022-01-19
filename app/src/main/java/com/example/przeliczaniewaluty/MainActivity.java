package com.example.przeliczaniewaluty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText kwotaWaluty, wartoscPoPrzeliczeniu;
    Button convertButton;
    Spinner fromWaluta, toWaluta;
    String fromWartosc, toWartosc;
    String[] toApiCurrecy;
    TextView toRate;
    ShowToast show_toast = new ShowToast();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kwotaWaluty = findViewById(R.id.kwotaWaluty);
        wartoscPoPrzeliczeniu = findViewById(R.id.toWartoscPoPrzeliczeniu);
        wartoscPoPrzeliczeniu.setEnabled(false);
        toRate = findViewById(R.id.toRate);
        toRate.setEnabled(false);
        convertButton = findViewById(R.id.convertButton);
        fromWaluta = findViewById(R.id.pierwszaWaluta);
        toWaluta = findViewById(R.id.drugaWaluta);
        String[] from = {"EUR"};
        String[] to = {"USD"};
        ArrayAdapter<String> adapterFrom = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, from);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapterTo = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, to);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromWaluta.setAdapter(adapterFrom);
        toWaluta.setAdapter(adapterTo);

        if (isNetworkConnected()){
            //TODO: programowanie pobierania wszystkich walut
        }
        else{
            show_toast.showToast(getApplicationContext(),"Brak Internetu - Sprawdź połączenie i spróbuj ponownie");
        }

        Setup();

    }

    private void Setup() {

        fromWaluta.setOnItemSelectedListener(new FromDropdown());
        toWaluta.setOnItemSelectedListener(new ToDropdown());
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isNetworkConnected()) {
                    if (fromWaluta == toWaluta) {
                        show_toast.showToast(getApplicationContext(),"Zmień walutę do konwersji");
                        return;
                    } else if (kwotaWaluty.getText().toString().isEmpty()) {
                        show_toast.showToast(getApplicationContext(),"Wprowadź poprawną kwotę do konwersji");
                        return;
                    } else {
                        //TODO: Konwertowanie wprowadzonych danych
//                        JSONQuestion obj = new JSONQuestion();
//                        obj.execute(kwotaWaluty.getText().toString());

                    }
                }
                else{
                    show_toast.showToast(getApplicationContext(),"Brak Internetu - Sprawdz połączenie z internetem i spróbuj ponownie");
                }

            }
        });

    }


    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }


    class FromDropdown implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String[] s = parent.getItemAtPosition(position).toString().split(":");
            fromWartosc = s[0];

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }


    }


    class ToDropdown implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String[] s = parent.getItemAtPosition(position).toString().split(":");
            toWartosc = s[0];

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}