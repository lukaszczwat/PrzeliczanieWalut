package com.example.przeliczaniewaluty;

import android.app.Activity;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PobieranieWaluty extends Activity {

    ShowToast show_toast = new ShowToast();


    public String[] getWaluty(String odpowiedz){
        try {
            JSONObject movieObject = new JSONObject(odpowiedz);

            String currency = (String) movieObject.getString("currencies");
            currency = currency.replaceAll("\\{", "");
            currency = currency.replaceAll("\\}", "");
            currency = currency.replaceAll("\"", "");
            List<String> skillsArray = Arrays.asList(currency.split(","));
            int size = skillsArray.size();
            Collections.sort(skillsArray);
            return skillsArray.toArray(new String[size]);




        } catch (Exception e) {
            show_toast.showToast(getApplicationContext(),"Coś poszło źle przy pobieraniu Waluty " + e.toString());
        }
        return null;
    }

}
