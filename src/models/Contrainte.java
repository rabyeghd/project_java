// Classe Contrainte : Gère les données du système
package models;

public class Contrainte {
    private int id;
    private String type;
    private String description;
    private String entiteType;
    private int entiteId;
    private int priorite;
    private boolean active;

    public Contrainte() {
    }

    public Contrainte(String type, String description, String entiteType, int entiteId, int priorite, boolean active) {
        this.type = type;
        this.description = description;
        this.entiteType = entiteType;
        this.entiteId = entiteId;
        this.priorite = priorite;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Contrainte{id=" + id + ", type='" + type + "', description='" + description + "', entiteType='"
                + entiteType + "', entiteId=" + entiteId + ", priorite=" + priorite + ", active=" + active + "}";
    }
}
// Fin de la classe Contrainte

