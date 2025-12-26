package com.formation.models;

/**
 * Represents a scheduled session in the planning
 */
public class SessionPlanifiee {
    private Long id;
    private Cours cours;
    private Formateur formateur;
    private Salle salle;
    private TimeSlot timeSlot;
    
    public SessionPlanifiee() {
    }
    
    public SessionPlanifiee(Cours cours, Formateur formateur, Salle salle, TimeSlot timeSlot) {
        this.cours = cours;
        this.formateur = formateur;
        this.salle = salle;
        this.timeSlot = timeSlot;
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Cours getCours() {
        return cours;
    }
    
    public void setCours(Cours cours) {
        this.cours = cours;
    }
    
    public Formateur getFormateur() {
        return formateur;
    }
    
    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }
    
    public Salle getSalle() {
        return salle;
    }
    
    public void setSalle(Salle salle) {
        this.salle = salle;
    }
    
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
    
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    @Override
    public String toString() {
        return "SessionPlanifiee{" +
               "cours=" + (cours != null ? cours.getNom() : "null") +
               ", formateur=" + (formateur != null ? formateur.getNom() : "null") +
               ", salle=" + (salle != null ? salle.getNom() : "null") +
               ", timeSlot=" + timeSlot +
               '}';
    }
}
