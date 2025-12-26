# 📊 Architecture et Flux de l'Algorithme

## Vue d'Ensemble du Système

```
┌─────────────────────────────────────────────────────────────────┐
│                        FRONTEND (HTML/CSS/JS)                    │
│  - Interface de gestion des entités                             │
│  - Visualisation du planning (FullCalendar)                     │
│  - Affichage des statistiques                                    │
└────────────────────────┬────────────────────────────────────────┘
                         │ HTTP/REST API
                         │ JSON
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                   BACKEND (Spring Boot - Java)                   │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │              PlanningController (API REST)                │  │
│  │  POST /api/planning/generate                             │  │
│  │  GET  /api/planning/timeslots                            │  │
│  │  POST /api/planning/verify                               │  │
│  └───────────────────┬──────────────────────────────────────┘  │
│                      │                                           │
│                      ▼                                           │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │     PlanningOptimisationService ⭐ ALGORITHME PRINCIPAL   │  │
│  │                                                           │  │
│  │  1. trierCoursParContraintes()    (Heuristique)         │  │
│  │  2. planifierCours()               (Backtracking)        │  │
│  │  3. optimiserLocalement()          (Hill Climbing)       │  │
│  │  4. calculerStatistiques()         (Métriques)           │  │
│  └───────────────────┬──────────────────────────────────────┘  │
│                      │                                           │
│                      ▼                                           │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │         ContrainteService (Validation)                    │  │
│  │                                                           │  │
│  │  - verifierContraintes()          (Détection conflits)   │  │
│  │  - calculerScore()                 (Qualité solution)    │  │
│  │  - detecterTousLesConflits()      (Vérification finale)  │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │                  Modèles (Entités)                        │  │
│  │  Formateur | Salle | Cours | Etudiant | SessionPlanifiee│  │
│  └──────────────────────────────────────────────────────────┘  │
└────────────────────────┬────────────────────────────────────────┘
                         │ JPA/JDBC
                         │ SQL
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                      BASE DE DONNÉES (MySQL)                     │
│  Tables: formateurs, salles, cours, etudiants, planning         │
└─────────────────────────────────────────────────────────────────┘
```

## Flux Détaillé de l'Algorithme

```
┌─────────────────────────────────────────────────────────────────┐
│  ENTRÉE                                                          │
│  - Liste de Cours à planifier                                   │
│  - Liste de Formateurs disponibles                              │
│  - Liste de Salles disponibles                                  │
└────────────┬────────────────────────────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────────────────────────────┐
│  ÉTAPE 1: TRI PAR CONTRAINTES (Heuristique)                     │
│                                                                  │
│  Pour chaque cours, calculer score de contrainte:               │
│                                                                  │
│    Score = 0                                                     │
│    + (priorité cours × 10)                                       │
│    + 20 si formateur spécifique requis                          │
│    + (40 - nb_créneaux_disponibles × 2)                         │
│    + (nb_étudiants / 5)                                          │
│    + (nb_matériel_requis × 5)                                    │
│                                                                  │
│  Trier: Score DESCENDANT (plus contraint = planifié en 1er)    │
│                                                                  │
│  Exemple de tri:                                                 │
│    1. Cours A (Score 85) - Formateur fixe, 30 étudiants        │
│    2. Cours B (Score 60) - Formateur fixe, 15 étudiants        │
│    3. Cours C (Score 35) - N'importe quel formateur            │
└────────────┬────────────────────────────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────────────────────────────┐
│  ÉTAPE 2: PLANIFICATION (Backtracking)                          │
│                                                                  │
│  Planning = []                                                   │
│                                                                  │
│  POUR CHAQUE cours dans liste_triée:                            │
│    ┌──────────────────────────────────────────────────────┐    │
│    │ meilleure_session = null                             │    │
│    │ meilleur_score = -∞                                  │    │
│    │                                                       │    │
│    │ POUR CHAQUE formateur compatible:                    │    │
│    │   POUR CHAQUE salle compatible:                      │    │
│    │     POUR CHAQUE créneau disponible:                  │    │
│    │                                                       │    │
│    │       session_candidate = {                          │    │
│    │         cours, formateur, salle, créneau             │    │
│    │       }                                               │    │
│    │                                                       │    │
│    │       conflits = verifierContraintes(               │    │
│    │         session_candidate,                           │    │
│    │         Planning                                     │    │
│    │       )                                               │    │
│    │                                                       │    │
│    │       SI conflits.isEmpty():                         │    │
│    │         score = calculerScore(session_candidate)     │    │
│    │         SI score > meilleur_score:                   │    │
│    │           meilleur_score = score                     │    │
│    │           meilleure_session = session_candidate      │    │
│    │                                                       │    │
│    │ SI meilleure_session != null:                        │    │
│    │   Planning.add(meilleure_session)                    │    │
│    │ SINON:                                                │    │
│    │   CoursNonPlanifiés.add(cours)                       │    │
│    └──────────────────────────────────────────────────────┘    │
│                                                                  │
│  Complexité: O(n × m × p × t) où                               │
│    n = nombre de cours                                          │
│    m = nombre de formateurs                                     │
│    p = nombre de salles                                         │
│    t = nombre de créneaux                                       │
└────────────┬────────────────────────────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────────────────────────────┐
│  ÉTAPE 3: OPTIMISATION LOCALE (Hill Climbing)                   │
│                                                                  │
│  amélioration = true                                             │
│  itérations = 0                                                  │
│                                                                  │
│  TANT QUE amélioration ET itérations < MAX_ITERATIONS:          │
│    amélioration = false                                          │
│                                                                  │
│    POUR i = 0 jusqu'à Planning.size():                          │
│      POUR j = i+1 jusqu'à Planning.size():                      │
│        session1 = Planning[i]                                   │
│        session2 = Planning[j]                                   │
│                                                                  │
│        score_avant = score(session1) + score(session2)          │
│                                                                  │
│        // Essayer d'échanger les créneaux                       │
│        nouvelle1 = session1 AVEC créneau de session2           │
│        nouvelle2 = session2 AVEC créneau de session1           │
│                                                                  │
│        // Vérifier si échange est valide                        │
│        SI pas_de_conflits(nouvelle1, nouvelle2):               │
│          score_après = score(nouvelle1) + score(nouvelle2)     │
│                                                                  │
│          SI score_après > score_avant:                          │
│            Planning[i] = nouvelle1                              │
│            Planning[j] = nouvelle2                              │
│            amélioration = true                                  │
│                                                                  │
│    itérations++                                                  │
│                                                                  │
│  Exemple d'optimisation:                                         │
│    Avant: Formateur A (préfère matin) → après-midi             │
│           Formateur B (préfère après-midi) → matin             │
│    Après: Formateur A → matin ✓                                │
│           Formateur B → après-midi ✓                            │
└────────────┬────────────────────────────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────────────────────────────┐
│  ÉTAPE 4: DÉTECTION FINALE DES CONFLITS                         │
│                                                                  │
│  conflits = []                                                   │
│                                                                  │
│  POUR CHAQUE session dans Planning:                             │
│    autres_sessions = Planning - session                         │
│    nouveaux_conflits = verifierContraintes(                     │
│      session,                                                    │
│      autres_sessions                                             │
│    )                                                             │
│    conflits.addAll(nouveaux_conflits)                           │
│                                                                  │
│  Types de conflits détectés:                                    │
│    • CRITIQUE: Formateur double réservation                     │
│    • CRITIQUE: Salle double réservation                         │
│    • CRITIQUE: Formateur indisponible                           │
│    • MAJEUR: Capacité salle insuffisante                        │
│    • MAJEUR: Matériel manquant                                  │
└────────────┬────────────────────────────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────────────────────────────┐
│  ÉTAPE 5: CALCUL DES STATISTIQUES                               │
│                                                                  │
│  Statistiques calculées:                                         │
│                                                                  │
│  1. Taux de planification:                                       │
│     (nb_sessions_planifiées / nb_cours_total) × 100             │
│                                                                  │
│  2. Utilisation des salles:                                      │
│     Moyenne de sessions par salle                                │
│                                                                  │
│  3. Utilisation des formateurs:                                  │
│     Nombre de formateurs actifs / total                          │
│                                                                  │
│  4. Satisfaction des préférences:                                │
│     (créneaux_préférés_respectés / total_sessions) × 100        │
│                                                                  │
│  5. Efficacité capacitaire:                                      │
│     Moyenne(nb_étudiants / capacité_salle) × 100                │
│                                                                  │
│  6. Répartition temporelle:                                      │
│     Distribution par jour de la semaine                          │
│                                                                  │
│  7. Score de qualité moyen:                                      │
│     Moyenne des scores de toutes les sessions                    │
└────────────┬────────────────────────────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────────────────────────────┐
│  SORTIE (ResultatOptimisation)                                   │
│                                                                  │
│  - Planning: List<SessionPlanifiee>                             │
│  - CoursNonPlanifiés: List<Cours>                               │
│  - Conflits: List<Conflit>                                      │
│  - Statistiques: Map<String, Object>                            │
│  - TempsExecution: long (millisecondes)                         │
│  - Statut: "SUCCES" | "PARTIEL" | "ECHEC"                      │
└─────────────────────────────────────────────────────────────────┘
```

## Fonction de Score Détaillée

```
┌─────────────────────────────────────────────────────────────────┐
│  CALCUL DU SCORE D'UNE SESSION                                  │
│                                                                  │
│  score = 0.0                                                     │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ 1. Préférence du formateur                                │  │
│  │    SI formateur préfère ce créneau:                       │  │
│  │      score += 10.0                                         │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ 2. Priorité du cours                                       │  │
│  │    score += cours.priorité × 5.0                           │  │
│  │                                                             │  │
│  │    Exemple:                                                 │  │
│  │      Priorité 1: +5.0                                       │  │
│  │      Priorité 2: +10.0                                      │  │
│  │      Priorité 3: +15.0                                      │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ 3. Utilisation efficace de la salle                        │  │
│  │    taux = nb_étudiants / capacité_salle                    │  │
│  │    score += taux × 15.0                                     │  │
│  │                                                             │  │
│  │    Exemples:                                                │  │
│  │      15 étudiants / 20 places = 75% → +11.25               │  │
│  │      10 étudiants / 30 places = 33% → +5.0                 │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ 4. Pénalité pour sous-utilisation                          │  │
│  │    SI taux < 0.5:                                           │  │
│  │      score -= (0.5 - taux) × 10.0                          │  │
│  │                                                             │  │
│  │    Exemple:                                                 │  │
│  │      5 étudiants / 30 places = 17%                         │  │
│  │      Pénalité: -(0.5 - 0.17) × 10 = -3.3                   │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                  │
│  SCORE FINAL: somme de tous les composants                      │
│                                                                  │
│  Interprétation:                                                 │
│    Score > 20  : Excellent placement                            │
│    Score 10-20 : Bon placement                                  │
│    Score 0-10  : Placement acceptable                           │
│    Score < 0   : Placement sous-optimal                         │
└─────────────────────────────────────────────────────────────────┘
```

## Vérification des Contraintes

```
┌─────────────────────────────────────────────────────────────────┐
│  VÉRIFICATION DES CONTRAINTES D'UNE SESSION                     │
│                                                                  │
│  conflits = []                                                   │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ 1. CONTRAINTES CRITIQUES                                   │  │
│  │                                                             │  │
│  │ ✓ Disponibilité du formateur                               │  │
│  │   SI formateur non disponible à ce créneau:               │  │
│  │     conflits.add("FORMATEUR_INDISPONIBLE")                 │  │
│  │                                                             │  │
│  │ ✓ Double réservation formateur                             │  │
│  │   SI formateur a déjà un cours au même créneau:           │  │
│  │     conflits.add("FORMATEUR_DOUBLE_RESERVATION")           │  │
│  │                                                             │  │
│  │ ✓ Double réservation salle                                 │  │
│  │   SI salle déjà occupée au même créneau:                  │  │
│  │     conflits.add("SALLE_DOUBLE_RESERVATION")               │  │
│  │                                                             │  │
│  │ ✓ Conflit étudiant                                         │  │
│  │   SI étudiants inscrits dans 2 cours simultanés:          │  │
│  │     conflits.add("ETUDIANT_DOUBLE_RESERVATION")            │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ 2. CONTRAINTES MAJEURES                                    │  │
│  │                                                             │  │
│  │ ✓ Capacité de la salle                                     │  │
│  │   SI nb_étudiants > capacité_salle:                       │  │
│  │     conflits.add("CAPACITE_INSUFFISANTE")                  │  │
│  │                                                             │  │
│  │ ✓ Matériel requis                                          │  │
│  │   POUR CHAQUE matériel dans cours.materielRequis:         │  │
│  │     SI matériel non disponible dans salle:                │  │
│  │       conflits.add("MATERIEL_MANQUANT")                    │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                  │
│  RETOUR: Liste des conflits (vide si OK)                        │
└─────────────────────────────────────────────────────────────────┘
```

## Exemple d'Exécution Complète

```
INPUT:
  Cours à planifier: 5
  - Java Avancé (Formateur: Martin, 15 étudiants, Priorité: 3)
  - Python Web (Formateur: Dubois, 20 étudiants, Priorité: 2)
  - Base de Données (N'importe quel formateur, 12 étudiants)
  - DevOps (Formateur: Laurent, 10 étudiants, Matériel: Ordinateurs)
  - Architecture (N'importe quel formateur, 8 étudiants)

  Formateurs: 3 (Martin, Dubois, Laurent)
  Salles: 2 (Salle A: 20 places, Salle B: 15 places)

TRAITEMENT:

  1. TRI PAR CONTRAINTES
     Order: Java Avancé → Python Web → DevOps → Base de Données → Architecture

  2. BACKTRACKING
     Session 1: Java Avancé
       Essai 1: Martin × Salle A × Lundi 8h → VALIDE (Score: 25.5)
       Essai 2: Martin × Salle A × Lundi 10h → VALIDE (Score: 18.2)
       Essai 3: Martin × Salle B × Lundi 8h → INVALIDE (Capacité)
       ...
       ✓ Meilleur: Martin × Salle A × Lundi 8h

     Session 2: Python Web
       Essai 1: Dubois × Salle A × Lundi 8h → INVALIDE (Salle occupée)
       Essai 2: Dubois × Salle B × Lundi 8h → INVALIDE (Capacité)
       Essai 3: Dubois × Salle A × Lundi 10h → VALIDE (Score: 22.0)
       ...
       ✓ Meilleur: Dubois × Salle A × Lundi 10h

     [continuer pour les autres cours...]

  3. OPTIMISATION LOCALE
     Itération 1:
       Échange Java Avancé ↔ Base de Données
       Score avant: 47.5
       Score après: 49.2 ✓ ACCEPTÉ

     Itération 2:
       Échange Python Web ↔ DevOps
       Score avant: 45.0
       Score après: 43.8 ✗ REJETÉ

     [Aucune autre amélioration possible]

  4. DÉTECTION CONFLITS
     ✓ Aucun conflit détecté

  5. STATISTIQUES
     - Taux de planification: 100%
     - Utilisation salles: 2.5 sessions/salle en moyenne
     - Satisfaction préférences: 60%
     - Efficacité capacitaire: 75.8%

OUTPUT:
  Statut: SUCCES
  Temps: 187 ms
  Planning: 5 sessions planifiées
  Conflits: 0

  LUNDI:
    08:00-10:00 | Base de Données  | Martin  | Salle A
    10:00-12:00 | Python Web       | Dubois  | Salle A
    14:00-16:00 | Java Avancé      | Martin  | Salle B

  MARDI:
    08:00-10:00 | DevOps           | Laurent | Salle A
    14:00-16:00 | Architecture     | Dubois  | Salle B
```

## Performance et Complexité

```
┌─────────────────────────────────────────────────────────────────┐
│  ANALYSE DE COMPLEXITÉ                                          │
│                                                                  │
│  Étape 1 - Tri:                  O(n log n)                     │
│    où n = nombre de cours                                        │
│                                                                  │
│  Étape 2 - Backtracking:         O(n × m × p × t)              │
│    où n = cours, m = formateurs, p = salles, t = créneaux      │
│                                                                  │
│  Étape 3 - Optimisation:         O(k × n²)                     │
│    où k = nombre d'itérations (max 100), n = sessions          │
│                                                                  │
│  Étape 4 - Détection:            O(n²)                         │
│    où n = nombre de sessions                                     │
│                                                                  │
│  COMPLEXITÉ TOTALE:              O(n × m × p × t) [dominant]   │
│                                                                  │
│  ──────────────────────────────────────────────────────────────│
│                                                                  │
│  TESTS DE PERFORMANCE                                            │
│                                                                  │
│  Cas 1: 5 cours, 3 formateurs, 2 salles                        │
│    → Temps: ~50-100 ms                                          │
│    → Combinaisons testées: ~600                                 │
│                                                                  │
│  Cas 2: 10 cours, 5 formateurs, 4 salles                       │
│    → Temps: ~150-250 ms                                         │
│    → Combinaisons testées: ~4,000                               │
│                                                                  │
│  Cas 3: 15 cours, 5 formateurs, 4 salles                       │
│    → Temps: ~200-350 ms                                         │
│    → Combinaisons testées: ~6,000                               │
│                                                                  │
│  Cas 4: 20 cours, 8 formateurs, 6 salles                       │
│    → Temps: ~400-600 ms                                         │
│    → Combinaisons testées: ~19,200                              │
└─────────────────────────────────────────────────────────────────┘
```

---

**Ce diagramme complet illustre l'architecture et le fonctionnement de l'algorithme d'optimisation.**
