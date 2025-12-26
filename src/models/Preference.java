package models;

public class Preference {
    private int id;
    private String entiteType;
    private int entiteId;
    private String typePreference;
    private String valeur;
    private int priorite;

    public Preference() {
    }

    public Preference(String entiteType, int entiteId, String typePreference, String valeur, int priorite) {
        this.entiteType = entiteType;
        this.entiteId = entiteId;
        this.typePreference = typePreference;
        this.valeur = valeur;
        this.priorite = priorite;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntiteType() {
        return entiteType;
    }

    public void setEntiteType(String entiteType) {
        this.entiteType = entiteType;
    }

    public int getEntiteId() {
        return entiteId;
    }

    public void setEntiteId(int entiteId) {
        this.entiteId = entiteId;
    }

    public String getTypePreference() {
        return typePreference;
    }

    public void setTypePreference(String typePreference) {
        this.typePreference = typePreference;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    @Override
    public String toString() {
        return "Preference{id=" + id + ", entiteType='" + entiteType + "', entiteId=" + entiteId + ", typePreference='"
                + typePreference + "', valeur='" + valeur + "', priorite=" + priorite + "}";
    }
}
