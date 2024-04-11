package com.hotel.projet.miniprojet;

public class Room {
    private int roomNumber; // Numéro de chambre
    private String status; //L'état du chambre
    private int numberOfBeds; // Nombre de lits
    private String bathroomType; // Type de salle de bain
    private String telephoneNumber; // Numéro de téléphone associé à la chambre

    public Room(int roomNumber, String status, int numberOfBeds, String bathroomType, String telephoneNumber) {
        this.roomNumber = roomNumber;
        this.status= status;
        this.numberOfBeds = numberOfBeds;
        this.bathroomType = bathroomType;
        this.telephoneNumber = telephoneNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public String getBathroomType() {
        return bathroomType;
    }

    public void setBathroomType(String bathroomType) {
        this.bathroomType = bathroomType;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}
