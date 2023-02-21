package com.example.kasvipaivakirja_ohj2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Kasvi-luokkaa käsittelevä käyttöliittymä, jonka avulla
 * voidaan pitää kirjaa kasvin hoidosta ja jolla voidaan
 * päivittää kasvin pituutta ja viimeisimpiä kastelukertoja.
 */
public class KasvipaivakirjanKayttoliittyma extends Application {
    /**
     * Tekstikenttä kasvin nimen asettamista varten,
     * eli uuden kasvin luomista varten.
     */
    private TextField tfNimi = new TextField();

    /**
     * Nappi kasvin nimen asettamista varten,
     * eli uuden kasvin luomista varten.
     */
    private Button btNimi = new Button("Luo kasvi");

    /**
     * Tekstikenttä kasvin pituuden asettamista varten.
     */
    private TextField tfPituus = new TextField();

    /**
     * Nappi kasvin pituuden asettamista varten.
     */
    private Button btPituus = new Button("Aseta pituus");

    /**
     * Tekstikenttä kasvin viimeisimmän kastelukerran
     * asettamista varten.
     */
    private TextField tfKastelukerta = new TextField();

    /**
     * Nappi kasvin viimeisimmän kastelukerran
     * asettamista varten.
     */
    private Button btKastelukerta = new Button("Aseta kastelukerta");

    /**
     * Nappi kasvien tietojen tarkastamiselle.
     */
    private Button btHistoria = new Button("Kasvin historia");


    /**
     * Ohjelmaikkunan käynnistyksen ja toiminnallisuuden
     * määrittely.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        /**
         * Luodaan paneeli, johon tulee kaikki graafiset osat,
         * ja asetetaan paneelille tilaa solmujen välille.
         */
        GridPane paneeli = new GridPane();
        paneeli.setHgap(10);
        paneeli.setVgap(10);
        paneeli.setBackground(new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW,CornerRadii.EMPTY, Insets.EMPTY)));

        /**
         * Tehdään napeista toiminnalliset,
         * eli käsitellään tapahtumat.
         */
        btNimi.setOnAction(e -> luoKasvi());
        btHistoria.setOnAction(e -> {
            JOptionPane.showMessageDialog(null,
                    lueKasvinTiedot(),
                    "Kasvin tietoja:)",
                    JOptionPane.PLAIN_MESSAGE);
        });
        btKastelukerta.setOnAction(e -> paivitaKastelu());
        btPituus.setOnAction(e -> paivitaPituus());

        /**
         * Lisätään paneeliin kaikki napit ja tekstikentät (eli solmut),
         * ja luodaan muutama Label eli otsikko niiden selitteiksi.
         */
        paneeli.add(new Label("Luo uusi kasvi. Syötä uuden kasvin nimi: "),0,0);
        paneeli.add(tfNimi,1, 0);
        paneeli.add(btNimi,2, 0);
        paneeli.add(new Label("HUOM! Jos luot uuden kasvin, entisen kasvin tiedot poistuvat. :(" +
                "\nOhjelmalla voi seurata YHDEN kasvin kastelukertoja/pituutta!! "),0,1);
        paneeli.add(new Label("Aseta ohjelmaan luodun kasvin pituus. Syötä kasvin pituus sentteinä: "),0,2);
        paneeli.add(tfPituus,1,2);
        paneeli.add(btPituus,2,2);
        paneeli.add(new Label("Aseta ohjelmaan luodun kasvin kastelukerta. Syötä kastelukerta muodossa vuosi/kk/pv: "),0,3);
        paneeli.add(tfKastelukerta,1,3);
        paneeli.add(btKastelukerta,2,3);
        paneeli.add(new Label("Tästä saat näkyviin luodun kasvin uusimmat tiedot: "),0,4);
        paneeli.add(btHistoria,1,4);


        /**
         * Määritellään paneelin asettelua.
         */
        paneeli.setAlignment(Pos.CENTER);
        tfKastelukerta.setAlignment(Pos.BOTTOM_RIGHT);
        tfNimi.setAlignment(Pos.BOTTOM_RIGHT);
        tfPituus.setAlignment(Pos.BOTTOM_RIGHT);

        /**
         * Asetetaan paneeli alkuikkunaan ja kehykseen.
         */
        Scene kehys = new Scene(paneeli,800,200);
        stage.setTitle("Kasvipäiväkirja :)");
        stage.setScene(kehys);
        stage.show();

    }

    /**
     * Metodi luo uuden kasviolion.
     */
    private void luoKasvi(){
        String nimi = tfNimi.getText();
        tfNimi.clear(); //Tyhjennetään nimi-textfield
        talletaKasvi(new Kasvi(nimi)); //Tallennetaan uusi kasvi tiedostoon oliona
    }

    /**
     * Metodi tallettaa uuden kasvin tiedostoon.
     */
    private void talletaKasvi(Kasvi kasviOlio){
        try{
            FileOutputStream tiedosto = new FileOutputStream("kasvit.dat");
            ObjectOutputStream oliotiedosto = new ObjectOutputStream(tiedosto);

            oliotiedosto.writeObject(kasviOlio);
            System.out.println("Tiedostoon kirjoittaminen onnistui");
            tiedosto.close();
        } catch (IOException e){
            System.out.println("Virhe tiedostoon kirjoittamisessa");
        }
    }

    /**
     * Metodi lukee kasvin tiedot tiedostosta,
     * ja palauttaa kasvin oliona.
     */
    private Kasvi lueKasvinTiedot() {
        File tiedostoTesti = new File("kasvit.dat");
        Kasvi luettuKasvi = null;

        if (tiedostoTesti.exists()) {
            try {
                FileInputStream tiedosto = new FileInputStream("kasvit.dat");
                ObjectInputStream tiedostoOlio = new ObjectInputStream(tiedosto);

                luettuKasvi = (Kasvi) tiedostoOlio.readObject();
                tiedosto.close();

            } catch (EOFException e) {
                System.err.println("Tiedostosta yritettiin lukea tiedoston lopun ohi");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Tiedosto löytyi, mutta lukeminen päättyi virheeseen");
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "Tiedostoa ei löytynyt... olethan luonut kasvin ennen sen muokkausta?" +
                            "\nKasvin pituutta/kastelukertaa ei voi muokata jos kasvia ei ole luotu :(",
                    "Varoitusvirhe",
                    JOptionPane.WARNING_MESSAGE);
            System.err.println("Tiedostoa ei löytynyt.");
        }

        return luettuKasvi;
    }

    /**
     * Metodi päivittää ohjelmassa olevan kasvin uusimman kastelukerran.
     * Se lukee Kastelukerta-tekstikentästä käyttäjän syötteen, ja asettaa
     * sen (tyyppimuunnosten jälkeen) tiedostossa olevan kasvin uudeksi
     * pituudeksi.
     * Tieto päivitetään lukemalla tiedostoa ja kirjoittamalla tiedostoon.
     * Jos käyttäjä antaa vääränmuotoisen syötteen, tulostetaan varoituslaatikko.
     */
    private void paivitaKastelu() {
        DateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date kastelukerta;

        try {
            String teksti = tfKastelukerta.getText();
            teksti = teksti.replace('/', '.');

            kastelukerta = format.parse(teksti);
            Kasvi luettuKasvi = lueKasvinTiedot();

            luettuKasvi.setViimeisin_kastelukerta(kastelukerta);
            talletaKasvi(luettuKasvi);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null,
                    "Väärä muoto! Yritä uudelleen.",
                    "Varoitusvirhe",
                    JOptionPane.WARNING_MESSAGE);
            System.err.println("Väärä muoto! Yritä uudelleen");
        }

        tfKastelukerta.clear();
    }

    /**
     * Metodi päivittää ohjelmassa olevan kasvin pituuden.
     * Se lukee pituus-tekstikentästä käyttäjän syötteen, ja
     * asettaa sen (tyyppimuunnosten jälkeen) tiedostossa olevan
     * kasvin uudeksi pituudeksi.
     * Tieto päivitetään lukemalla tiedostoa ja kirjoittamalla tiedostoon.
     * Jos käyttäjä antaa vääränmuotoisen syötteen, tulostetaan varoituslaatikko.
     */
    private void paivitaPituus(){
        try{
            String teksti = tfPituus.getText();
            teksti = teksti.replace(',', '.');

            double pituus = Double.parseDouble(teksti);

            Kasvi luettuKasvi = lueKasvinTiedot();
            luettuKasvi.setPituus(pituus);
            talletaKasvi(luettuKasvi);
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,
                    "Väärä muoto! Yritä uudelleen.",
                    "Varoitusvirhe",
                    JOptionPane.WARNING_MESSAGE);
            System.err.println("Väärä muoto! Yritä uudelleen");
        }


        tfPituus.clear();
    }

    /**
     * Ohjelman käynnistävä metodi
     * @param args kutsuparametreja ei käytetä
     */
    public static void main(String[]args){
        Application.launch(args);
    }
}
