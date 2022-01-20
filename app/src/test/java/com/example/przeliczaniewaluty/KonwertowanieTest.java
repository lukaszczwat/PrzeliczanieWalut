package com.example.przeliczaniewaluty;

import static org.junit.Assert.*;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(sdk = 28)
@RunWith(RobolectricTestRunner.class)
public class KonwertowanieTest {
    Konwertowanie konwertowanie;
    @Before
    public void KonwertowanieTest(){
        String odpowiedz = "{\"base_currency_code\":\"EUR\",\"base_currency_name\":\"Euro\",\"amount\":\"6.0000\",\"updated_date\":\"2022-01-20\",\"rates\":{\"USD\":{\"currency_name\":\"United States dollar\",\"rate\":\"1.1331\",\"rate_for_amount\":\"6.7989\"}},\"status\":\"success\"}";
        String toCurrency = "USD";
        konwertowanie = new Konwertowanie(odpowiedz,toCurrency);
    }

    @Test
    public void getToOutput() {
        assertEquals("6.7989", konwertowanie.getToOutput());
    }

    @Test
    public void getToRate() {
        assertEquals("1.1331", konwertowanie.getToRate());
    }
}