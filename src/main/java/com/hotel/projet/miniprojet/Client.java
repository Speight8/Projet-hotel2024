package com.hotel.projet.miniprojet;

public class Client{
    private String nom;
    private long cin;
    private String email;
    private String num_tel;
    private String genre;
    private String nationalite;

    public Client(String nom) {
        this.nom = nom;
    }
    public Client(long cin, String nom,String nationalité,String num_tel, String genre, String email) {
        this.nom = nom;
        this.cin = cin;
        this.email = email;
        this.num_tel = num_tel;
        this.genre = genre;
        this.nationalite = nationalité;
    }
    public String getNom() {
        return nom;
    }

    public long getCin() {
        return cin;
    }

    public String getEmail() {
        return email;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public String getGenre() {
        return genre;
    }

    public String getNationalite() {
        return nationalite;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCin(long cin) {
        this.cin = cin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setNationalité(String nationalité) {
        this.nationalite = nationalité;
    }
    @Override
    public String toString() {
        return "Client{" +
                "nom='" + nom + '\'' +
                ", cin=" + cin +
                ", email='" + email + '\'' +
                ", num_tel='" + num_tel + '\'' +
                ", genre='" + genre + '\'' +
                ", nationalité='" + nationalite + '\'' +
                '}';
    }
}