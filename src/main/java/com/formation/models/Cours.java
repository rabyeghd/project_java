package com.formation.models;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a course/training session that needs to be scheduled
 */
public class Cours {
    private Long id;
    private String nom;
    private int duree; // Duration in minutes
    private Formateur formateurRequis; // Required trainer (can be null if any trainer works)
    private int nombreEtudiants;
    private Set<String> materielRequis; // Required equipment
    private int priorite; // Higher = more important (default 1)
    private Set<Long> etudiantIds; // Students enrolled in this course
    
    public Cours() {
        this.materielRequis = new HashSet<>();
        this.etudiantIds = new HashSet<>();
        this.priorite = 1;
    }
    
    public Cours(Long id, String nom, int duree, int nombreEtudiants) {
        this.id = id;
        this.nom = nom;
        this.duree = duree;
        this.nombreEtudiants = nombreEtudiants;
        this.materielRequis = new HashSet<>();
        this.etudiantIds = new HashSet<>();
        this.priorite = 1;
    }
    
    public void addMateriel(String materiel) {
        this.materielRequis.add(materiel);
    }
    
    public void addEtudiant(Long etudiantId) {
        this.etudiantIds.add(etudiantId);
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
    
    public int getDuree() {
        return duree;
    }
    
    public void setDuree(int duree) {
        this.duree = duree;
    }
    
    public Formateur getFormateurRequis() {
        return formateurRequis;
    }
    
    public void setFormateurRequis(Formateur formateurRequis) {
        this.formateurRequis = formateurRequis;
    }
    
    public int getNombreEtudiants() {
        return nombreEtudiants;
    }
    
    public void setNombreEtudiants(int nombreEtudiants) {
        this.nombreEtudiants = nombreEtudiants;
    }
    
    public Set<String> getMaterielRequis() {
        return materielRequis;
    }
    
    public void setMaterielRequis(Set<String> materielRequis) {
        this.materielRequis = materielRequis;
    }
    
    public int getPriorite() {
        return priorite;
    }
    
    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }
    
    public Set<Long> getEtudiantIds() {
        return etudiantIds;
    }
    
    public void setEtudiantIds(Set<Long> etudiantIds) {
        this.etudiantIds = etudiantIds;
    }
    
    @Override
    public String toString() {
        return "Cours{" +
               "id=" + id +
               ", nom='" + nom + '\'' +
               ", duree=" + duree +
               ", nombreEtudiants=" + nombreEtudiants +
               ", priorite=" + priorite +
               '}';
    }
}
