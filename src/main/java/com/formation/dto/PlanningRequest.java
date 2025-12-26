package com.formation.dto;

import com.formation.models.*;
import java.util.List;

/**
 * Data Transfer Object for planning generation request
 */
public class PlanningRequest {
    private List<Cours> cours;
    private List<Formateur> formateurs;
    private List<Salle> salles;
    private List<Etudiant> etudiants;
    private List<TimeSlot> creneauxPersonnalises; // Optional custom time slots
    
    public PlanningRequest() {
    }
    
    // Getters and setters
    public List<Cours> getCours() {
        return cours;
    }
    
    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }
    
    public List<Formateur> getFormateurs() {
        return formateurs;
    }
    
    public void setFormateurs(List<Formateur> formateurs) {
        this.formateurs = formateurs;
    }
    
    public List<Salle> getSalles() {
        return salles;
    }
    
    public void setSalles(List<Salle> salles) {
        this.salles = salles;
    }
    
    public List<Etudiant> getEtudiants() {
        return etudiants;
    }
    
    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }
    
    public List<TimeSlot> getCreneauxPersonnalises() {
        return creneauxPersonnalises;
    }
    
    public void setCreneauxPersonnalises(List<TimeSlot> creneauxPersonnalises) {
        this.creneauxPersonnalises = creneauxPersonnalises;
    }
}
