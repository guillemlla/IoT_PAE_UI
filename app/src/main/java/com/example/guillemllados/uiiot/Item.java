package com.example.guillemllados.uiiot;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by guillemllados on 5/10/17.
 */

public class Item {

    private String nom,id;
    private ArrayList<Atributs> atributs;
    private String image;
    private int latitude,longitude;

    public Item(String nom, String id,String image) {
        this.nom = nom;
        atributs = new ArrayList<>();
        this.id = id;
        this.image = image;
        latitude = 0;
        longitude = 0;
    }

    public ArrayList<Atributs> getAtributs() {
        return atributs;
    }

    public void addAtribute(Atributs a){
        atributs.add(a);
    }

    public void addAtribute(int day,int month, int year, int hour, int min, int sec, String atrib1, String atrib2){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,day);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.YEAR,year);
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,min);
        c.set(Calendar.SECOND,sec);
        CalendarE cg = new CalendarE(c);
        atributs.add(new Atributs(cg,atrib1,atrib2));
    }

    public void addAtribute(Calendar c, String atrib1, String atrib2){
        CalendarE cg = new CalendarE(c);
        atributs.add(new Atributs(cg,atrib1,atrib2));
    }
    public void addAtribute(CalendarE cg, String atrib1, String atrib2){

        atributs.add(new Atributs(cg,atrib1,atrib2));
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public String getLastAtrib1() {
        return atributs.get(atributs.size()-1).getAtrib1();
    }

    public String getLastAtrib2() {
        return atributs.get(atributs.size()-1).getAtrib2();
    }

    public String  getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public String lastTimeUpdated(){

        CalendarE now = new CalendarE();
        CalendarE lastC = atributs.get(atributs.size()-1).getDate();

        if(now.getYear()==lastC.getYear() && now.getMonth() == lastC.getMonth() && now.getDay() != lastC.getDay()){
            return "Fa "+(now.getDay()-lastC.getDay()) +" dies que no s'actualitza.";
        }else if(now.getDay() == lastC.getDay()){
            if(now.getHour()== lastC.getHour()){
                return "Fa menys d'una hora que s'ha actualizat";
            }else{
                return "Fa "+(now.getHour()-lastC.getHour()) +" Hores que no s'actualitza.";
            }
        }else{
            return "Fa molt de temps que no s'actualitza.";
        }

    }





}



