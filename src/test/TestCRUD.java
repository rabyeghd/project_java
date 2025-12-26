package test;
import  dao.*;
import models.*;
import util.DatabaseConnection;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * 
 * Test class to verify CRUD operations for all DAOs
 */
public class TestCRUD {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Training Center Management System - CRUD Test");
        System.out.println("========================================\n");

        // Test database connection
        testDatabaseConnection();

        // Test Formateur CRUD
        testFormateurCRUD();

        // Test Etudiant CRUD
        testEtudiantCRUD();

        // Test Salle CRUD
        testSalleCRUD();

        // Test Materiel CRUD
        testMaterielCRUD();

        // Test Session CRUD
        testSessionCRUD();

        System.out.println("\n========================================");
        System.out.println("All tests completed!");
        System.out.println("========================================");
    }

    private static void testDatabaseConnection() {
        System.out.println("\n--- Testing Database Connection ---");
        DatabaseConnection dbConn = DatabaseConnection.getInstance();
        if (dbConn.testConnection()) {
            System.out.println("✓ Database connection is active!");
        } else {
            System.out.println("✗ Database connection failed!");
        }
    }

    private static void testFormateurCRUD() {
        System.out.println("\n--- Testing Formateur CRUD Operations ---");
        FormateurDAO formateurDAO = new FormateurDAO();

        // CREATE
        System.out.println("\n1. CREATE Test:");
        Formateur newFormateur = new Formateur(
                "Dupont", "Jean", "jean.dupont@test.fr",
                "+33 6 11 22 33 44", "Intelligence Artificielle", 5);
        formateurDAO.create(newFormateur);

        // READ
        System.out.println("\n2. READ Test:");
        Formateur found = formateurDAO.findById(newFormateur.getId());
        if (found != null) {
            System.out.println("✓ Found: " + found);
        }

        // UPDATE
        System.out.println("\n3. UPDATE Test:");
        found.setAnneesExperience(10);
        found.setSpecialite("IA & Machine Learning");
        formateurDAO.update(found);

        // READ ALL
        System.out.println("\n4. READ ALL Test:");
        formateurDAO.findAll();

        // SEARCH BY SPECIALITE
        System.out.println("\n5. SEARCH BY SPECIALITE Test:");
        var formateursBySpec = formateurDAO.findBySpecialite("Web");
        System.out.println("✓ Found " + formateursBySpec.size() + " formateur(s) with 'Web' specialite");

        // DELETE
        System.out.println("\n6. DELETE Test:");
        formateurDAO.delete(newFormateur.getId());
    }

    private static void testEtudiantCRUD() {
        System.out.println("\n--- Testing Etudiant CRUD Operations ---");
        EtudiantDAO etudiantDAO = new EtudiantDAO();

        // CREATE
        System.out.println("\n1. CREATE Test:");
        Etudiant newEtudiant = new Etudiant(
                "Rousseau", "Claire", "claire.rousseau@test.fr",
                "+33 6 99 88 77 66", "Débutant", Date.valueOf("2024-03-01"));
        etudiantDAO.create(newEtudiant);

        // READ
        System.out.println("\n2. READ Test:");
        Etudiant found = etudiantDAO.findById(newEtudiant.getId());
        if (found != null) {
            System.out.println("✓ Found: " + found);
        }

        // UPDATE
        System.out.println("\n3. UPDATE Test:");
        found.setNiveau("Intermédiaire");
        etudiantDAO.update(found);

        // READ ALL
        System.out.println("\n4. READ ALL Test:");
        etudiantDAO.findAll();

        // SEARCH BY NIVEAU
        System.out.println("\n5. SEARCH BY NIVEAU Test:");
        var etudiantsByNiveau = etudiantDAO.findByNiveau("Débutant");
        System.out.println("✓ Found " + etudiantsByNiveau.size() + " etudiant(s) with 'Débutant' niveau");

        // DELETE
        System.out.println("\n6. DELETE Test:");
        etudiantDAO.delete(newEtudiant.getId());
    }

    private static void testSalleCRUD() {
        System.out.println("\n--- Testing Salle CRUD Operations ---");
        SalleDAO salleDAO = new SalleDAO();

        // CREATE
        System.out.println("\n1. CREATE Test:");
        Salle newSalle = new Salle(
                "Salle C301", 25, "Conférence",
                "Projecteur, Système audio, Tableau interactif",
                "Bâtiment C - 3ème étage", true);
        salleDAO.create(newSalle);

        // READ
        System.out.println("\n2. READ Test:");
        Salle found = salleDAO.findById(newSalle.getId());
        if (found != null) {
            System.out.println("✓ Found: " + found);
        }

        // UPDATE
        System.out.println("\n3. UPDATE Test:");
        found.setCapacite(30);
        found.setDisponible(false);
        salleDAO.update(found);

        // READ ALL
        System.out.println("\n4. READ ALL Test:");
        salleDAO.findAll();

        // SEARCH BY TYPE
        System.out.println("\n5. SEARCH BY TYPE Test:");
        var sallesByType = salleDAO.findByType("Informatique");
        System.out.println("✓ Found " + sallesByType.size() + " salle(s) of type 'Informatique'");

        // DELETE
        System.out.println("\n6. DELETE Test:");
        salleDAO.delete(newSalle.getId());
    }

    private static void testMaterielCRUD() {
        System.out.println("\n--- Testing Materiel CRUD Operations ---");
        MaterielDAO materielDAO = new MaterielDAO();

        // CREATE
        System.out.println("\n1. CREATE Test:");
        Materiel newMateriel = new Materiel(
                "Tablette Samsung", "Informatique",
                20, 25, "Bon", "Tablette Samsung Galaxy Tab pour formations mobiles");
        materielDAO.create(newMateriel);

        // READ
        System.out.println("\n2. READ Test:");
        Materiel found = materielDAO.findById(newMateriel.getId());
        if (found != null) {
            System.out.println("✓ Found: " + found);
        }

        // UPDATE
        System.out.println("\n3. UPDATE Test:");
        found.setQuantiteDisponible(18);
        found.setEtat("Très bon");
        materielDAO.update(found);

        // READ ALL
        System.out.println("\n4. READ ALL Test:");
        materielDAO.findAll();

        // SEARCH BY TYPE
        System.out.println("\n5. SEARCH BY TYPE Test:");
        var materielsByType = materielDAO.findByType("Informatique");
        System.out.println("✓ Found " + materielsByType.size() + " materiel(s) of type 'Informatique'");

        // DELETE
        System.out.println("\n6. DELETE Test:");
        materielDAO.delete(newMateriel.getId());
    }

    private static void testSessionCRUD() {
        System.out.println("\n--- Testing Session CRUD Operations ---");
        SessionDAO sessionDAO = new SessionDAO();

        // CREATE
        System.out.println("\n1. CREATE Test:");
        Session newSession = new Session(
                "Formation Spring Boot", 1, 1,
                Timestamp.valueOf("2024-04-01 09:00:00"),
                Timestamp.valueOf("2024-04-01 17:00:00"),
                new BigDecimal("7.00"), 15, "Planifiée",
                "Formation complète sur Spring Boot et microservices");
        sessionDAO.create(newSession);

        // READ
        System.out.println("\n2. READ Test:");
        Session found = sessionDAO.findById(newSession.getId());
        if (found != null) {
            System.out.println("✓ Found: " + found);
        }

        // UPDATE
        System.out.println("\n3. UPDATE Test:");
        found.setNombreEtudiants(18);
        found.setStatut("Confirmée");
        sessionDAO.update(found);

        // READ ALL
        System.out.println("\n4. READ ALL Test:");
        sessionDAO.findAll();

        // SEARCH BY STATUT
        System.out.println("\n5. SEARCH BY STATUT Test:");
        var sessionsByStatut = sessionDAO.findByStatut("Planifiée");
        System.out.println("✓ Found " + sessionsByStatut.size() + " session(s) with 'Planifiée' statut");

        // DELETE
        System.out.println("\n6. DELETE Test:");
        sessionDAO.delete(newSession.getId());
    }
}
