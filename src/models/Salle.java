// Classe Salle : Gère les données du système
package models;

public class Salle {
    private int id;
    private String nom;
    private int capacite;
    private String type;
    private String equipementsFixes;
    private String localisation;
    private boolean disponible;

    public Salle() {
    }

    public Salle(String nom, int capacite, String type, String equipementsFixes, String localisation,
            boolean disponible) {
        this.nom = nom;
        this.capacite = capacite;
        this.type = type;
        this.equipementsFixes = equipementsFixes;
        this.localisation = localisation;
        this.disponible = disponible;
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

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEquipementsFixes() {
        return equipementsFixes;
    }

    public void setEquipementsFixes(String equipementsFixes) {
        this.equipementsFixes = equipementsFixes;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Salle{id=" + id + ", nom='" + nom + "', capacite=" + capacite + ", type='" + type
                + "', equipementsFixes='" + equipementsFixes + "', localisation='" + localisation + "', disponible="
                + disponible + "}";
    }
}
// Fin de la classe Salle

