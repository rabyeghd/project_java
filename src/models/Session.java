// Classe Session : Gère les données du système
package models;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Session {
    private int id;
    private String nomCours;
    private int formateurId;
    private int salleId;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private BigDecimal dureeHeures;
    private int nombreEtudiants;
    private String statut;
    private String description;

    public Session() {
    }

    public Session(String nomCours, int formateurId, int salleId, Timestamp dateDebut, Timestamp dateFin,
            BigDecimal dureeHeures, int nombreEtudiants, String statut, String description) {
        this.nomCours = nomCours;
        this.formateurId = formateurId;
        this.salleId = salleId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dureeHeures = dureeHeures;
        this.nombreEtudiants = nombreEtudiants;
        this.statut = statut;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomCours() {
        return nomCours;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    public int getFormateurId() {
        return formateurId;
    }

    public void setFormateurId(int formateurId) {
        this.formateurId = formateurId;
    }

    public int getSalleId() {
        return salleId;
    }

    public void setSalleId(int salleId) {
        this.salleId = salleId;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public BigDecimal getDureeHeures() {
        return dureeHeures;
    }

    public void setDureeHeures(BigDecimal dureeHeures) {
        this.dureeHeures = dureeHeures;
    }

    public int getNombreEtudiants() {
        return nombreEtudiants;
    }

    public void setNombreEtudiants(int nombreEtudiants) {
        this.nombreEtudiants = nombreEtudiants;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Session{id=" + id + ", nomCours='" + nomCours + "', formateurId=" + formateurId + ", salleId=" + salleId
                + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", dureeHeures=" + dureeHeures
                + ", nombreEtudiants=" + nombreEtudiants + ", statut='" + statut + "'}";
    }
}
// Fin de la classe Session

