-- ============================================
-- Training Center Management System - Database Schema
-- ============================================

-- Drop database if exists and create new one
DROP DATABASE IF EXISTS centre_formation;
CREATE DATABASE centre_formation CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE centre_formation;

-- ============================================
-- Table: formateurs (Trainers)
-- ============================================
CREATE TABLE formateurs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telephone VARCHAR(20),
    specialite VARCHAR(200),
    annees_experience INT DEFAULT 0,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_specialite (specialite)
) ENGINE=InnoDB;

-- ============================================
-- Table: etudiants (Students)
-- ============================================
CREATE TABLE etudiants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telephone VARCHAR(20),
    niveau VARCHAR(50),
    date_inscription DATE,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_niveau (niveau)
) ENGINE=InnoDB;

-- ============================================
-- Table: salles (Rooms)
-- ============================================
CREATE TABLE salles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE,
    capacite INT NOT NULL,
    type VARCHAR(50),
    equipements_fixes TEXT,
    localisation VARCHAR(200),
    disponible BOOLEAN DEFAULT TRUE,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_nom (nom),
    INDEX idx_capacite (capacite)
) ENGINE=InnoDB;

-- ============================================
-- Table: materiel (Equipment)
-- ============================================
CREATE TABLE materiel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    quantite_disponible INT NOT NULL DEFAULT 0,
    quantite_totale INT NOT NULL DEFAULT 0,
    etat VARCHAR(50) DEFAULT 'Bon',
    description TEXT,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type (type),
    INDEX idx_nom (nom)
) ENGINE=InnoDB;

-- ============================================
-- Table: sessions (Training Sessions)
-- ============================================
CREATE TABLE sessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom_cours VARCHAR(200) NOT NULL,
    formateur_id INT,
    salle_id INT,
    date_debut DATETIME NOT NULL,
    date_fin DATETIME NOT NULL,
    duree_heures DECIMAL(5,2),
    nombre_etudiants INT DEFAULT 0,
    statut VARCHAR(50) DEFAULT 'Planifiée',
    description TEXT,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (formateur_id) REFERENCES formateurs(id) ON DELETE SET NULL,
    FOREIGN KEY (salle_id) REFERENCES salles(id) ON DELETE SET NULL,
    INDEX idx_formateur (formateur_id),
    INDEX idx_salle (salle_id),
    INDEX idx_date_debut (date_debut),
    INDEX idx_statut (statut)
) ENGINE=InnoDB;

-- ============================================
-- Table: session_etudiants (Many-to-Many: Sessions and Students)
-- ============================================
CREATE TABLE session_etudiants (
    session_id INT NOT NULL,
    etudiant_id INT NOT NULL,
    date_inscription TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (session_id, etudiant_id),
    FOREIGN KEY (session_id) REFERENCES sessions(id) ON DELETE CASCADE,
    FOREIGN KEY (etudiant_id) REFERENCES etudiants(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ============================================
-- Table: session_materiel (Many-to-Many: Sessions and Equipment)
-- ============================================
CREATE TABLE session_materiel (
    session_id INT NOT NULL,
    materiel_id INT NOT NULL,
    quantite_requise INT NOT NULL DEFAULT 1,
    PRIMARY KEY (session_id, materiel_id),
    FOREIGN KEY (session_id) REFERENCES sessions(id) ON DELETE CASCADE,
    FOREIGN KEY (materiel_id) REFERENCES materiel(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ============================================
-- Table: disponibilites (Availability)
-- ============================================
CREATE TABLE disponibilites (
    id INT AUTO_INCREMENT PRIMARY KEY,
    formateur_id INT NOT NULL,
    jour_semaine VARCHAR(20) NOT NULL,
    heure_debut TIME NOT NULL,
    heure_fin TIME NOT NULL,
    date_debut DATE,
    date_fin DATE,
    type VARCHAR(50) DEFAULT 'Récurrente',
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (formateur_id) REFERENCES formateurs(id) ON DELETE CASCADE,
    INDEX idx_formateur (formateur_id),
    INDEX idx_jour (jour_semaine)
) ENGINE=InnoDB;

-- ============================================
-- Table: contraintes (Constraints)
-- ============================================
CREATE TABLE contraintes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    description TEXT,
    entite_type VARCHAR(50),
    entite_id INT,
    parametre_json JSON,
    priorite INT DEFAULT 1,
    active BOOLEAN DEFAULT TRUE,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type (type),
    INDEX idx_entite (entite_type, entite_id)
) ENGINE=InnoDB;

-- ============================================
-- Table: preferences (Preferences)
-- ============================================
CREATE TABLE preferences (
    id INT AUTO_INCREMENT PRIMARY KEY,
    entite_type VARCHAR(50) NOT NULL,
    entite_id INT NOT NULL,
    type_preference VARCHAR(100) NOT NULL,
    valeur VARCHAR(255),
    priorite INT DEFAULT 1,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_entite (entite_type, entite_id),
    INDEX idx_type (type_preference)
) ENGINE=InnoDB;

-- ============================================
-- End of Schema
-- ============================================
