package com.example.przeliczaniewaluty;

import static org.junit.Assert.*;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.ExecutionException;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.O_MR1)
@RunWith(RobolectricTestRunner.class)
public class JSONQuestionTest {


    String odpowiedz = null;
    String question = "https://currency-converter5.p.rapidapi.com/currency/list?format=json";
    Exception mError = null;



    @Test
    public void TestowanieJson(){
        MainActivity context = Robolectric.setupActivity(MainActivity.class);;
        try {
            odpowiedz = new JSONQuestion(context)
                    .execute(question)
                    .get();
        } catch (ExecutionException e) {
            mError = e;

        } catch (InterruptedException e) {
            mError = e;
        }

        assertNull(mError);
        assertFalse(odpowiedz.isEmpty());
        assertTrue(odpowiedz.endsWith("}"));
        assertTrue(odpowiedz.startsWith("{"));


    }
}