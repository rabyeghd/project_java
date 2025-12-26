# Training Center Management System - Setup Guide

## 📋 Project Overview

This is a Java-based training center management system with MySQL database backend. The project includes:
- 8 Model classes (Formateur, Etudiant, Salle, Materiel, Session, Disponibilite, Contrainte, Preference)
- **8 DAO classes with full CRUD operations** (FormateurDAO, EtudiantDAO, SalleDAO, MaterielDAO, SessionDAO, DisponibiliteDAO, ContrainteDAO, PreferenceDAO)
- Database connection utility using JDBC
- Comprehensive test suite for all DAOs
- PowerShell run script for easy execution

## 🗂️ Project Structure

```
training-center-management/
├── src/
│   ├── models/          # Model classes (POJOs)
│   ├── dao/             # Data Access Objects
│   ├── util/            # Database connection utility
│   └── test/            # Test classes
├── sql/
│   ├── schema.sql       # Database schema
│   └── test_data.sql    # Test data
└── lib/
    └── mysql-connector-java-8.0.33.jar  # MySQL JDBC driver
```

## 🚀 Setup Instructions

### 1. Prerequisites
- Java JDK 8 or higher
- MySQL Server 8.0 or higher
- VS Code with Java Extension Pack

### 2. ⚠️ IMPORTANT: VS Code Workspace Setup
To avoid "Unresolved compilation problem" and "ClassNotFoundException", you **MUST** open the project root folder in VS Code:
1.  Close any individual folders (`src/app`, `src/dao`, etc.) you have open.
2.  In VS Code, go to **File > Open Folder...**
3.  Select the **root folder**: `C:\Users\Lenovo\.gemini\antigravity\scratch\training-center-management`
4.  If prompted, click **"Yes, I trust the authors"**.
5.  Wait for the "Java" status icon in the bottom right to finish loading.
6.  If you still see errors, press `Ctrl+Shift+P` and run **"Java: Clean Java Language Server Workspace"**.

### 2. Database Setup

**Step 1:** Start MySQL server and login:
```bash
mysql -u root -p
```

**Step 2:** Execute the schema file:
```sql
source C:/Users/Lenovo/.gemini/antigravity/scratch/training-center-management/sql/schema.sql
```

**Step 3:** Execute the test data file:
```sql
source C:/Users/Lenovo/.gemini/antigravity/scratch/training-center-management/sql/test_data.sql
```

**Step 4:** Verify the database:
```sql
USE centre_formation;
SHOW TABLES;
SELECT * FROM formateurs;
```

### 3. MySQL JDBC Driver Setup

**Download MySQL Connector/J:**
- Visit: https://dev.mysql.com/downloads/connector/j/
- Download version 8.0.33 (Platform Independent ZIP)
- Extract and copy `mysql-connector-java-8.0.33.jar` to the `lib/` folder

### 4. Database Configuration

**Update database credentials in `src/util/DatabaseConnection.java`:**
```java
private static final String URL = "jdbc:mysql://localhost:3306/centre_formation";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_password_here";
```

### 5. VS Code Configuration

**Create `.vscode/settings.json`:**
```json
{
    "java.project.sourcePaths": ["src"],
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
}
```

## 🚀 Quick Start

**Easy way to run tests (using PowerShell script):**
```powershell
cd C:\Users\Lenovo\.gemini\antigravity\scratch\training-center-management
.\run-tests.ps1
```

This script will automatically:
1. Check if Java is installed
2. Compile all Java files
3. Run the comprehensive test suite

## 🧪 Running the Tests (Manual)

### Compile the project:
```bash
cd C:\Users\Lenovo\.gemini\antigravity\scratch\training-center-management
javac -cp ".;lib/*" -d bin src/models/*.java src/util/*.java src/dao/*.java src/test/*.java
```

### Run the test class:
```bash
java -cp ".;bin;lib/*" test.TestCRUD
```

## 📊 Database Schema

### Tables Created:
1. **formateurs** - Trainer information
2. **etudiants** - Student information
3. **salles** - Room information
4. **materiel** - Equipment inventory
5. **sessions** - Training sessions
6. **session_etudiants** - Session-Student relationships
7. **session_materiel** - Session-Equipment relationships
8. **disponibilites** - Trainer availability
9. **contraintes** - System constraints
10. **preferences** - User preferences

## 🔧 DAO Operations Available

Each DAO provides:
- `create()` - Insert new records
- `findById()` - Retrieve by ID
- `findAll()` - List all records
- `update()` - Modify existing records
- `delete()` - Remove records
- Additional search methods (by specialite, niveau, type, etc.)

## 📝 Test Data Included

- 3 Formateurs (Trainers)
- 5 Etudiants (Students)
- 2 Salles (Rooms)
- 3 Types of Materiel (Equipment)
- 2 Sample Sessions
- Availability schedules
- Constraints and preferences

## ⚠️ Important Notes

1. **MySQL Password:** Update the password in `DatabaseConnection.java` before running
2. **JDBC Driver:** Ensure the MySQL connector JAR is in the `lib/` folder
3. **Database Name:** The database is named `centre_formation`
4. **Port:** MySQL runs on default port 3306

## 🎯 Next Steps

After setup, you can:
1. Run the test class to verify all CRUD operations
2. Extend the DAOs with additional business logic
3. Create a web interface (HTML/CSS/JavaScript)
4. Implement the scheduling optimization algorithm
5. Add authentication and authorization

## 📞 Troubleshooting

**Connection Error:**
- Verify MySQL is running
- Check username/password in DatabaseConnection.java
- Ensure database `centre_formation` exists

**ClassNotFoundException:**
- Verify MySQL connector JAR is in lib/ folder
- Check VS Code settings.json includes lib path

**SQL Errors:**
- Ensure schema.sql executed successfully
- Check for foreign key constraint violations
