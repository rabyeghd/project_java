# Système de Gestion de Centre de Formation - Guide d'Installation

## 📋 Présentation du Projet

Il s'agit d'un système de gestion de centre de formation basé sur Java avec une base de données MySQL. Le projet comprend :
- 8 classes de modèles (Formateur, Etudiant, Salle, Materiel, Session, Disponibilite, Contrainte, Preference)
- **8 classes DAO avec opérations CRUD complètes**
- Utilitaire de connexion à la base de données via JDBC
- Suite de tests complète pour tous les DAOs
- Script d'exécution pour faciliter le lancement

## 🗂️ Structure du Projet

```
training-center-management/
├── src/
│   ├── models/          # Classes de modèles (POJOs)
│   ├── dao/             # Objets d'accès aux données (DAOs)
│   ├── util/            # Utilitaire de connexion à la base de données
│   └── test/            # Classes de test
├── sql/
│   ├── schema.sql       # Schéma de la base de données
│   └── test_data.sql    # Données de test
└── lib/
    └── mysql-connector-j-9.5.0.jar  # Pilote JDBC MySQL
```

## 🚀 Instructions d'Installation

### 1. Prérequis
- Java JDK 8 ou supérieur
- Serveur MySQL 8.0 ou supérieur
- VS Code avec le pack d'extensions Java

### 2. ⚠️ IMPORTANT : Configuration de l'Espace de Travail VS Code
Pour éviter les erreurs de compilation, vous **DEVEZ** ouvrir le dossier racine du projet dans VS Code :
1. Fermez tous les dossiers individuels ouverts.
2. Dans VS Code, allez dans **Fichier > Ouvrir le dossier...**
3. Sélectionnez le **dossier racine** : `C:\Users\Lenovo\...\training-center-management`
4. Attendez que l'icône de statut "Java" en bas à droite finisse de charger.
5. Si des erreurs persistent, utilisez `Ctrl+Shift+P` et lancez **"Java: Clean Java Language Server Workspace"**.

### 3. Configuration de la Base de Données

**Étape 1 :** Démarrez votre serveur MySQL.

**Étape 2 :** Exécutez le script d'installation automatique ou manullement les fichiers SQL :
```bash
mysql -u root -p < sql/schema.sql
mysql -u root -p < sql/test_data.sql
```

### 4. Lancement Rapide

**Utilisez le fichier batch pour compiler et lancer les tests :**
Double-cliquez sur `RUN.bat` dans le dossier racine.

## 📊 Schéma de la Base de Données

### Tables Créées :
1. **formateurs** - Informations sur les formateurs
2. **etudiants** - Informations sur les étudiants
3. **salles** - Informations sur les salles
4. **materiel** - Inventaire du matériel
5. **sessions** - Sessions de formation
6. **disponibilites** - Disponibilités des formateurs

## 🔧 Opérations DAO Disponibles
Chaque DAO fournit les méthodes : `create()`, `findById()`, `findAll()`, `update()`, et `delete()`.

## ⚠️ Notes Importantes
1. **Mot de passe MySQL :** Mettez à jour le mot de passe dans `src/util/DatabaseConnection.java`.
2. **Pilote JDBC :** Vérifiez que le JAR est présent dans le dossier `lib/`.
