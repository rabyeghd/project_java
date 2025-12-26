package com.formation.models;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a classroom/room in the training center
 */
public class Salle {
    private Long id;
    private String nom;
    private int capacite;
    private Set<String> equipements;
    
    public Salle() {
        this.equipements = new HashSet<>();
    }
    
    public Salle(Long id, String nom, int capacite) {
        this.id = id;
        this.nom = nom;
        this.capacite = capacite;
        this.equipements = new HashSet<>();
    }
    
    /**
     * Check if the room has specific equipment
     */
    public boolean hasEquipment(String equipment) {
        return equipements.contains(equipment);
    }
    
    /**
     * Check if the room has all required equipment
     */
    public boolean hasAllEquipment(Set<String> requiredEquipment) {
        return equipements.containsAll(requiredEquipment);
    }
    
    public void addEquipement(String equipement) {
        this.equipements.add(equipement);
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
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
    
    public Set<String> getEquipements() {
        return equipements;
    }
    
    public void setEquipements(Set<String> equipements) {
        this.equipements = equipements;
    }
    
    @Override
    public String toString() {
        return "Salle{" +
               "id=" + id +
               ", nom='" + nom + '\'' +
               ", capacite=" + capacite +
               ", equipements=" + equipements +
               '}';
    }
}
