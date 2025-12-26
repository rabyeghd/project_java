package models;

public class Materiel {
    private int id;
    private String nom;
    private String type;
    private int quantiteDisponible;
    private int quantiteTotale;
    private String etat;
    private String description;

    public Materiel() {
    }

    public Materiel(String nom, String type, int quantiteDisponible, int quantiteTotale, String etat,
            String description) {
        this.nom = nom;
        this.type = type;
        this.quantiteDisponible = quantiteDisponible;
        this.quantiteTotale = quantiteTotale;
        this.etat = etat;
        this.description = description;
    }

    // Getters and Setters
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantiteDisponible() {
        return quantiteDisponible;
    }

    public void setQuantiteDisponible(int quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }

    public int getQuantiteTotale() {
        return quantiteTotale;
    }

    public void setQuantiteTotale(int quantiteTotale) {
        this.quantiteTotale = quantiteTotale;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Materiel{id=" + id + ", nom='" + nom + "', type='" + type + "', quantiteDisponible="
                + quantiteDisponible + ", quantiteTotale=" + quantiteTotale + ", etat='" + etat + "', description='"
                + description + "'}";
    }
}
