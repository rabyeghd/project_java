USE centre_formation;

INSERT INTO formateurs (nom, prenom, email, telephone, specialite, annees_experience) VALUES
('Ben Salem', 'Amira', 'amira.bensalem@formation.tn', '+216 20 123 456', 'Développement Web', 8),
('Trabelsi', 'Mohamed', 'mohamed.trabelsi@formation.tn', '+216 22 234 567', 'Data Science', 12),
('Jlassi', 'Leila', 'leila.jlassi@formation.tn', '+216 24 345 678', 'Cybersécurité', 6);

INSERT INTO etudiants (nom, prenom, email, telephone, niveau, date_inscription) VALUES
('Gharbi', 'Youssef', 'youssef.gharbi@email.tn', '+216 26 456 789', 'Débutant', '2024-01-15'),
('Kacem', 'Salma', 'salma.kacem@email.tn', '+216 28 567 890', 'Intermédiaire', '2024-01-20'),
('Mansour', 'Rami', 'rami.mansour@email.tn', '+216 50 678 901', 'Débutant', '2024-02-01'),
('Ayari', 'Nour', 'nour.ayari@email.tn', '+216 52 789 012', 'Avancé', '2024-02-10'),
('Hamdi', 'Mehdi', 'mehdi.hamdi@email.tn', '+216 54 890 123', 'Intermédiaire', '2024-02-15');

INSERT INTO salles (nom, capacite, type, equipements_fixes, localisation, disponible) VALUES
('Salle A101', 20, 'Informatique', 'Projecteur, Tableau blanc, Climatisation', 'Bâtiment A - 1er étage', TRUE),
('Salle B205', 15, 'Laboratoire', 'Projecteur, Écran tactile, Équipement réseau', 'Bâtiment B - 2ème étage', TRUE);

INSERT INTO materiel (nom, type, quantite_disponible, quantite_totale, etat, description) VALUES
('Ordinateur Portable Dell', 'Informatique', 25, 30, 'Bon', 'Dell Latitude 5420, i5, 16GB RAM, 512GB SSD'),
('Projecteur Epson', 'Audiovisuel', 8, 10, 'Bon', 'Projecteur HD 1080p avec HDMI'),
('Routeur Cisco', 'Réseau', 12, 15, 'Bon', 'Routeur Cisco pour ateliers réseau');

INSERT INTO sessions (nom_cours, formateur_id, salle_id, date_debut, date_fin, duree_heures, nombre_etudiants, statut, description) VALUES
('Introduction au Développement Web', 1, 1, '2024-03-01 09:00:00', '2024-03-01 17:00:00', 7.00, 3, 'Planifiée', 'Formation HTML, CSS et JavaScript pour débutants'),
('Analyse de Données avec Python', 2, 2, '2024-03-05 10:00:00', '2024-03-05 16:00:00', 6.00, 2, 'Planifiée', 'Introduction à Pandas et NumPy');

INSERT INTO session_etudiants (session_id, etudiant_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 4),
(2, 5);

INSERT INTO session_materiel (session_id, materiel_id, quantite_requise) VALUES
(1, 1, 20),
(1, 2, 1),
(2, 1, 15),
(2, 2, 1);

INSERT INTO disponibilites (formateur_id, jour_semaine, heure_debut, heure_fin, type) VALUES
(1, 'Lundi', '09:00:00', '17:00:00', 'Récurrente'),
(1, 'Mercredi', '09:00:00', '17:00:00', 'Récurrente'),
(1, 'Vendredi', '09:00:00', '12:00:00', 'Récurrente'),
(2, 'Mardi', '10:00:00', '18:00:00', 'Récurrente'),
(2, 'Jeudi', '10:00:00', '18:00:00', 'Récurrente'),
(3, 'Lundi', '14:00:00', '18:00:00', 'Récurrente'),
(3, 'Mercredi', '09:00:00', '17:00:00', 'Récurrente');

INSERT INTO contraintes (type, description, entite_type, entite_id, priorite, active) VALUES
('Capacité Salle', 'La salle ne peut pas accueillir plus de 20 personnes', 'salle', 1, 5, TRUE),
('Disponibilité Formateur', 'Le formateur n''est pas disponible le week-end', 'formateur', 1, 4, TRUE),
('Équipement Requis', 'Nécessite au moins 15 ordinateurs portables', 'session', 1, 3, TRUE);

INSERT INTO preferences (entite_type, entite_id, type_preference, valeur, priorite) VALUES
('formateur', 1, 'Horaire Préféré', 'Matin', 3),
('formateur', 2, 'Salle Préférée', 'Salle B205', 2),
('etudiant', 4, 'Format Cours', 'Pratique intensive', 4),
('etudiant', 5, 'Horaire Préféré', 'Après-midi', 2);
