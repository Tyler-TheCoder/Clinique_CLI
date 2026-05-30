# Medical Clinic Management System
Mini-Project — Object-Oriented Programming (OOP) in Java
Level: ACAD L2 Computer Science 

---

## How to Compile and Run

The `bin/` folder is empty by default. You must compile the source code first before
you can run the program. Skipping the compile step will result in the following error:

    Error: Could not find or load main class main.Main

Follow these two steps in order every time you want to run the program.

### Step 1 — Compile

Open a terminal in the root of the project (where `src/` and `bin/` are located) in `ProjetPOO_Clinique`, then run:

**Linux / macOS:**

```bash
javac -d bin -sourcepath src $(find src -name "*.java")
```

**Windows (CMD or PowerShell):**

```cmd
javac -d bin -sourcepath src src\modele\*.java src\exception\*.java src\gestion\*.java src\main\*.java
```

This command reads all `.java` files from `src/` and places the compiled `.class` files
into `bin/`, preserving the package structure. You only need to repeat this step if you
make changes to the source code.

### Step 2 — Run

After a successful compilation, run:

```bash
java -cp bin main.Main
```

The `-cp bin` flag tells Java where to find the compiled class files.
The program will start, load demo data automatically, and display the main menu.

---

## Prerequisites

- Java JDK 17 or higher (tested with JDK 21)
- No external libraries required

To verify your Java installation:
```bash
java -version
javac -version
```

---

## Project Structure

```
ProjetPOO_Clinique/
|
+-- src/
|   +-- modele/
|   |   +-- Personne.java             (abstract base class)
|   |   +-- Patient.java              (extends Personne)
|   |   +-- PersonnelMedical.java     (abstract, extends Personne)
|   |   +-- Medecin.java              (extends PersonnelMedical)
|   |   +-- Infirmier.java            (extends PersonnelMedical)
|   |   +-- Consultation.java         (links Patient and Medecin)
|   |   +-- Ordonnance.java           (linked to a Consultation)
|   |   +-- Medicament.java           (prescribed drug)
|   |
|   +-- gestion/
|   |   +-- CliniqueMedicale.java     (central management class)
|   |
|   +-- exception/
|   |   +-- PatientInexistantException.java
|   |   +-- MedecinIndisponibleException.java
|   |   +-- DossierMedicalException.java
|   |   +-- OrdonnanceInvalideException.java
|   |
|   +-- main/
|       +-- Main.java                 (entry point and console menu)
|
+-- bin/                              (empty by default, filled after compilation)
+-- README.md
|
+-- Rapport.docx                      (rapport Explains the code in details)
```

---

## Packages

| Package     | Description                                    |
| ----------- | ---------------------------------------------- |
| `modele`    | All business entity classes                    |
| `gestion`   | Application logic and data management          |
| `exception` | Four custom business exceptions                |
| `main`      | Entry point and interactive console menu (CLI) |

---

## Class Hierarchy

```
Personne  (abstract)
    |
    +-- Patient
    |
    +-- PersonnelMedical  (abstract)
            |
            +-- Medecin
            |
            +-- Infirmier
```

---

## Features

When launched, the application automatically loads demo data (3 patients, 2 doctors,
1 nurse, 2 consultations, 1 prescription) so you can test all features immediately.

The interactive console menu offers the following options:

| **Option** | **Description**                                                    |
| ---------- | ------------------------------------------------------------------ |
| 1          | Register a new patient, view their medical file, list all patients |
| 2          | Add a doctor or nurse, display all staff                           |
| 3          | Create a consultation (checks doctor availability automatically)   |
| 4          | Create a prescription, add medications, validate                   |
| 5          | Search consultations by patient or by doctor                       |
| 6          | Dashboard — total patients, consultations, and revenue             |
| 0          | Quit                                                               |

---

## OOP Concepts Implemented

### Encapsulation
All attributes are declared `private`. Setters include business validation:
- `groupeSanguin` only accepts: A+, A-, B+, B-, O+, O-, AB+, AB-
- `salaire` must be >= 0
- `tarifConsultation` must be > 0

### Inheritance
Full hierarchy using `extends`, with `super()` called in every subclass constructor:
- Personne -> Patient
- Personne -> PersonnelMedical -> Medecin
- Personne -> PersonnelMedical -> Infirmier

### Polymorphism
- `afficherProfil()` is overridden with `@Override` in Patient, Medecin, and Infirmier
- `toString()` is overridden in all classes
- Polymorphic call demonstrated via `List<Personne>` in `afficherToutLePersonnel()`
- Constructor overloading in `Consultation` (with or without clinical notes)

### Abstraction
- `Personne` is an abstract class and cannot be instantiated directly
- `afficherProfil()` is an abstract method that every subclass must implement

### Exception Handling
Four custom exceptions in the `exception` package:

| Exception                      | Thrown when                                        |
|--------------------------------|----------------------------------------------------|
| `PatientInexistantException`   | No patient found for the given ID                  |
| `MedecinIndisponibleException` | Doctor already has an appointment at that time     |
| `DossierMedicalException`      | Medical file is invalid or inaccessible            |
| `OrdonnanceInvalideException`  | Prescription has no medications or no diagnosis    |

`try-catch-finally` blocks are used in `creerConsultation()` and `creerOrdonnance()`.

---

## Important Notes

- The application runs entirely in the console — no graphical interface
- Data is not persisted between sessions (no database)
- Dates must be entered in the format `YYYY-MM-DD` (example: 2026-05-03)
- Consultation dates include time: `YYYY-MM-DD HH:MM` (example: 2026-05-03 09:00)
- UTF-8 encoding is recommended to display accented characters correctly

---

## Group members

> Bounoua Akram 
> Kadouche Mohamed Chakib
> Boumara brahim
> Ouznaji Sohaib
> Bouzouad Myriam Malak

---

Module: Object-Oriented Programming (OOP) | Academic year 2025-2026
# Clinique_CLI
