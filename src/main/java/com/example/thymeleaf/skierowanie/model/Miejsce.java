package com.example.thymeleaf.skierowanie.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="fajne_miejsce_wpadaj")  //to nadpisyuje nazwe tabeli w bazie, bez tego nazwa tabeli bedzie=nazwie klasy
public class Miejsce {

    @Id
    @GeneratedValue
    Integer id;

    String adres;

    String kodPocztowy;

    String miasto;

    @Transient  //tak zaznaczony parametr nie wejdzie do encji (bazy danych)
    String dyskoteka;

    @OneToMany(mappedBy = "miejsce")
    List<SkierowanieDoLekarza> skierowanieDoLekarzas = new ArrayList<>();

    @OneToMany(mappedBy = "miejsce")
    List<Pracownik> pracowniks = new ArrayList<>();

    public List<Pracownik> getPracowniks() {
        return pracowniks;
    }

    public void setPracowniks(List<Pracownik> pracowniks) {
        this.pracowniks = pracowniks;
    }

    public List<SkierowanieDoLekarza> getSkierowanieDoLekarzas() {
        return skierowanieDoLekarzas;
    }
//
    public void setSkierowanieDoLekarzas(List<SkierowanieDoLekarza> skierowanieDoLekarzas) {
        this.skierowanieDoLekarzas = skierowanieDoLekarzas;
    }

    //hibernate bedzie zawsze szukal konstruktora domyslnego!!!! do stworzenia encji!!
    public Miejsce() {
    }

    public Miejsce(String adres, String kodPocztowy, String miasto, String dyskoteka) {
        this.adres = adres;
        this.kodPocztowy = kodPocztowy;
        this.miasto = miasto;
        this.dyskoteka = dyskoteka;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getDyskoteka() {
        return dyskoteka;
    }

    public void setDyskoteka(String dyskoteka) {
        this.dyskoteka = dyskoteka;
    }
}
