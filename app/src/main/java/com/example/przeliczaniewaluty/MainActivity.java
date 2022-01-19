package com.example.przeliczaniewaluty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
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

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText kwotaWaluty, wartoscPoPrzeliczeniu;
    Button convertButton;
    Spinner fromWaluta, toWaluta;
    String fromWartosc, toWartosc, odpowiedz;
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

        RunSpinerValue(from,to);

        if (isNetworkConnected()){
            String question = "https://currency-converter5.p.rapidapi.com/currency/list?format=json";
            try {
                odpowiedz = new JSONQuestion(MainActivity.this)
                        .execute(question)
                        .get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            PobieranieWaluty pobieranieWaluty = new PobieranieWaluty();
            if (pobieranieWaluty.getWaluty(odpowiedz).length > 0){
                toApiCurrecy = pobieranieWaluty.getWaluty(odpowiedz);
                RunSpinerValue(toApiCurrecy, toApiCurrecy);
            }
        }
        else{
            show_toast.showToast(getApplicationContext(),"Brak Internetu - Sprawdź połączenie i spróbuj ponownie");
        }

        Setup();

    }
    private void RunSpinerValue(String[] from,String[] to){


        ArrayAdapter<String> adapterFrom = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, from);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapterTo = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, to);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromWaluta.setAdapter(adapterFrom);
        toWaluta.setAdapter(adapterTo);
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

                    } else if (kwotaWaluty.getText().toString().isEmpty()) {
                        show_toast.showToast(getApplicationContext(),"Wprowadź poprawną kwotę do konwersji");

                    } else {
                        String question = "https://currency-converter5.p.rapidapi.com/currency/convert?format=json&from=" + fromWartosc + "&to=" + toWartosc + "&amount=" + kwotaWaluty.getText().toString();

                        try {
                            odpowiedz = new JSONQuestion(MainActivity.this)
                                    .execute(question)
                                    .get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Konwertowanie konwertowanie = new Konwertowanie(odpowiedz, toWartosc);
                        wartoscPoPrzeliczeniu.setText(konwertowanie.getToOutput());
                        toRate.setText(konwertowanie.getToRate());

                        String from = kwotaWaluty.getText().toString();
                        String to = wartoscPoPrzeliczeniu.getText().toString();

                        NotyficationShow notyficationShow = new NotyficationShow(MainActivity.this);
                        Notification.Builder nb = notyficationShow.
                                getAndroidChannelNotification("Currency", from + " - " + fromWartosc +
                                        " to " + to + " - " + toWartosc);

                        notyficationShow.getManager().notify(101, nb.build());

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