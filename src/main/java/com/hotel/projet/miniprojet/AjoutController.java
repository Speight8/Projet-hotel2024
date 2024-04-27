package com.hotel.projet.miniprojet;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AjoutController {
    public Connection connexion;
    public ConnexionBD connexionBD;
    public PreparedStatement pst;

    public boolean confirmationAjout = false ;
    public boolean confirmationModification = false ;
}
