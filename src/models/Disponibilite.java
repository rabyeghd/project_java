// Classe Disponibilite : Gère les données du système
package models;

import java.sql.Date;
import java.sql.Time;

public class Disponibilite {
    private int id;
    private int formateurId;
    private String jourSemaine;
    private Time heureDebut;
    private Time heureFin;
    private Date dateDebut;
    private Date dateFin;
    private String type;

    public Disponibilite() {
    }

    public Disponibilite(int formateurId, String jourSemaine, Time heureDebut, Time heureFin, Date dateDebut,
            Date dateFin, String type) {
        this.formateurId = formateurId;
        this.jourSemaine = jourSemaine;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFormateurId() {
        return formateurId;
    }

    public void setFormateurId(int formateurId) {
        this.formateurId = formateurId;
    }

    public String getJourSemaine() {
        return jourSemaine;
    }

    public void setJourSemaine(String jourSemaine) {
        this.jourSemaine = jourSemaine;
    }

    public Time getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Time getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Disponibilite{id=" + id + ", formateurId=" + formateurId + ", jourSemaine='" + jourSemaine
                + "', heureDebut=" + heureDebut + ", heureFin=" + heureFin + ", dateDebut=" + dateDebut + ", dateFin="
                + dateFin + ", type='" + type + "'}";
    }
}
// Fin de la classe Disponibilite

