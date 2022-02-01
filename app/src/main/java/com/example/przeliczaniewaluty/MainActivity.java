package com.example.przeliczaniewaluty;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
    int x = 1;
    NetworkConnect networkConnect = new NetworkConnect();
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
        if (networkConnect.Sprawdz(MainActivity.this)){
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
            if (pobieranieWaluty.getWaluty(odpowiedz, MainActivity.this).length > 0){
                //uruchomienie widoku z pobranymi walutami
                RunSpinerValue(pobieranieWaluty.getWaluty(odpowiedz, MainActivity.this), pobieranieWaluty.getWaluty(odpowiedz, MainActivity.this));
            }
        }
        else{
            show_toast.showToast(getApplicationContext(),"Brak Internetu - Sprawdź połączenie i spróbuj ponownie");
        }

        setup();




    }
    private void RunSpinerValue(String[] from,String[] to){


        ArrayAdapter<String> adapterFrom = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, from);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapterTo = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, to);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromWaluta.setAdapter(adapterFrom);
        toWaluta.setAdapter(adapterTo);
    }

    private void setup() {


        fromWaluta.setOnItemSelectedListener(new FromDropdown());
        toWaluta.setOnItemSelectedListener(new ToDropdown());
        convertButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                CheckCurrency checkCurrency = new CheckCurrency();
                AmountCurrency amountCurrency = new AmountCurrency();

                //sprawdzenie czy jest połączenie z Internetem, sprawdzenie czy obje waluty nie są takie same,
                //sprawdzenie czy kowata waluty nie jest pusta
                if (networkConnect.Sprawdz(MainActivity.this) &&
                        !checkCurrency.Sprawdz(MainActivity.this, fromWaluta, toWaluta) &&
                        !amountCurrency.Sprawdz(MainActivity.this, kwotaWaluty)) {
                    //
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
                            Konwertowanie konwertowanie = new Konwertowanie(odpowiedz, toWartosc, MainActivity.this);
                            //wpisanie wartości do aplikacji - context
                            wartoscPoPrzeliczeniu.setText(konwertowanie.getToOutput());
                            toRate.setText(konwertowanie.getToRate());
                            //numer pytania które robiliści do rapid api

                            //
                            handler = new Handler(Looper.getMainLooper());

                            handler.postDelayed(() -> {
                                String from = kwotaWaluty.getText().toString();
                                String to = wartoscPoPrzeliczeniu.getText().toString();
                                String bodyNotyfication = from + " - " + fromWartosc + " to " + to + " - " + toWartosc;
                                new RunNotyfication(x, bodyNotyfication, MainActivity.this);


                            }, 30000);

                            x++;
                        }





                }


            }
        });

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