package com.formation.models;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a trainer/instructor in the training center
 */
public class Formateur {
    private Long id;
    private String nom;
    private String specialite;
    private Set<TimeSlot> disponibilites; // Available time slots
    private Set<TimeSlot> preferences;     // Preferred time slots
    
    public Formateur() {
        this.disponibilites = new HashSet<>();
        this.preferences = new HashSet<>();
    }
    
    public Formateur(Long id, String nom, String specialite) {
        this.id = id;
        this.nom = nom;
        this.specialite = specialite;
        this.disponibilites = new HashSet<>();
        this.preferences = new HashSet<>();
    }
    
    /**
     * Check if the trainer is available during a specific time slot
     */
    public boolean isAvailable(TimeSlot slot) {
        if (disponibilites.isEmpty()) {
            return true; // If no constraints specified, assume always available
        }
        for (TimeSlot dispo : disponibilites) {
            if (dispo.contains(slot) || dispo.equals(slot)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if the trainer prefers this time slot
     */
    public boolean prefersSlot(TimeSlot slot) {
        return preferences.stream().anyMatch(p -> p.equals(slot) || p.contains(slot));
    }
    
    public void addDisponibilite(TimeSlot slot) {
        this.disponibilites.add(slot);
    }
    
    public void addPreference(TimeSlot slot) {
        this.preferences.add(slot);
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
    
    public String getSpecialite() {
        return specialite;
    }
    
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
    
    public Set<TimeSlot> getDisponibilites() {
        return disponibilites;
    }
    
    public void setDisponibilites(Set<TimeSlot> disponibilites) {
        this.disponibilites = disponibilites;
    }
    
    public Set<TimeSlot> getPreferences() {
        return preferences;
    }
    
    public void setPreferences(Set<TimeSlot> preferences) {
        this.preferences = preferences;
    }
    
    @Override
    public String toString() {
        return "Formateur{" +
               "id=" + id +
               ", nom='" + nom + '\'' +
               ", specialite='" + specialite + '\'' +
               '}';
    }
}
