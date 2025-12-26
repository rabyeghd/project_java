package com.formation.services;

import com.formation.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main optimization service for generating optimal schedules
 * Uses a hybrid approach: Heuristic prioritization + Backtracking + Local optimization
 */
@Service
public class PlanningOptimisationService {
    
    @Autowired
    private ContrainteService contrainteService;
    
    private List<TimeSlot> creneauxDisponibles;
    
    public PlanningOptimisationService() {
        this.creneauxDisponibles = genererCreneauxStandard();
    }
    
    /**
     * Generate standard time slots (Monday-Friday, 8AM-6PM, 2-hour blocks)
     */
    private List<TimeSlot> genererCreneauxStandard() {
        List<TimeSlot> creneaux = new ArrayList<>();
        DayOfWeek[] jours = {
            DayOfWeek.MONDAY, 
            DayOfWeek.TUESDAY, 
            DayOfWeek.WEDNESDAY, 
            DayOfWeek.THURSDAY, 
            DayOfWeek.FRIDAY
        };
        
        for (DayOfWeek jour : jours) {
            creneaux.add(new TimeSlot(jour, LocalTime.of(8, 0), LocalTime.of(10, 0)));
            creneaux.add(new TimeSlot(jour, LocalTime.of(10, 0), LocalTime.of(12, 0)));
            creneaux.add(new TimeSlot(jour, LocalTime.of(14, 0), LocalTime.of(16, 0)));
            creneaux.add(new TimeSlot(jour, LocalTime.of(16, 0), LocalTime.of(18, 0)));
        }
        
        return creneaux;
    }
    
    /**
     * Set custom time slots for the week
     */
    public void setCreneauxDisponibles(List<TimeSlot> creneaux) {
        this.creneauxDisponibles = new ArrayList<>(creneaux);
    }
    
    /**
     * Result object containing the optimized planning and statistics
     */
    public static class ResultatOptimisation {
        private List<SessionPlanifiee> planning;
        private List<Cours> coursNonPlanifies;
        private List<ContrainteService.Conflit> conflits;
        private Map<String, Object> statistiques;
        private long tempsExecution; // milliseconds
        private String statut; // "SUCCES", "PARTIEL", "ECHEC"
        
        public ResultatOptimisation() {
            this.planning = new ArrayList<>();
            this.coursNonPlanifies = new ArrayList<>();
            this.conflits = new ArrayList<>();
            this.statistiques = new HashMap<>();
            this.statut = "SUCCES";
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
    }
    
    /**
     * Main entry point: Generate optimized planning
     */
    public ResultatOptimisation genererPlanningOptimise(
            List<Cours> cours,
            List<Formateur> formateurs,
            List<Salle> salles) {
        
        long startTime = System.currentTimeMillis();
        ResultatOptimisation resultat = new ResultatOptimisation();
        
        System.out.println("=== Début de la génération du planning ===");
        System.out.println("Cours à planifier: " + cours.size());
        System.out.println("Formateurs disponibles: " + formateurs.size());
        System.out.println("Salles disponibles: " + salles.size());
        System.out.println("Créneaux disponibles: " + creneauxDisponibles.size());
        
        // Step 1: Sort courses by constraints (Most Constrained First heuristic)
        List<Cours> coursTries = trierCoursParContraintes(cours, formateurs);
        System.out.println("\nÉtape 1: Tri des cours par niveau de contrainte - OK");
        
        // Step 2: Try to schedule each course using backtracking
        List<SessionPlanifiee> planning = new ArrayList<>();
        
        for (int i = 0; i < coursTries.size(); i++) {
            Cours coursActuel = coursTries.get(i);
            System.out.println("\nPlanification du cours " + (i + 1) + "/" + coursTries.size() + 
                             ": " + coursActuel.getNom());
            
            SessionPlanifiee sessionPlanifiee = planifierCours(
                coursActuel, formateurs, salles, planning
            );
            
            if (sessionPlanifiee != null) {
                planning.add(sessionPlanifiee);
                System.out.println("  ✓ Planifié: " + sessionPlanifiee.getFormateur().getNom() + 
                                 " - " + sessionPlanifiee.getSalle().getNom() + 
                                 " - " + sessionPlanifiee.getTimeSlot());
            } else {
                resultat.getCoursNonPlanifies().add(coursActuel);
                System.out.println("  ✗ Impossible à planifier (contraintes non satisfiables)");
            }
        }
        
        resultat.setPlanning(planning);
        System.out.println("\nÉtape 2: Planification initiale - " + planning.size() + 
                         "/" + cours.size() + " cours planifiés");
        
        // Step 3: Local optimization - try to improve the solution
        if (!planning.isEmpty()) {
            System.out.println("\nÉtape 3: Optimisation locale...");
            int ameliorations = optimiserLocalement(resultat.getPlanning());
            System.out.println("  Nombre d'améliorations: " + ameliorations);
        }
        
        // Step 4: Detect any remaining conflicts
        resultat.setConflits(contrainteService.detecterTousLesConflits(resultat.getPlanning()));
        System.out.println("\nÉtape 4: Détection des conflits - " + 
                         resultat.getConflits().size() + " conflit(s) détecté(s)");
        
        // Step 5: Calculate statistics
        resultat.setStatistiques(calculerStatistiques(
            resultat.getPlanning(), 
            salles, 
            formateurs,
            cours.size()
        ));
        System.out.println("\nÉtape 5: Calcul des statistiques - OK");
        
        // Determine status
        if (resultat.getCoursNonPlanifies().isEmpty() && resultat.getConflits().isEmpty()) {
            resultat.setStatut("SUCCES");
        } else if (!resultat.getPlanning().isEmpty()) {
            resultat.setStatut("PARTIEL");
        } else {
            resultat.setStatut("ECHEC");
        }
        
        long endTime = System.currentTimeMillis();
        resultat.setTempsExecution(endTime - startTime);
        
        System.out.println("\n=== Génération terminée ===");
        System.out.println("Statut: " + resultat.getStatut());
        System.out.println("Temps d'exécution: " + resultat.getTempsExecution() + " ms");
        
        return resultat;
    }
    
    /**
     * Sort courses by number of constraints (most constrained first)
     * This heuristic improves the chances of finding a valid solution
     */
    private List<Cours> trierCoursParContraintes(List<Cours> cours, List<Formateur> formateurs) {
        return cours.stream()
            .sorted((c1, c2) -> {
                int score1 = calculerScoreContrainte(c1, formateurs);
                int score2 = calculerScoreContrainte(c2, formateurs);
                // Higher constraint score = schedule first
                return Integer.compare(score2, score1);
            })
            .collect(Collectors.toList());
    }
    
    /**
     * Calculate constraint score for a course (higher = more constrained)
     */
    private int calculerScoreContrainte(Cours cours, List<Formateur> formateurs) {
        int score = 0;
        
        // Higher priority courses get higher scores
        score += cours.getPriorite() * 10;
        
        // Courses with specific formateur requirements are more constrained
        if (cours.getFormateurRequis() != null) {
            score += 20;
            // Limited availability = higher constraint
            int nbCreneauxDispo = cours.getFormateurRequis().getDisponibilites().size();
            if (nbCreneauxDispo > 0) {
                score += (40 - nbCreneauxDispo * 2);
            }
        }
        
        // More students = more constrained (fewer rooms available)
        score += cours.getNombreEtudiants() / 5;
        
        // More equipment required = more constrained
        score += cours.getMaterielRequis().size() * 5;
        
        // More enrolled students = more constrained
        score += cours.getEtudiantIds().size() / 3;
        
        return score;
    }
    
    /**
     * Try to schedule a single course using backtracking
     */
    private SessionPlanifiee planifierCours(
            Cours cours,
            List<Formateur> formateurs,
            List<Salle> salles,
            List<SessionPlanifiee> planningActuel) {
        
        // Determine possible formateurs
        List<Formateur> formateursDisponibles = cours.getFormateurRequis() != null
            ? Collections.singletonList(cours.getFormateurRequis())
            : new ArrayList<>(formateurs);
        
        // Filter salles by capacity and equipment
        List<Salle> sallesCompatibles = salles.stream()
            .filter(s -> s.getCapacite() >= cours.getNombreEtudiants())
            .filter(s -> s.hasAllEquipment(cours.getMaterielRequis()))
            .collect(Collectors.toList());
        
        if (sallesCompatibles.isEmpty()) {
            System.out.println("    Aucune salle compatible trouvée");
            return null;
        }
        
        // Try each combination of formateur, salle, and time slot
        SessionPlanifiee meilleureSession = null;
        double meilleurScore = Double.NEGATIVE_INFINITY;
        
        for (Formateur formateur : formateursDisponibles) {
            for (Salle salle : sallesCompatibles) {
                for (TimeSlot creneau : creneauxDisponibles) {
                    SessionPlanifiee candidat = new SessionPlanifiee(cours, formateur, salle, creneau);
                    
                    // Check constraints
                    List<ContrainteService.Conflit> conflits = 
                        contrainteService.verifierContraintes(candidat, planningActuel);
                    
                    if (conflits.isEmpty()) {
                        // Valid assignment - calculate score
                        double score = contrainteService.calculerScore(candidat);
                        
                        if (score > meilleurScore) {
                            meilleurScore = score;
                            meilleureSession = candidat;
                        }
                    }
                }
            }
        }
        
        return meilleureSession;
    }
    
    /**
     * Local optimization - try swapping sessions to improve overall score
     * Returns the number of improvements made
     */
    private int optimiserLocalement(List<SessionPlanifiee> planning) {
        boolean amelioration = true;
        int iterations = 0;
        int totalAmeliorations = 0;
        int maxIterations = 100;
        
        while (amelioration && iterations < maxIterations) {
            amelioration = false;
            iterations++;
            
            // Try swapping time slots between pairs of sessions
            for (int i = 0; i < planning.size(); i++) {
                for (int j = i + 1; j < planning.size(); j++) {
                    SessionPlanifiee session1 = planning.get(i);
                    SessionPlanifiee session2 = planning.get(j);
                    
                    // Calculate current score
                    double scoreAvant = contrainteService.calculerScore(session1) + 
                                       contrainteService.calculerScore(session2);
                    
                    // Try swapping time slots
                    SessionPlanifiee nouvelle1 = new SessionPlanifiee(
                        session1.getCours(), 
                        session1.getFormateur(), 
                        session1.getSalle(), 
                        session2.getTimeSlot()
                    );
                    SessionPlanifiee nouvelle2 = new SessionPlanifiee(
                        session2.getCours(), 
                        session2.getFormateur(), 
                        session2.getSalle(), 
                        session1.getTimeSlot()
                    );
                    
                    // Create temporary planning for validation
                    List<SessionPlanifiee> planningTemp = new ArrayList<>(planning);
                    planningTemp.set(i, nouvelle1);
                    planningTemp.set(j, nouvelle2);
                    
                    // Check if swap is valid
                    List<ContrainteService.Conflit> conflits1 = 
                        contrainteService.verifierContraintes(nouvelle1, 
                            planningTemp.stream()
                                .filter(s -> s != nouvelle1)
                                .collect(Collectors.toList()));
                    List<ContrainteService.Conflit> conflits2 = 
                        contrainteService.verifierContraintes(nouvelle2, 
                            planningTemp.stream()
                                .filter(s -> s != nouvelle2)
                                .collect(Collectors.toList()));
                    
                    if (conflits1.isEmpty() && conflits2.isEmpty()) {
                        double scoreApres = contrainteService.calculerScore(nouvelle1) + 
                                           contrainteService.calculerScore(nouvelle2);
                        
                        if (scoreApres > scoreAvant + 0.01) { // Small threshold to avoid floating point issues
                            // Accept the swap
                            planning.set(i, nouvelle1);
                            planning.set(j, nouvelle2);
                            amelioration = true;
                            totalAmeliorations++;
                        }
                    }
                }
            }
        }
        
        return totalAmeliorations;
    }
    
    /**
     * Calculate comprehensive usage statistics
     */
    private Map<String, Object> calculerStatistiques(
            List<SessionPlanifiee> planning,
            List<Salle> salles,
            List<Formateur> formateurs,
            int totalCours) {
        
        Map<String, Object> stats = new HashMap<>();
        
        // Basic counts
        stats.put("nombreSessionsPlanifiees", planning.size());
        stats.put("nombreCoursTotal", totalCours);
        stats.put("tauxPlanification", totalCours > 0 ? 
            (double) planning.size() / totalCours * 100 : 0.0);
        
        // Room utilization
        Map<Long, Long> utilisationSalles = planning.stream()
            .collect(Collectors.groupingBy(
                s -> s.getSalle().getId(),
                Collectors.counting()
            ));
        
        stats.put("nombreSallesUtilisees", utilisationSalles.size());
        stats.put("nombreSallesDisponibles", salles.size());
        
        double tauxUtilisationMoyen = utilisationSalles.values().stream()
            .mapToLong(Long::longValue)
            .average()
            .orElse(0.0);
        stats.put("tauxUtilisationMoyenSalles", tauxUtilisationMoyen);
        
        // Formateur utilization
        Map<Long, Long> utilisationFormateurs = planning.stream()
            .collect(Collectors.groupingBy(
                s -> s.getFormateur().getId(),
                Collectors.counting()
            ));
        
        stats.put("nombreFormateursUtilises", utilisationFormateurs.size());
        stats.put("nombreFormateursDisponibles", formateurs.size());
        
        // Formateur satisfaction (preferences matched)
        long preferencesRespectees = planning.stream()
            .filter(s -> s.getFormateur().prefersSlot(s.getTimeSlot()))
            .count();
        
        double tauxSatisfaction = planning.isEmpty() ? 0.0 : 
            (double) preferencesRespectees / planning.size() * 100;
        stats.put("tauxSatisfactionPreferences", tauxSatisfaction);
        stats.put("nombrePreferencesRespectees", preferencesRespectees);
        
        // Room capacity efficiency
        double efficaciteCapacite = planning.stream()
            .mapToDouble(s -> (double) s.getCours().getNombreEtudiants() / s.getSalle().getCapacite())
            .average()
            .orElse(0.0) * 100;
        stats.put("efficaciteCapaciteSalles", efficaciteCapacite);
        
        // Time slot distribution
        Map<DayOfWeek, Long> repartitionJours = planning.stream()
            .collect(Collectors.groupingBy(
                s -> s.getTimeSlot().getDay(),
                Collectors.counting()
            ));
        stats.put("repartitionParJour", repartitionJours);
        
        // Overall quality score
        double scoreQualite = planning.stream()
            .mapToDouble(contrainteService::calculerScore)
            .average()
            .orElse(0.0);
        stats.put("scoreQualiteMoyen", scoreQualite);
        
        return stats;
    }
    
    /**
     * Get available time slots
     */
    public List<TimeSlot> getCreneauxDisponibles() {
        return new ArrayList<>(creneauxDisponibles);
    }
}
