package com.example.guillemllados.uiiot;

import java.util.Calendar;

/**
 * Created by guillemllados on 31/10/17.
 */

public class CalendarE {

    private Calendar c;

    public CalendarE(){
        c = Calendar.getInstance();
    }

    public CalendarE(Calendar c){
        this.c = c;
    }

    public int getHour(){
        return c.get(Calendar.HOUR_OF_DAY);
    }
    public int getMin(){
        return c.get(Calendar.MINUTE);
    }
    public int getSec(){
        return c.get(Calendar.SECOND);
    }
    public int getMonth(){
        return c.get(Calendar.MONTH)+1;
    }
    public int getYear(){
        return c.get(Calendar.YEAR);
    }
    public int getDay(){
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public void setDate(int day,int month ,int year){
        c.set(Calendar.DAY_OF_MONTH,day);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.YEAR,year);
    }

    public void setTime(int hour,int min ,int sec){
        c.set(Calendar.MINUTE,min);
        c.set(Calendar.SECOND,sec);
        c.set(Calendar.HOUR_OF_DAY,hour);
    }

    public String toString(){

        return Integer.toString(getYear())+"-"+Integer.toString(getMonth())+"-"+Integer.toString(getDay())+" "+Integer.toString(getHour())+":"+Integer.toString(getMin())+":"+Integer.toString(getSec());
    }
}
