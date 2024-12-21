package com.example.bevasarlas_oraimunka;

public class Termekek {
    private String nev;
    private int egysegar;
    private double mennyiseg;
    private String mertekegyseg;
    private double brutto_ar;

    public Termekek(String nev, int egysegar, double mennyiseg, String mertekegyseg, double brutto_ar) {
        this.nev = nev;
        this.egysegar = egysegar;
        this.mennyiseg = mennyiseg;
        this.mertekegyseg = mertekegyseg;
        this.brutto_ar = brutto_ar;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getEgysegar() {
        return egysegar;
    }

    public void setEgysegar(int egysegar) {
        this.egysegar = egysegar;
    }

    public double getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(double mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    public String getMertekegyseg() {
        return mertekegyseg;
    }

    public void setMertekegyseg(String mertekegyseg) {
        this.mertekegyseg = mertekegyseg;
    }

    public double getBrutto_ar() {
        return brutto_ar;
    }

    public void setBrutto_ar(double brutto_ar) {
        this.brutto_ar = brutto_ar;
    }
}
