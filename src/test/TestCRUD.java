// Classe TestCRUD : Gère les données du système
package test;
import  dao.*;
import models.*;
import util.DatabaseConnection;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.math.BigDecimal;

public class TestCRUD {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Training Center Management System - CRUD Test");
        System.out.println("========================================\n");

        testDatabaseConnection();

        testFormateurCRUD();

        testEtudiantCRUD();

        testSalleCRUD();

        testMaterielCRUD();

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

        System.out.println("\n1. CREATE Test:");
        Formateur newFormateur = new Formateur(
                "Dupont", "Jean", "jean.dupont@test.fr",
                "+33 6 11 22 33 44", "Intelligence Artificielle", 5);
        formateurDAO.create(newFormateur);

        System.out.println("\n2. READ Test:");
        Formateur Trouvé = formateurDAO.findById(newFormateur.getId());
        if (Trouvé != null) {
            System.out.println("✓ Trouvé: " + Trouvé);
        }

        System.out.println("\n3. UPDATE Test:");
        Trouvé.setAnneesExperience(10);
        Trouvé.setSpecialite("IA & Machine Learning");
        formateurDAO.update(Trouvé);

        System.out.println("\n4. READ ALL Test:");
        formateurDAO.findAll();

        System.out.println("\n5. SEARCH BY SPECIALITE Test:");
        var formateursBySpec = formateurDAO.findBySpecialite("Web");
        System.out.println("✓ Trouvé " + formateursBySpec.size() + " formateur(s) with 'Web' specialite");

        System.out.println("\n6. DELETE Test:");
        formateurDAO.delete(newFormateur.getId());
    }

    private static void testEtudiantCRUD() {
        System.out.println("\n--- Testing Etudiant CRUD Operations ---");
        EtudiantDAO etudiantDAO = new EtudiantDAO();

        System.out.println("\n1. CREATE Test:");
        Etudiant newEtudiant = new Etudiant(
                "Rousseau", "Claire", "claire.rousseau@test.fr",
                "+33 6 99 88 77 66", "Débutant", Date.valueOf("2024-03-01"));
        etudiantDAO.create(newEtudiant);

        System.out.println("\n2. READ Test:");
        Etudiant Trouvé = etudiantDAO.findById(newEtudiant.getId());
        if (Trouvé != null) {
            System.out.println("✓ Trouvé: " + Trouvé);
        }

        System.out.println("\n3. UPDATE Test:");
        Trouvé.setNiveau("Intermédiaire");
        etudiantDAO.update(Trouvé);

        System.out.println("\n4. READ ALL Test:");
        etudiantDAO.findAll();

        System.out.println("\n5. SEARCH BY NIVEAU Test:");
        var etudiantsByNiveau = etudiantDAO.findByNiveau("Débutant");
        System.out.println("✓ Trouvé " + etudiantsByNiveau.size() + " etudiant(s) with 'Débutant' niveau");

        System.out.println("\n6. DELETE Test:");
        etudiantDAO.delete(newEtudiant.getId());
    }

    private static void testSalleCRUD() {
        System.out.println("\n--- Testing Salle CRUD Operations ---");
        SalleDAO salleDAO = new SalleDAO();

        System.out.println("\n1. CREATE Test:");
        Salle newSalle = new Salle(
                "Salle C301", 25, "Conférence",
                "Projecteur, Système audio, Tableau interactif",
                "Bâtiment C - 3ème étage", true);
        salleDAO.create(newSalle);

        System.out.println("\n2. READ Test:");
        Salle Trouvé = salleDAO.findById(newSalle.getId());
        if (Trouvé != null) {
            System.out.println("✓ Trouvé: " + Trouvé);
        }

        System.out.println("\n3. UPDATE Test:");
        Trouvé.setCapacite(30);
        Trouvé.setDisponible(false);
        salleDAO.update(Trouvé);

        System.out.println("\n4. READ ALL Test:");
        salleDAO.findAll();

        System.out.println("\n5. SEARCH BY TYPE Test:");
        var sallesByType = salleDAO.findByType("Informatique");
        System.out.println("✓ Trouvé " + sallesByType.size() + " salle(s) of type 'Informatique'");

        System.out.println("\n6. DELETE Test:");
        salleDAO.delete(newSalle.getId());
    }

    private static void testMaterielCRUD() {
        System.out.println("\n--- Testing Materiel CRUD Operations ---");
        MaterielDAO materielDAO = new MaterielDAO();

        System.out.println("\n1. CREATE Test:");
        Materiel newMateriel = new Materiel(
                "Tablette Samsung", "Informatique",
                20, 25, "Bon", "Tablette Samsung Galaxy Tab pour formations mobiles");
        materielDAO.create(newMateriel);

        System.out.println("\n2. READ Test:");
        Materiel Trouvé = materielDAO.findById(newMateriel.getId());
        if (Trouvé != null) {
            System.out.println("✓ Trouvé: " + Trouvé);
        }

        System.out.println("\n3. UPDATE Test:");
        Trouvé.setQuantiteDisponible(18);
        Trouvé.setEtat("Très bon");
        materielDAO.update(Trouvé);

        System.out.println("\n4. READ ALL Test:");
        materielDAO.findAll();

        System.out.println("\n5. SEARCH BY TYPE Test:");
        var materielsByType = materielDAO.findByType("Informatique");
        System.out.println("✓ Trouvé " + materielsByType.size() + " materiel(s) of type 'Informatique'");

        System.out.println("\n6. DELETE Test:");
        materielDAO.delete(newMateriel.getId());
    }

    private static void testSessionCRUD() {
        System.out.println("\n--- Testing Session CRUD Operations ---");
        SessionDAO sessionDAO = new SessionDAO();

        System.out.println("\n1. CREATE Test:");
        Session newSession = new Session(
                "Formation Spring Boot", 1, 1,
                Timestamp.valueOf("2024-04-01 09:00:00"),
                Timestamp.valueOf("2024-04-01 17:00:00"),
                new BigDecimal("7.00"), 15, "Planifiée",
                "Formation complète sur Spring Boot et microservices");
        sessionDAO.create(newSession);

        System.out.println("\n2. READ Test:");
        Session Trouvé = sessionDAO.findById(newSession.getId());
        if (Trouvé != null) {
            System.out.println("✓ Trouvé: " + Trouvé);
        }

        System.out.println("\n3. UPDATE Test:");
        Trouvé.setNombreEtudiants(18);
        Trouvé.setStatut("Confirmée");
        sessionDAO.update(Trouvé);

        System.out.println("\n4. READ ALL Test:");
        sessionDAO.findAll();

        System.out.println("\n5. SEARCH BY STATUT Test:");
        var sessionsByStatut = sessionDAO.findByStatut("Planifiée");
        System.out.println("✓ Trouvé " + sessionsByStatut.size() + " session(s) with 'Planifiée' statut");

        System.out.println("\n6. DELETE Test:");
        sessionDAO.delete(newSession.getId());
    }
}
// Fin de la classe TestCRUD

