package com.formation.test;

import com.formation.controllers.PlanningController;
import com.formation.dto.*;
import com.formation.models.*;
import com.formation.services.*;
import com.formation.utils.TestDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

/**
 * Test class to demonstrate the optimization algorithm
 * This class runs automatically when the application starts
 */
@Component
public class AlgorithmDemo implements CommandLineRunner {
    
    @Autowired
    private PlanningOptimisationService optimisationService;
    
    @Autowired
    private ContrainteService contrainteService;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║    DÉMONSTRATION ALGORITHME D'OPTIMISATION DE PLANNING    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        // Run different test scenarios
        runScenario1_SimpleCase();
        runScenario2_HighLoad();
        runScenario3_ConflictDetection();
    }
    
    /**
     * Scenario 1: Simple case with few courses
     */
    private void runScenario1_SimpleCase() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("SCÉNARIO 1: Cas simple (3 formateurs, 2 salles, 5 cours)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        // Generate test data
        List<Formateur> formateurs = TestDataGenerator.genererFormateurs(3);
        List<Salle> salles = TestDataGenerator.genererSalles(2);
        List<Cours> cours = TestDataGenerator.genererCours(5, formateurs);
        
        // Run optimization
        PlanningOptimisationService.ResultatOptimisation resultat = 
            optimisationService.genererPlanningOptimise(cours, formateurs, salles);
        
        // Display results
        afficherResultats(resultat);
    }
    
    /**
     * Scenario 2: High load with many courses
     */
    private void runScenario2_HighLoad() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("SCÉNARIO 2: Charge élevée (5 formateurs, 4 salles, 15 cours)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        List<Formateur> formateurs = TestDataGenerator.genererFormateurs(5);
        List<Salle> salles = TestDataGenerator.genererSalles(4);
        List<Cours> cours = TestDataGenerator.genererCours(15, formateurs);
        
        PlanningOptimisationService.ResultatOptimisation resultat = 
            optimisationService.genererPlanningOptimise(cours, formateurs, salles);
        
        afficherResultats(resultat);
    }
    
    /**
     * Scenario 3: Conflict detection with limited availability
     */
    private void runScenario3_ConflictDetection() {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("SCÉNARIO 3: Détection de conflits (disponibilités limitées)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        // Create formateurs with limited availability
        List<Formateur> formateurs = new ArrayList<>();
        Formateur f1 = new Formateur(1L, "Dr. Martin", "Java");
        // Only available Monday and Wednesday mornings
        f1.addDisponibilite(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)));
        f1.addDisponibilite(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)));
        formateurs.add(f1);
        
        Formateur f2 = new Formateur(2L, "Prof. Dubois", "Python");
        // Available full week
        for (DayOfWeek jour : DayOfWeek.values()) {
            if (jour != DayOfWeek.SATURDAY && jour != DayOfWeek.SUNDAY) {
                f2.addDisponibilite(new TimeSlot(jour, LocalTime.of(8, 0), LocalTime.of(18, 0)));
            }
        }
        formateurs.add(f2);
        
        // Create small rooms
        List<Salle> salles = new ArrayList<>();
        Salle s1 = new Salle(1L, "Salle A", 15);
        s1.addEquipement("Projecteur");
        s1.addEquipement("Tableau");
        salles.add(s1);
        
        Salle s2 = new Salle(2L, "Salle B", 10);
        s2.addEquipement("Projecteur");
        salles.add(s2);
        
        // Create courses
        List<Cours> cours = new ArrayList<>();
        
        Cours c1 = new Cours(1L, "Java Avancé", 120, 12);
        c1.setFormateurRequis(f1);
        c1.addMateriel("Projecteur");
        c1.setPriorite(3);
        cours.add(c1);
        
        Cours c2 = new Cours(2L, "Java Débutant", 120, 20); // Too many students for available rooms!
        c2.setFormateurRequis(f1);
        c2.setPriorite(2);
        cours.add(c2);
        
        Cours c3 = new Cours(3L, "Python Web", 120, 8);
        c3.setFormateurRequis(f2);
        c3.addMateriel("Ordinateurs"); // Equipment not available!
        cours.add(c3);
        
        PlanningOptimisationService.ResultatOptimisation resultat = 
            optimisationService.genererPlanningOptimise(cours, formateurs, salles);
        
        afficherResultats(resultat);
    }
    
    /**
     * Display optimization results in a readable format
     */
    private void afficherResultats(PlanningOptimisationService.ResultatOptimisation resultat) {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                    RÉSULTATS                             │");
        System.out.println("└─────────────────────────────────────────────────────────┘\n");
        
        System.out.println("Statut: " + resultat.getStatut());
        System.out.println("Temps d'exécution: " + resultat.getTempsExecution() + " ms");
        System.out.println("Sessions planifiées: " + resultat.getPlanning().size());
        System.out.println("Cours non planifiés: " + resultat.getCoursNonPlanifies().size());
        System.out.println("Conflits détectés: " + resultat.getConflits().size());
        
        // Display planning
        if (!resultat.getPlanning().isEmpty()) {
            System.out.println("\n📅 PLANNING GÉNÉRÉ:");
            System.out.println("─────────────────────────────────────────────────────────");
            
            Map<DayOfWeek, List<SessionPlanifiee>> parJour = new TreeMap<>();
            for (SessionPlanifiee session : resultat.getPlanning()) {
                DayOfWeek jour = session.getTimeSlot().getDay();
                parJour.computeIfAbsent(jour, k -> new ArrayList<>()).add(session);
            }
            
            for (Map.Entry<DayOfWeek, List<SessionPlanifiee>> entry : parJour.entrySet()) {
                System.out.println("\n🔹 " + entry.getKey() + ":");
                entry.getValue().sort(Comparator.comparing(s -> s.getTimeSlot().getStartTime()));
                
                for (SessionPlanifiee session : entry.getValue()) {
                    System.out.printf("  %s-%s | %-25s | %-15s | %-10s%n",
                        session.getTimeSlot().getStartTime(),
                        session.getTimeSlot().getEndTime(),
                        session.getCours().getNom(),
                        session.getFormateur().getNom(),
                        session.getSalle().getNom()
                    );
                }
            }
        }
        
        // Display unscheduled courses
        if (!resultat.getCoursNonPlanifies().isEmpty()) {
            System.out.println("\n⚠️  COURS NON PLANIFIÉS:");
            System.out.println("─────────────────────────────────────────────────────────");
            for (Cours cours : resultat.getCoursNonPlanifies()) {
                System.out.println("  • " + cours.getNom() + 
                    " (" + cours.getNombreEtudiants() + " étudiants)");
            }
        }
        
        // Display conflicts
        if (!resultat.getConflits().isEmpty()) {
            System.out.println("\n❌ CONFLITS DÉTECTÉS:");
            System.out.println("─────────────────────────────────────────────────────────");
            Map<String, List<ContrainteService.Conflit>> parSeverite = 
                contrainteService.grouperConflitsParSeverite(resultat.getConflits());
            
            for (Map.Entry<String, List<ContrainteService.Conflit>> entry : parSeverite.entrySet()) {
                System.out.println("\n  " + entry.getKey() + ":");
                for (ContrainteService.Conflit conflit : entry.getValue()) {
                    System.out.println("    • " + conflit.getDescription());
                }
            }
        }
        
        // Display statistics
        System.out.println("\n📊 STATISTIQUES:");
        System.out.println("─────────────────────────────────────────────────────────");
        Map<String, Object> stats = resultat.getStatistiques();
        stats.forEach((key, value) -> {
            if (value instanceof Double) {
                System.out.printf("  %-35s: %.2f%n", key, (Double)value);
            } else if (value instanceof Map) {
                System.out.println("  " + key + ":");
                ((Map<?, ?>)value).forEach((k, v) -> 
                    System.out.println("    - " + k + ": " + v));
            } else {
                System.out.printf("  %-35s: %s%n", key, value);
            }
        });
        
        System.out.println("\n" + "═".repeat(61) + "\n");
    }
}
