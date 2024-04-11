package com.hotel.projet.miniprojet;

import java.util.Date;
import java.lang.String;

public class Reservation {

    private int resID;
    private int numChambre;
    private String nomClient;
    private Date dateArrive;
    private Date dateDepart;
    private int periode;
    private int prix;
    private String statut;

    public Reservation(int resID, int numChambre, String nomClient, Date dateArrive, Date dateDepart, int periode, int prix, String statut) {
        this.resID = resID;
        this.numChambre = numChambre;
        this.nomClient = nomClient;
        this.dateArrive = dateArrive;
        this.dateDepart = dateDepart;
        this.periode = periode;
        this.prix = prix;
        this.statut = statut;
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

    public Date getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(Date dateArrive) {
        this.dateArrive = dateArrive;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public int getPeriode() {
        return periode;
    }

    public void setPeriode(int periode) {
        this.periode = periode;
    }

    public int getPrix() {
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
}
