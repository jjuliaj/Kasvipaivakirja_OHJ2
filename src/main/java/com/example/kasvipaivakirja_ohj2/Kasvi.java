package com.example.kasvipaivakirja_ohj2;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Luokka toteuttaa yksinkertaisen kasvi-olion, jolla on
 * pituus, nimi ja viimeisin kastelukerta.
 */
public class Kasvi implements Serializable {
    /**
     * Nimi merkkijonona, yksityinen.
     */
    private String nimi;
    /**
     * Pituus desimaalilukuna, yksityinen.
     */
    private double pituus;
    /**
     * Viimeisin kastelukerta päivämääränä, yksityinen.
     */
    private Date viimeisin_kastelukerta;


    /**
     * Alustaja, joka saa parametreina kasvin nimen.
     */
    protected Kasvi(String nimi){
        this.nimi = nimi;
    }

    /**
     * Asettaa pituuden.
     * @param pituus
     */
    public void setPituus(double pituus) {
        this.pituus = pituus;
    }

    /**
     * Palauttaa pituuden.
     * @return
     */
    public double getPituus() {
        return pituus;
    }

    /**
     * Asettaa viimeisimmän kastelukerran.
     * @param viimeisin_kastelukerta
     */
    public void setViimeisin_kastelukerta(Date viimeisin_kastelukerta) {
        this.viimeisin_kastelukerta = viimeisin_kastelukerta;
    }

    /**
     * Palauttaa viimeisimmän kastelukerran.
     * @return
     */
    public Date getViimeisin_kastelukerta() {
        return viimeisin_kastelukerta;
    }

    /**
     * To String -metodi Kasvi-luokan olioiden tulostamiseen.
     * @return
     */
    @Override
    public String toString() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return ("PÄIVITYS KASVISTA "+this.nimi+
                "\nKasvin viimeisin kastelukerta: "+simpleDateFormat.format(this.viimeisin_kastelukerta)+
                "\nKasvin viimeisin pituus: "+this.pituus);
    }
}

