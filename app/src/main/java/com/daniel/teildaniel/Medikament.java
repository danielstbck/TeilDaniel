package com.daniel.teildaniel;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "medikament_table")
public class Medikament {

    //joseph was here

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String wirkstoff;

    private String aufbewahrungshinweis;

    private String einnahme;

    private String anwendungsgebiet;

    private String verschreibungspflichtig;

    public Medikament(String name, String wirkstoff, String anwendungsgebiet, String verschreibungspflichtig) {
        this.name = name;
        this.wirkstoff = wirkstoff;
        /*this.aufbewahrungshinweis = aufbewahrungshinweis;
        this.einnahme = einnahme;*/
        this.anwendungsgebiet = anwendungsgebiet;
        this.verschreibungspflichtig = verschreibungspflichtig;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWirkstoff() {
        return wirkstoff;
    }

    public String getAufbewahrungshinweis() {
        return aufbewahrungshinweis;
    }

    public String getEinnahme() {
        return einnahme;
    }

    public String getAnwendungsgebiet() {
        return anwendungsgebiet;
    }

    public String getVerschreibungspflichtig() {
        return verschreibungspflichtig;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWirkstoff(String wirkstoff) {
        this.wirkstoff = wirkstoff;
    }

    public void setAufbewahrungshinweis(String aufbewahrungshinweis) {
        this.aufbewahrungshinweis = aufbewahrungshinweis;
    }

    public void setEinnahme(String einnahme) {
        this.einnahme = einnahme;
    }

    public void setAnwendungsgebiet(String anwendungsgebiet) {
        this.anwendungsgebiet = anwendungsgebiet;
    }

    public void setVerschreibungspflichtig(String verschreibungspflichtig) {
        this.verschreibungspflichtig = verschreibungspflichtig;
    }
}
