# 🎓 Système d'Optimisation de Planning - Centre de Formation

## 📋 Description

Algorithme d'optimisation avancé pour la génération automatique de planning hebdomadaire pour un centre de formation. Ce système utilise une **approche hybride** combinant:

- **Heuristique de prioritisation** (Most Constrained First)
- **Backtracking** pour exploration des solutions
- **Optimisation locale** (Hill Climbing)
- **Détection automatique de conflits**

## 🏗️ Architecture

```
src/
├── main/
│   ├── java/com/formation/
│   │   ├── models/              # Entités du domaine
│   │   │   ├── TimeSlot.java
│   │   │   ├── Formateur.java
│   │   │   ├── Salle.java
│   │   │   ├── Cours.java
│   │   │   ├── Etudiant.java
│   │   │   └── SessionPlanifiee.java
│   │   │
│   │   ├── services/            # Logique métier
│   │   │   ├── PlanningOptimisationService.java  # ⭐ Algorithme principal
│   │   │   └── ContrainteService.java            # Validation des contraintes
│   │   │
│   │   ├── controllers/         # API REST
│   │   │   └── PlanningController.java
│   │   │
│   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── PlanningRequest.java
│   │   │   └── PlanningResponse.java
│   │   │
│   │   ├── utils/               # Utilitaires
│   │   │   └── TestDataGenerator.java
│   │   │
│   │   ├── test/                # Tests et démonstrations
│   │   │   └── AlgorithmDemo.java
│   │   │
│   │   └── FormationApplication.java  # Point d'entrée
│   │
│   └── resources/
│       └── application.properties
│
└── pom.xml                      # Configuration Maven
```

## 🚀 Démarrage Rapide

### Prérequis

- **Java 17** ou supérieur
- **Maven 3.6+**
- **MySQL 8.0+** (optionnel, H2 embarqué disponible)

### Installation

1. **Cloner/télécharger le projet**

2. **Compiler le projet:**
```bash
mvn clean install
```

3. **Lancer l'application:**
```bash
mvn spring-boot:run
```

L'application démarre sur `http://localhost:8080`

## 📊 Algorithme d'Optimisation

### Étape 1: Tri par Contraintes (Heuristique)

```java
// Les cours sont triés par niveau de contrainte (plus contraint = priorité)
Score de contrainte basé sur:
- Priorité du cours (×10)
- Formateur spécifique requis (+20)
- Disponibilité limitée du formateur
- Nombre d'étudiants
- Matériel requis (×5)
```

### Étape 2: Planification par Backtracking

```java
Pour chaque cours (du plus contraint au moins contraint):
  Pour chaque formateur compatible:
    Pour chaque salle compatible:
      Pour chaque créneau horaire:
        Vérifier toutes les contraintes
        Si aucun conflit:
          Calculer le score de qualité
          Garder la meilleure solution
```

### Étape 3: Optimisation Locale

```java
Tant qu'il y a des améliorations possibles:
  Pour chaque paire de sessions:
    Essayer d'échanger les créneaux horaires
    Si l'échange améliore le score global ET respecte les contraintes:
      Accepter l'échange
```

### Contraintes Vérifiées

✅ **Contraintes CRITIQUES:**
- Disponibilité des formateurs
- Pas de double réservation (formateur/salle)
- Pas d'étudiant dans deux cours simultanés

✅ **Contraintes MAJEURES:**
- Capacité des salles
- Matériel requis disponible

✅ **Optimisations:**
- Préférences horaires des formateurs
- Utilisation efficace des salles
- Priorité des cours

## 🔌 API REST

### Générer un Planning

**POST** `/api/planning/generate`

**Request Body:**
```json
{
  "cours": [
    {
      "id": 1,
      "nom": "Java Avancé",
      "duree": 120,
      "nombreEtudiants": 15,
      "materielRequis": ["Projecteur", "Ordinateurs"],
      "priorite": 2,
      "formateurRequis": {
        "id": 1,
        "nom": "Prof. Martin",
        "specialite": "Java",
        "disponibilites": [...]
      }
    }
  ],
  "formateurs": [...],
  "salles": [...],
  "creneauxPersonnalises": [...]  // Optionnel
}
```

**Response:**
```json
{
  "statut": "SUCCES",
  "message": "Planning généré avec succès",
  "planning": [
    {
      "cours": {...},
      "formateur": {...},
      "salle": {...},
      "timeSlot": {
        "day": "MONDAY",
        "startTime": "08:00:00",
        "endTime": "10:00:00"
      }
    }
  ],
  "coursNonPlanifies": [],
  "conflits": [],
  "statistiques": {
    "nombreSessionsPlanifiees": 10,
    "tauxUtilisationMoyenSalles": 15.5,
    "tauxSatisfactionPreferences": 85.0,
    "efficaciteCapaciteSalles": 78.5
  },
  "tempsExecution": 245
}
```

### Autres Endpoints

- **GET** `/api/planning/timeslots` - Obtenir les créneaux disponibles
- **POST** `/api/planning/verify` - Vérifier les contraintes d'une session
- **POST** `/api/planning/conflicts` - Détecter les conflits dans un planning
- **POST** `/api/planning/score` - Calculer le score d'une session
- **GET** `/api/planning/health` - Health check

## 🧪 Tests et Scénarios

L'application inclut des tests automatiques qui s'exécutent au démarrage:

### Scénario 1: Cas Simple
- 3 formateurs, 2 salles, 5 cours
- Test de fonctionnement basique

### Scénario 2: Charge Élevée
- 5 formateurs, 4 salles, 15 cours
- Test de performance et scalabilité

### Scénario 3: Détection de Conflits
- Disponibilités limitées
- Contraintes serrées
- Test de gestion des conflits

## 📈 Statistiques Générées

Le système fournit des métriques détaillées:

- **Taux de planification** - % de cours planifiés
- **Utilisation des salles** - Nombre moyen de sessions par salle
- **Satisfaction des préférences** - % de créneaux préférés respectés
- **Efficacité capacitaire** - Taux de remplissage des salles
- **Distribution temporelle** - Répartition par jour de la semaine
- **Score de qualité** - Score moyen d'optimisation

## 🔧 Configuration

### Créneaux Horaires par Défaut

```
Lundi - Vendredi:
  08:00 - 10:00
  10:00 - 12:00
  14:00 - 16:00
  16:00 - 18:00
```

Modifiable via `PlanningOptimisationService.setCreneauxDisponibles()`

### Paramètres d'Optimisation

```java
// Dans PlanningOptimisationService.java
private static final int MAX_ITERATIONS = 100; // Optimisation locale
```

## 🎯 Utilisation avec le Backend

### Intégration Spring Boot

```java
@Autowired
private PlanningOptimisationService optimisationService;

@PostMapping("/planning/generate")
public ResponseEntity<PlanningResponse> generate(@RequestBody PlanningRequest request) {
    ResultatOptimisation resultat = optimisationService.genererPlanningOptimise(
        request.getCours(),
        request.getFormateurs(),
        request.getSalles()
    );
    
    // Convertir en response DTO
    return ResponseEntity.ok(convertToResponse(resultat));
}
```

### Intégration Base de Données (MySQL)

1. **Configurer `application.properties`:**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/formation_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

2. **Ajouter annotations JPA aux entités:**

```java
@Entity
@Table(name = "formateurs")
public class Formateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // ... rest of the fields
}
```

## 📦 Dépendances Principales

- **Spring Boot 3.2.0** - Framework backend
- **Spring Web** - API REST
- **Spring Data JPA** - Persistance
- **MySQL Connector** - Base de données
- **SpringDoc OpenAPI** - Documentation API

### Dépendance Optionnelle: Choco Solver

Pour des contraintes très complexes, décommenter dans `pom.xml`:

```xml
<dependency>
    <groupId>org.choco-solver</groupId>
    <artifactId>choco-solver</artifactId>
    <version>4.10.13</version>
</dependency>
```

## 🎨 Génération de Données de Test

```java
// Générer données de test
List<Formateur> formateurs = TestDataGenerator.genererFormateurs(5);
List<Salle> salles = TestDataGenerator.genererSalles(3);
List<Cours> cours = TestDataGenerator.genererCours(10, formateurs);

// Ou générer un scénario complet
Map<String, Object> scenario = TestDataGenerator.genererScenarioComplet(
    5,  // formateurs
    3,  // salles
    20, // étudiants
    10  // cours
);
```

## 🐛 Débogage

Activer les logs détaillés dans `application.properties`:

```properties
logging.level.com.formation=DEBUG
```

## 📝 Exemple Complet

```java
// 1. Créer les données
Formateur f1 = new Formateur(1L, "Dr. Martin", "Java");
f1.addDisponibilite(new TimeSlot(DayOfWeek.MONDAY, 
    LocalTime.of(8, 0), LocalTime.of(18, 0)));

Salle s1 = new Salle(1L, "Salle A", 20);
s1.addEquipement("Projecteur");

Cours c1 = new Cours(1L, "Java 101", 120, 15);
c1.setFormateurRequis(f1);
c1.addMateriel("Projecteur");

// 2. Générer le planning
ResultatOptimisation resultat = optimisationService.genererPlanningOptimise(
    Arrays.asList(c1),
    Arrays.asList(f1),
    Arrays.asList(s1)
);

// 3. Exploiter les résultats
if (resultat.getStatut().equals("SUCCES")) {
    for (SessionPlanifiee session : resultat.getPlanning()) {
        System.out.println(session.getCours().getNom() + " - " + 
                          session.getTimeSlot());
    }
}
```

## 📚 Documentation API Interactive

Une fois l'application lancée, accéder à la documentation Swagger:

```
http://localhost:8080/swagger-ui.html
```

## 🤝 Contribution au Projet

1. **Vous**: Développement de l'algorithme d'optimisation
2. **Équipe**: Intégration backend + frontend + tests

## 📄 Livrables

- ✅ Code source complet
- ✅ Algorithme d'optimisation fonctionnel
- ✅ API REST documentée
- ✅ Tests automatisés
- ✅ Documentation technique

## 🎓 Concepts Utilisés

- **Heuristique Most Constrained First**
- **Backtracking avec élagage**
- **Optimisation locale (Hill Climbing)**
- **Constraint Satisfaction Problem (CSP)**
- **Évaluation par fonction de score**

## 💡 Améliorations Futures

- Algorithme génétique pour très grandes instances
- Parallélisation du backtracking
- Cache des solutions partielles
- Support multi-semaines
- Contraintes soft/hard pondérées
- Interface web de visualisation

---

**Auteur**: Votre Nom  
**Projet**: Gestion Centre de Formation  
**Date**: Décembre 2025  
**Version**: 1.0.0
