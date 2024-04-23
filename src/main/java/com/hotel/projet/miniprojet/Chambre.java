package com.hotel.projet.miniprojet;

public class Chambre {
    private int numChambre;
    private int nbLits;
    private String typeSdb;
    private String etat;

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    private float prix;
    public Chambre(int numChambre, int nbLits, String typeSdb, String etat, Float prix) {
        this.numChambre = numChambre;
        this.nbLits = nbLits;
        this.typeSdb = typeSdb;
        this.etat = etat;
        this.prix = prix;
    }


    public int getNumChambre() {
        return numChambre;
    }

    public void setNumChambre(int numChambre) {
        this.numChambre = numChambre;
    }

    public int getNbLits() {
        return nbLits;
    }

    public void setNbLits(int nbLits) {
        this.nbLits = nbLits;
    }

    public String getTypeSdb() {
        return typeSdb;
    }

    public void setTypeSdb(String typeSdb) {
        this.typeSdb = typeSdb;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
