package com.formation.models;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a student in the training center
 */
public class Etudiant {
    private Long id;
    private String nom;
    private String groupe;
    private Set<Long> compatibilites; // IDs of other students they can be grouped with
    
    public Etudiant() {
        this.compatibilites = new HashSet<>();
    }
    
    public Etudiant(Long id, String nom, String groupe) {
        this.id = id;
        this.nom = nom;
        this.groupe = groupe;
        this.compatibilites = new HashSet<>();
    }
    
    public void addCompatibilite(Long etudiantId) {
        this.compatibilites.add(etudiantId);
    }
    
    public boolean isCompatibleWith(Long etudiantId) {
        return compatibilites.isEmpty() || compatibilites.contains(etudiantId);
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
    
    public String getGroupe() {
        return groupe;
    }
    
    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }
    
    public Set<Long> getCompatibilites() {
        return compatibilites;
    }
    
    public void setCompatibilites(Set<Long> compatibilites) {
        this.compatibilites = compatibilites;
    }
    
    @Override
    public String toString() {
        return "Etudiant{" +
               "id=" + id +
               ", nom='" + nom + '\'' +
               ", groupe='" + groupe + '\'' +
               '}';
    }
}
