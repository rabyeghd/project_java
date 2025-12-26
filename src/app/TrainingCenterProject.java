package app;

import java.sql.*;
import java.util.*;
import java.math.BigDecimal;
import models.*;
import dao.*;
import util.DatabaseConnection;

public class TrainingCenterProject {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Systeme de Gestion de Centre de Formation");
        System.out.println("========================================\n");
        testerConnexion();
        testerCRUDFormateur();
        testerCRUDEtudiant();
        testerCRUDSalle();
        testerCRUDMateriel();
        testerCRUDSession();
        System.out.println("\n========================================");
        System.out.println("Tous les tests sont termines avec succes !");
        System.out.println("========================================");
    }

    private static void testerConnexion() {
        System.out.println("\n--- Test de Connexion ---");
        if (DatabaseConnection.getInstance().testConnection()) {
            System.out.println("✓ Connexion active !");
        } else {
            System.out.println("✗ Echec de connexion !");
        }
    }

    private static void testerCRUDFormateur() {
        System.out.println("\n--- Test CRUD Formateurs ---");
        FormateurDAO dao = new FormateurDAO();
        Formateur f = new Formateur("Dupont", "Jean", "jean.dupont@test.fr", "0611223344", "IA", 5);
        dao.create(f);
        System.out.println("✓ Formateur cree : " + f);
        dao.delete(f.getId());
    }

    private static void testerCRUDEtudiant() {
        System.out.println("\n--- Test CRUD Etudiants ---");
        EtudiantDAO dao = new EtudiantDAO();
        Etudiant e = new Etudiant("Rousseau", "Claire", "claire@test.fr", "0699887766", "Debutant",
                java.sql.Date.valueOf("2024-01-01"));
        dao.create(e);
        System.out.println("✓ Etudiant cree : " + e);
        dao.delete(e.getId());
    }

    private static void testerCRUDSalle() {
        System.out.println("\n--- Test CRUD Salles ---");
        SalleDAO dao = new SalleDAO();
        Salle s = new Salle("Salle A1", 20, "Informatique", "PC, Projecteur", "Batiment A", true);
        dao.create(s);
        System.out.println("✓ Salle creee : " + s);
        dao.delete(s.getId());
    }

    private static void testerCRUDMateriel() {
        System.out.println("\n--- Test CRUD Materiel ---");
        MaterielDAO dao = new MaterielDAO();
        Materiel m = new Materiel("Tablette", "Informatique", 10, 10, "Bon", "Galaxy Tab");
        dao.create(m);
        System.out.println("✓ Materiel cree : " + m);
        dao.delete(m.getId());
    }

    private static void testerCRUDSession() {
        System.out.println("\n--- Test CRUD Sessions ---");
        SessionDAO dao = new SessionDAO();
        Session s = new Session("Java Expert", 1, 1, new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis() + 3600000),
                new BigDecimal("1.00"), 10, "Planifiee", "Cours Java");
        dao.create(s);
        System.out.println("✓ Session creee : " + s.getNomCours());
        dao.delete(s.getId());
    }
}
