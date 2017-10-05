package com.example.guillemllados.uiiot;

/**
 * Created by guillemllados on 5/10/17.
 */

public class Item {

    private String nom,atrib1,atrib2,atrib3;
    private int id;
    private String image;

    public Item(String nom, String atrib1, String atrib2, String atrib3, int id,String image) {
        this.nom = nom;
        this.atrib1 = atrib1;
        this.atrib2 = atrib2;
        this.atrib3 = atrib3;
        this.id = id;
        this.image = image;
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

    public String getAtrib1() {
        return atrib1;
    }

    public String getAtrib2() {
        return atrib2;
    }

    public String getAtrib3() {
        return atrib3;
    }

    public int getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAtrib1(String atrib1) {
        this.atrib1 = atrib1;
    }

    public void setAtrib2(String atrib2) {
        this.atrib2 = atrib2;
    }

    public void setAtrib3(String atrib3) {
        this.atrib3 = atrib3;
    }
}
