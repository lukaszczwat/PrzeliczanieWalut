package com.example.przeliczaniewaluty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    String fromWartosc, toWartosc, odpowiedz;
    TextView toRate;

    ShowToast show_toast = new ShowToast();
    Handler handler;


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
        //uruchomienie widoku z Walutami EUR i USD w przypadku braku interentu oraz nie zaciągniecia walut
        RunSpinerValue(from,to);
        //sprawdzenie czy jest połączenie z Interentem
        if (isNetworkConnected()){
            String question = "https://currency-converter5.p.rapidapi.com/currency/list?format=json";
            try {
                //wysłanie zapytania do strony
                odpowiedz = new JSONQuestion(MainActivity.this)
                        .execute(question)
                        .get();
            } catch (Exception e) {
                show_toast.showToast(getApplicationContext(), "Problem z pobraniem waluty "+ e.toString());
            }
            //tworzenie listy z pobranych walut
            PobieranieWaluty pobieranieWaluty = new PobieranieWaluty();
            if (pobieranieWaluty.getWaluty(odpowiedz).length > 0){
                //uruchomienie widoku z pobranymi walutami
                RunSpinerValue(pobieranieWaluty.getWaluty(odpowiedz), pobieranieWaluty.getWaluty(odpowiedz));
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

                //sprawdzenie czy jest połączenie z Internetem
                if (isNetworkConnected()) {
                    //sprawdzenie czy obje waluty nie są takie same
                    if (fromWaluta == toWaluta) {
                        show_toast.showToast(getApplicationContext(),"Zmień walutę do konwersji");
                    //sprawdzenie czy kowata waluty nie jest pusta
                    } else if (kwotaWaluty.getText().toString().isEmpty()) {
                        show_toast.showToast(getApplicationContext(),"Wprowadź poprawną kwotę do konwersji");

                    } else {
                        //string do rapid api
                        String question = "https://currency-converter5.p.rapidapi.com/currency/convert?format=json&from=" + fromWartosc + "&to=" + toWartosc + "&amount=" + kwotaWaluty.getText().toString();
                        //wysłanie pytania o przekonwertowanie waluty
                        try {
                            odpowiedz = new JSONQuestion(MainActivity.this)
                                    .execute(question)
                                    .get();
                        } catch (Exception e) {
                            show_toast.showToast(getApplicationContext(),"Problem z konwertowaniem waluty " + e.toString());
                        }
                        //jeżeli odpowiedz nie jest pusta i null
                        if(!odpowiedz.isEmpty()){
                            //praca na odpowiedzi z rapid api, wydobywanie wartości z odpowiedzi
                            Konwertowanie konwertowanie = new Konwertowanie(odpowiedz, toWartosc);
                            //wpisanie wartości do aplikacji - context
                            wartoscPoPrzeliczeniu.setText(konwertowanie.getToOutput());
                            toRate.setText(konwertowanie.getToRate());
                            //numer pytania które robiliści do rapid api
                            int x = 1;
                            //
                            handler = new Handler(){
                                @Override
                                public void handleMessage(@NonNull Message msg) {
                                    super.handleMessage(msg);
                                    //wywołanie notyfikacji
                                    NotyficationRun(msg.what, (String)msg.obj);
                                }
                            };
                            //wywołanie nowego zadania - notyfikacji
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String from = kwotaWaluty.getText().toString();
                                    String to = wartoscPoPrzeliczeniu.getText().toString();
                                    String bodyNotyfication = from + " - " + fromWartosc + " to " + to + " - " + toWartosc;
                                    //utworzenie wiadomości do handlera
                                    Message message = new Message();
                                    message.what = x;
                                    message.obj = bodyNotyfication;
                                    //wysłanie wiadomości do handlera z opóźnieniem 30s
                                    handler.sendMessageDelayed(message, 30000);
                                }
                            }).start();

                        }




                    }
                }
                else{
                    show_toast.showToast(getApplicationContext(),"Brak Internetu - Sprawdz połączenie z internetem i spróbuj ponownie");
                }

            }
        });

    }

    private void NotyficationRun(int y, String  bodyNotyfication){

        try {

            NotyficationShow notyficationShow;
            Notification.Builder nb;
            notyficationShow = new NotyficationShow(MainActivity.this);
            nb = notyficationShow.
                    getAndroidChannelNotification("Przelicz Walutę", bodyNotyfication);

            notyficationShow.getManager().notify(y, nb.build());

        } catch (Exception e) {
            String error = e.toString();
            show_toast.showToast(MainActivity.this, "Problem z wyświetleniem notyfikacji" + error);

        }

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