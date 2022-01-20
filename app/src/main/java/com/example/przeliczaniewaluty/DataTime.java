package com.example.przeliczaniewaluty;

import java.util.Calendar;
import java.util.Date;

public class DataTime {
    boolean Czas = false;
    Calendar calendar;

    public DataTime(Date date){
        this.calendar = Calendar.getInstance();
        this.calendar.setTime(date);

    }
    private Date dataNow(){
        return this.calendar.getTime();
    }

    private Date addSeccond(int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataNow());
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }

    public boolean DodanieCzasu(Date date, int second){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Date str = cal.getTime();
        Date str2 = addSeccond(second);
        if( str.getTime() >= str2.getTime()){
            return Czas = true;
        }


        return Czas;
    }


}
