// Classe Etudiant : Gère les données du système
package models;

import java.sql.Date;

public class Etudiant {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String niveau;
    private Date dateInscription;

    public Etudiant() {
    }

    public Etudiant(String nom, String prenom, String email, String telephone, String niveau, Date dateInscription) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.niveau = niveau;
        this.dateInscription = dateInscription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    @Override
    public String toString() {
        return "Etudiant{id=" + id + ", nom='" + nom + "', prenom='" + prenom + "', email='" + email + "', telephone='"
                + telephone + "', niveau='" + niveau + "', dateInscription=" + dateInscription + "}";
    }
}
// Fin de la classe Etudiant

