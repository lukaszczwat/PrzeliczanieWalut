package com.example.przeliczaniewaluty;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.Date;


@RunWith(JUnit4.class)
public class DataTimeTest {



    @Test
    public void TestowanieCzasu(){

        DataTime dataTime = new DataTime(new Date());
        int x = 10;

        do{
            if(dataTime.DodanieCzasu(new Date(), x)){
                assertTrue(dataTime.DodanieCzasu(new Date(), x));
            }
        }while(!dataTime.DodanieCzasu(new Date(), x));


        assertTrue(dataTime.DodanieCzasu(new Date(), x));
    }

}