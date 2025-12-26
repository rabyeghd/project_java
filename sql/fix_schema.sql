-- ============================================
-- Fix Database Schema - Add Missing Columns
-- ============================================
USE centre_formation;

-- Fix formateurs table (already done, but included for completeness)
ALTER TABLE formateurs 
ADD COLUMN IF NOT EXISTS annees_experience INT DEFAULT 0 AFTER specialite;

-- Fix salles table - add missing columns
ALTER TABLE salles 
ADD COLUMN IF NOT EXISTS type VARCHAR(50) AFTER capacite,
ADD COLUMN IF NOT EXISTS equipements_fixes TEXT AFTER type,
ADD COLUMN IF NOT EXISTS localisation VARCHAR(200) AFTER equipements_fixes,
ADD COLUMN IF NOT EXISTS disponible BOOLEAN DEFAULT TRUE AFTER localisation;

-- Fix materiel table - add missing columns
ALTER TABLE materiel 
ADD COLUMN IF NOT EXISTS type VARCHAR(50) NOT NULL DEFAULT 'Général' AFTER nom,
ADD COLUMN IF NOT EXISTS quantite_disponible INT NOT NULL DEFAULT 0 AFTER type,
ADD COLUMN IF NOT EXISTS quantite_totale INT NOT NULL DEFAULT 0 AFTER quantite_disponible,
ADD COLUMN IF NOT EXISTS etat VARCHAR(50) DEFAULT 'Bon' AFTER quantite_totale,
ADD COLUMN IF NOT EXISTS description TEXT AFTER etat;

-- Fix etudiants table - add missing columns
ALTER TABLE etudiants 
ADD COLUMN IF NOT EXISTS niveau VARCHAR(50) AFTER telephone,
ADD COLUMN IF NOT EXISTS date_inscription DATE AFTER niveau;

-- Fix sessions table - add missing columns
ALTER TABLE sessions 
ADD COLUMN IF NOT EXISTS nom_cours VARCHAR(200) NOT NULL DEFAULT 'Cours' AFTER id,
ADD COLUMN IF NOT EXISTS formateur_id INT AFTER nom_cours,
ADD COLUMN IF NOT EXISTS salle_id INT AFTER formateur_id,
ADD COLUMN IF NOT EXISTS date_debut DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER salle_id,
ADD COLUMN IF NOT EXISTS date_fin DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER date_debut,
ADD COLUMN IF NOT EXISTS duree_heures DECIMAL(5,2) AFTER date_fin,
ADD COLUMN IF NOT EXISTS nombre_etudiants INT DEFAULT 0 AFTER duree_heures,
ADD COLUMN IF NOT EXISTS statut VARCHAR(50) DEFAULT 'Planifiée' AFTER nombre_etudiants,
ADD COLUMN IF NOT EXISTS description TEXT AFTER statut;

-- Add foreign keys if they don't exist
-- Note: MySQL doesn't have IF NOT EXISTS for foreign keys, so we'll skip this if they already exist

-- Verify all changes
SELECT 'formateurs table:' as '';
DESCRIBE formateurs;

SELECT 'etudiants table:' as '';
DESCRIBE etudiants;

SELECT 'salles table:' as '';
DESCRIBE salles;

SELECT 'materiel table:' as '';
DESCRIBE materiel;

SELECT 'sessions table:' as '';
DESCRIBE sessions;
