package com.hotel.projet.miniprojet;

import java.util.Date;
import java.lang.String;

public class Reservation {

    private int resID;
    private int numChambre;
    private String nomClient;
    private String dateArrive;
    private String dateDepart;
    private int nbLits;
    private int duree;
    private float prix;

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    private float total;
    private String statut;

    public Reservation(int resID, int numChambre, int nbLits, String nomClient, String dateArrive, String dateDepart, int periode, float prix, float total, String statut) {
        this.resID = resID;
        this.numChambre = numChambre;
        this.nomClient = nomClient;
        this.dateArrive = dateArrive;
        this.dateDepart = dateDepart;
        this.duree = dateDepart.compareTo(dateArrive);
        this.prix = prix;
        this.statut = statut;
        this.total = total;
    }
    public Reservation(int resID, int numChambre, String nomClient, String dateArrive, String dateDepart, int periode, float total, String statut) {
        this.resID = resID;
        this.numChambre = numChambre;
        this.nomClient = nomClient;
        this.dateArrive = dateArrive;
        this.dateDepart = dateDepart;
        this.duree = periode;
        this.statut = statut;
        this.total = total;
    }
    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public int getNumChambre() {
        return numChambre;
    }

    public void setNumChambre(int numChambre) {
        this.numChambre = numChambre;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(Date dateArrive) {
        this.dateArrive = String.valueOf(dateArrive);
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = String.valueOf(dateDepart);
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int periode) {
        this.duree = periode;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getNbLits() {
        return nbLits;
    }

    public void setNbLits(int nbLits) {
        this.nbLits = nbLits;
    }

}
