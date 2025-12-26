package com.formation.utils;

import com.formation.models.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

/**
 * Utility class for generating test data
 */
public class TestDataGenerator {
    
    /**
     * Generate sample trainers
     */
    public static List<Formateur> genererFormateurs(int count) {
        List<Formateur> formateurs = new ArrayList<>();
        String[] specialites = {"Java", "Python", "JavaScript", "Data Science", "DevOps", "Database"};
        
        for (long i = 1; i <= count; i++) {
            Formateur formateur = new Formateur(i, "Formateur " + i, 
                specialites[(int)(i-1) % specialites.length]);
            
            // Add default availability (full week)
            for (DayOfWeek jour : DayOfWeek.values()) {
                if (jour != DayOfWeek.SATURDAY && jour != DayOfWeek.SUNDAY) {
                    formateur.addDisponibilite(new TimeSlot(jour, LocalTime.of(8, 0), LocalTime.of(18, 0)));
                }
            }
            
            // Add some preferences
            formateur.addPreference(new TimeSlot(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(10, 0)));
            formateur.addPreference(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(16, 0)));
            
            formateurs.add(formateur);
        }
        
        return formateurs;
    }
    
    /**
     * Generate sample rooms
     */
    public static List<Salle> genererSalles(int count) {
        List<Salle> salles = new ArrayList<>();
        int[] capacites = {10, 15, 20, 25, 30, 40};
        String[][] equipements = {
            {"Projecteur", "Tableau"},
            {"Projecteur", "Tableau", "Ordinateurs"},
            {"Projecteur", "Tableau", "Laboratoire"},
            {"Projecteur", "Tableau", "Ordinateurs", "Wifi"},
            {"Projecteur", "Tableau", "Video Conference"}
        };
        
        for (long i = 1; i <= count; i++) {
            int capacite = capacites[(int)(i-1) % capacites.length];
            Salle salle = new Salle(i, "Salle " + i, capacite);
            
            // Add equipment
            String[] equipementSet = equipements[(int)(i-1) % equipements.length];
            for (String equip : equipementSet) {
                salle.addEquipement(equip);
            }
            
            salles.add(salle);
        }
        
        return salles;
    }
    
    /**
     * Generate sample students
     */
    public static List<Etudiant> genererEtudiants(int count) {
        List<Etudiant> etudiants = new ArrayList<>();
        String[] groupes = {"Groupe A", "Groupe B", "Groupe C", "Groupe D"};
        
        for (long i = 1; i <= count; i++) {
            String groupe = groupes[(int)(i-1) % groupes.length];
            Etudiant etudiant = new Etudiant(i, "Etudiant " + i, groupe);
            etudiants.add(etudiant);
        }
        
        return etudiants;
    }
    
    /**
     * Generate sample courses
     */
    public static List<Cours> genererCours(int count, List<Formateur> formateurs) {
        List<Cours> cours = new ArrayList<>();
        String[] nomsCoursBase = {
            "Introduction à Java", 
            "Programmation Avancée", 
            "Base de Données", 
            "Développement Web",
            "Machine Learning",
            "DevOps Pratique",
            "Architecture Logicielle",
            "Sécurité Informatique"
        };
        
        int[] durees = {120, 120, 180, 120}; // 2 or 3 hours
        int[] nombreEtudiants = {10, 15, 20, 25, 12, 18};
        
        for (long i = 1; i <= count; i++) {
            String nomCours = nomsCoursBase[(int)(i-1) % nomsCoursBase.length] + " - Session " + i;
            int duree = durees[(int)(i-1) % durees.length];
            int nbEtudiants = nombreEtudiants[(int)(i-1) % nombreEtudiants.length];
            
            Cours cours_item = new Cours(i, nomCours, duree, nbEtudiants);
            
            // Assign formateur
            if (formateurs != null && !formateurs.isEmpty()) {
                Formateur formateur = formateurs.get((int)(i-1) % formateurs.size());
                cours_item.setFormateurRequis(formateur);
            }
            
            // Add some required equipment
            if (i % 3 == 0) {
                cours_item.addMateriel("Ordinateurs");
            }
            if (i % 2 == 0) {
                cours_item.addMateriel("Projecteur");
            }
            cours_item.addMateriel("Tableau");
            
            // Set priority (some courses are more important)
            cours_item.setPriorite((int)(i % 3) + 1);
            
            cours.add(cours_item);
        }
        
        return cours;
    }
    
    /**
     * Generate a complete test scenario
     */
    public static Map<String, Object> genererScenarioComplet(
            int nbFormateurs, 
            int nbSalles, 
            int nbEtudiants, 
            int nbCours) {
        
        Map<String, Object> scenario = new HashMap<>();
        
        List<Formateur> formateurs = genererFormateurs(nbFormateurs);
        List<Salle> salles = genererSalles(nbSalles);
        List<Etudiant> etudiants = genererEtudiants(nbEtudiants);
        List<Cours> cours = genererCours(nbCours, formateurs);
        
        scenario.put("formateurs", formateurs);
        scenario.put("salles", salles);
        scenario.put("etudiants", etudiants);
        scenario.put("cours", cours);
        
        return scenario;
    }
}
