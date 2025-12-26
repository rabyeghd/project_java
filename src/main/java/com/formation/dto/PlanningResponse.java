package com.formation.dto;

import com.formation.models.*;
import com.formation.services.ContrainteService;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for planning generation response
 */
public class PlanningResponse {
    private List<SessionPlanifiee> planning;
    private List<Cours> coursNonPlanifies;
    private List<ContrainteService.Conflit> conflits;
    private Map<String, Object> statistiques;
    private long tempsExecution;
    private String statut;
    private String message;
    
    public PlanningResponse() {
    }
    
    // Getters and setters
    public List<SessionPlanifiee> getPlanning() {
        return planning;
    }
    
    public void setPlanning(List<SessionPlanifiee> planning) {
        this.planning = planning;
    }
    
    public List<Cours> getCoursNonPlanifies() {
        return coursNonPlanifies;
    }
    
    public void setCoursNonPlanifies(List<Cours> coursNonPlanifies) {
        this.coursNonPlanifies = coursNonPlanifies;
    }
    
    public List<ContrainteService.Conflit> getConflits() {
        return conflits;
    }
    
    public void setConflits(List<ContrainteService.Conflit> conflits) {
        this.conflits = conflits;
    }
    
    public Map<String, Object> getStatistiques() {
        return statistiques;
    }
    
    public void setStatistiques(Map<String, Object> statistiques) {
        this.statistiques = statistiques;
    }
    
    public long getTempsExecution() {
        return tempsExecution;
    }
    
    public void setTempsExecution(long tempsExecution) {
        this.tempsExecution = tempsExecution;
    }
    
    public String getStatut() {
        return statut;
    }
    
    public void setStatut(String statut) {
        this.statut = statut;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
