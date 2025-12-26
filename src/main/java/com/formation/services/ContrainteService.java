package com.formation.services;

import com.formation.models.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for checking constraints and detecting conflicts in the planning
 */
@Service
public class ContrainteService {
    
    /**
     * Represents a conflict or constraint violation
     */
    public static class Conflit {
        private String type;
        private String description;
        private String severite; // "CRITIQUE", "MAJEUR", "MINEUR"
        private SessionPlanifiee session1;
        private SessionPlanifiee session2;
        
        public Conflit(String type, String description, String severite) {
            this.type = type;
            this.description = description;
            this.severite = severite;
        }
        
        public Conflit(String type, String description, String severite, 
                      SessionPlanifiee s1, SessionPlanifiee s2) {
            this(type, description, severite);
            this.session1 = s1;
            this.session2 = s2;
        }
        
        // Getters and setters
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
        
        public String getSeverite() {
            return severite;
        }
        
        public void setSeverite(String severite) {
            this.severite = severite;
        }
        
        public SessionPlanifiee getSession1() {
            return session1;
        }
        
        public void setSession1(SessionPlanifiee session1) {
            this.session1 = session1;
        }
        
        public SessionPlanifiee getSession2() {
            return session2;
        }
        
        public void setSession2(SessionPlanifiee session2) {
            this.session2 = session2;
        }
        
        @Override
        public String toString() {
            return "[" + severite + "] " + type + ": " + description;
        }
    }
    
    /**
     * Check if a session can be added to the schedule without conflicts
     */
    public List<Conflit> verifierContraintes(SessionPlanifiee nouvelleSession, 
                                              List<SessionPlanifiee> planning) {
        List<Conflit> conflits = new ArrayList<>();
        
        // Check formateur availability
        if (!nouvelleSession.getFormateur().isAvailable(nouvelleSession.getTimeSlot())) {
            conflits.add(new Conflit(
                "FORMATEUR_INDISPONIBLE", 
                "Le formateur " + nouvelleSession.getFormateur().getNom() + 
                " n'est pas disponible à ce créneau: " + nouvelleSession.getTimeSlot(),
                "CRITIQUE"
            ));
        }
        
        // Check for overlapping sessions
        for (SessionPlanifiee session : planning) {
            if (nouvelleSession.getTimeSlot().overlaps(session.getTimeSlot())) {
                
                // Same formateur conflict
                if (nouvelleSession.getFormateur().getId().equals(session.getFormateur().getId())) {
                    conflits.add(new Conflit(
                        "FORMATEUR_DOUBLE_RESERVATION",
                        "Le formateur " + nouvelleSession.getFormateur().getNom() + 
                        " a déjà un cours '" + session.getCours().getNom() + 
                        "' à ce créneau: " + nouvelleSession.getTimeSlot(),
                        "CRITIQUE",
                        nouvelleSession, 
                        session
                    ));
                }
                
                // Same salle conflict
                if (nouvelleSession.getSalle().getId().equals(session.getSalle().getId())) {
                    conflits.add(new Conflit(
                        "SALLE_DOUBLE_RESERVATION",
                        "La salle " + nouvelleSession.getSalle().getNom() + 
                        " est déjà réservée pour le cours '" + session.getCours().getNom() + 
                        "' à ce créneau: " + nouvelleSession.getTimeSlot(),
                        "CRITIQUE",
                        nouvelleSession, 
                        session
                    ));
                }
                
                // Check student conflicts (same student in two courses at same time)
                Set<Long> etudiantsCommuns = new HashSet<>(nouvelleSession.getCours().getEtudiantIds());
                etudiantsCommuns.retainAll(session.getCours().getEtudiantIds());
                
                if (!etudiantsCommuns.isEmpty()) {
                    conflits.add(new Conflit(
                        "ETUDIANT_DOUBLE_RESERVATION",
                        etudiantsCommuns.size() + " étudiant(s) sont inscrits dans deux cours simultanés: '" + 
                        nouvelleSession.getCours().getNom() + "' et '" + session.getCours().getNom() + "'",
                        "CRITIQUE",
                        nouvelleSession, 
                        session
                    ));
                }
            }
        }
        
        // Check room capacity
        if (nouvelleSession.getCours().getNombreEtudiants() > nouvelleSession.getSalle().getCapacite()) {
            conflits.add(new Conflit(
                "CAPACITE_INSUFFISANTE",
                "La salle " + nouvelleSession.getSalle().getNom() + 
                " ne peut accueillir que " + nouvelleSession.getSalle().getCapacite() + 
                " étudiants, mais le cours '" + nouvelleSession.getCours().getNom() + 
                "' nécessite " + nouvelleSession.getCours().getNombreEtudiants() + " places",
                "MAJEUR"
            ));
        }
        
        // Check required equipment
        for (String materiel : nouvelleSession.getCours().getMaterielRequis()) {
            if (!nouvelleSession.getSalle().hasEquipment(materiel)) {
                conflits.add(new Conflit(
                    "MATERIEL_MANQUANT",
                    "La salle " + nouvelleSession.getSalle().getNom() + 
                    " ne dispose pas de: " + materiel + 
                    " (requis pour le cours '" + nouvelleSession.getCours().getNom() + "')",
                    "MAJEUR"
                ));
            }
        }
        
        return conflits;
    }
    
    /**
     * Check all constraints without considering overlaps (for pre-validation)
     */
    public List<Conflit> verifierContraintesSimples(SessionPlanifiee session) {
        List<Conflit> conflits = new ArrayList<>();
        
        // Check formateur availability
        if (!session.getFormateur().isAvailable(session.getTimeSlot())) {
            conflits.add(new Conflit(
                "FORMATEUR_INDISPONIBLE", 
                "Le formateur " + session.getFormateur().getNom() + 
                " n'est pas disponible à ce créneau: " + session.getTimeSlot(),
                "CRITIQUE"
            ));
        }
        
        // Check room capacity
        if (session.getCours().getNombreEtudiants() > session.getSalle().getCapacite()) {
            conflits.add(new Conflit(
                "CAPACITE_INSUFFISANTE",
                "La salle " + session.getSalle().getNom() + 
                " ne peut accueillir que " + session.getSalle().getCapacite() + 
                " étudiants, mais le cours nécessite " + 
                session.getCours().getNombreEtudiants() + " places",
                "MAJEUR"
            ));
        }
        
        // Check required equipment
        for (String materiel : session.getCours().getMaterielRequis()) {
            if (!session.getSalle().hasEquipment(materiel)) {
                conflits.add(new Conflit(
                    "MATERIEL_MANQUANT",
                    "La salle " + session.getSalle().getNom() + 
                    " ne dispose pas de: " + materiel,
                    "MAJEUR"
                ));
            }
        }
        
        return conflits;
    }
    
    /**
     * Calculate a quality score for a session placement (higher is better)
     */
    public double calculerScore(SessionPlanifiee session) {
        double score = 0.0;
        
        // Bonus if formateur prefers this time slot
        if (session.getFormateur().prefersSlot(session.getTimeSlot())) {
            score += 10.0;
        }
        
        // Bonus for course priority
        score += session.getCours().getPriorite() * 5.0;
        
        // Bonus for efficient room usage (closer to capacity is better)
        double roomUtilization = (double) session.getCours().getNombreEtudiants() / 
                                 session.getSalle().getCapacite();
        score += roomUtilization * 15.0;
        
        // Penalty for under-utilization (wasting space)
        if (roomUtilization < 0.5) {
            score -= (0.5 - roomUtilization) * 10.0;
        }
        
        return score;
    }
    
    /**
     * Detect all conflicts in the complete planning
     */
    public List<Conflit> detecterTousLesConflits(List<SessionPlanifiee> planning) {
        List<Conflit> tousConflits = new ArrayList<>();
        Set<String> conflitsVus = new HashSet<>(); // To avoid duplicates
        
        for (int i = 0; i < planning.size(); i++) {
            SessionPlanifiee session = planning.get(i);
            
            // Create a planning without current session for checking
            List<SessionPlanifiee> autresSessions = new ArrayList<>(planning);
            autresSessions.remove(i);
            
            List<Conflit> conflits = verifierContraintes(session, autresSessions);
            
            // Add unique conflicts only
            for (Conflit conflit : conflits) {
                String conflitKey = conflit.getType() + "_" + 
                                   (session.getCours() != null ? session.getCours().getId() : "null");
                if (!conflitsVus.contains(conflitKey)) {
                    tousConflits.add(conflit);
                    conflitsVus.add(conflitKey);
                }
            }
        }
        
        return tousConflits;
    }
    
    /**
     * Check if planning is valid (no critical conflicts)
     */
    public boolean estValide(List<SessionPlanifiee> planning) {
        List<Conflit> conflits = detecterTousLesConflits(planning);
        return conflits.stream().noneMatch(c -> "CRITIQUE".equals(c.getSeverite()));
    }
    
    /**
     * Get conflicts grouped by severity
     */
    public Map<String, List<Conflit>> grouperConflitsParSeverite(List<Conflit> conflits) {
        return conflits.stream().collect(Collectors.groupingBy(Conflit::getSeverite));
    }
}
