package main;

import exception.*;
import gestion.CliniqueMedicale;
import modele.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Point d'entrée principal de l'application.
 * Contient le menu interactif et toutes les validations de saisie utilisateur.
 */
public class Main {

    private static Scanner          scanner  = new Scanner(System.in);
    private static CliniqueMedicale clinique;

    // ─── Formats de date attendus ─────────────────────────────────────────────
    private static final String FORMAT_DATE       = "yyyy-MM-dd";        // 10 chars
    private static final String FORMAT_DATETIME   = "yyyy-MM-dd HH:mm";  // 16 chars

    // ═══════════════════════════════════════════════════════════════════════
    // POINT D'ENTREE
    // ═══════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        clinique = new CliniqueMedicale("Clinique El Shifa", "Rue Didouche Mourad, Alger");
        chargerDonneesDemonstration();

        boolean continuer = true;
        while (continuer) {
            afficherMenuPrincipal();
            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1  -> menuPatients();
                case 2  -> menuPersonnel();
                case 3  -> menuConsultations();
                case 4  -> menuOrdonnances();
                case 5  -> menuRecherche();
                case 6  -> clinique.afficherTableauDeBord();
                case 0  -> { continuer = false; System.out.println("\nAu revoir !\n"); }
                default -> System.out.println("⚠ Choix invalide. Veuillez réessayer.");
            }
        }
        scanner.close();
    }

    // ═══════════════════════════════════════════════════════════════════════
    // MENUS PRINCIPAUX
    // ═══════════════════════════════════════════════════════════════════════

    private static void afficherMenuPrincipal() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║      CLINIQUE MEDICALE — MENU PRINCIPAL      ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║  1. Gestion des Patients                     ║");
        System.out.println("║  2. Gestion du Personnel médical             ║");
        System.out.println("║  3. Gestion des Consultations                ║");
        System.out.println("║  4. Gestion des Ordonnances                  ║");
        System.out.println("║  5. Recherche                                ║");
        System.out.println("║  6. Tableau de bord                          ║");
        System.out.println("║  0. Quitter                                  ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MENU PATIENTS
    // ─────────────────────────────────────────────────────────────────────────
    private static void menuPatients() {
        System.out.println("\n── GESTION DES PATIENTS ──");
        System.out.println("  1. Enregistrer un nouveau patient");
        System.out.println("  2. Consulter le dossier d'un patient");
        System.out.println("  3. Lister tous les patients");
        System.out.println("  4. Ajouter un antécédent médical");
        System.out.println("  0. Retour");

        switch (lireEntier("Votre choix : ")) {
            case 1  -> enregistrerNouveauPatient();
            case 2  -> consulterDossierPatient();
            case 3  -> clinique.afficherTousLesPatients();
            case 4  -> ajouterAntecedent();
            case 0  -> {}
            default -> System.out.println("⚠ Choix invalide.");
        }
    }

    /** Formulaire complet d'enregistrement d'un nouveau patient */
    private static void enregistrerNouveauPatient() {
        System.out.println("\n─ Nouveau Patient ─");
        String nom    = lireTexteNonVide("Nom             : ");
        String prenom = lireTexteNonVide("Prénom          : ");
        String dNais  = lireDate("Date naissance  : ");
        String tel    = lireTelephone("Téléphone       : ");
        String nss    = lireTexteNonVide("N° Sécu. Sociale: ");
        String groupe = lireGroupeSanguin("Groupe sanguin  : ");

        Patient p = new Patient(nom, prenom, dNais, tel, nss, groupe);
        clinique.enregistrerPatient(p);
        System.out.println("  ID attribué : " + p.getIdentifiant());
    }

    /** Affiche le dossier complet d'un patient par ID */
    private static void consulterDossierPatient() {
        clinique.afficherTousLesPatients();
        String id = lireTexteNonVide("ID du patient : ");
        try {
            Patient p = clinique.rechercherPatient(id);
            p.afficherDossier();
        } catch (PatientInexistantException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Ajoute un antécédent médical à un patient existant */
    private static void ajouterAntecedent() {
        clinique.afficherTousLesPatients();
        String id = lireTexteNonVide("ID du patient : ");
        try {
            Patient p   = clinique.rechercherPatient(id);
            String  ant = lireTexteNonVide("Antécédent à ajouter : ");
            p.ajouterAntecedent(ant);
        } catch (PatientInexistantException e) {
            System.out.println(e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MENU PERSONNEL
    // ─────────────────────────────────────────────────────────────────────────
    private static void menuPersonnel() {
        System.out.println("\n── GESTION DU PERSONNEL MÉDICAL ──");
        System.out.println("  1. Ajouter un médecin");
        System.out.println("  2. Ajouter un infirmier");
        System.out.println("  3. Afficher tout le personnel");
        System.out.println("  0. Retour");

        switch (lireEntier("Votre choix : ")) {
            case 1  -> ajouterMedecin();
            case 2  -> ajouterInfirmier();
            case 3  -> clinique.afficherToutLePersonnel();
            case 0  -> {}
            default -> System.out.println("⚠ Choix invalide.");
        }
    }

    /** Formulaire d'ajout d'un médecin avec toutes les validations */
    private static void ajouterMedecin() {
        System.out.println("\n─ Nouveau Médecin ─");
        String nom       = lireTexteNonVide("Nom                     : ");
        String prenom    = lireTexteNonVide("Prénom                  : ");
        // Âge minimum 18 ans pour tout le personnel médical
        String dNais     = lireDateAvecAgeMin("Date naissance          : ", 18);
        String tel       = lireTelephone("Téléphone               : ");
        String matricule = lireTexteNonVide("Matricule               : ");
        String dEmbauch  = lireDate("Date embauche           : ");
        double salaire   = lireDoubleMin("Salaire (DA)            : ", 0);
        String spec      = lireTexteNonVide("Spécialité              : ");
        String numOrdre  = lireTexteNonVide("N° Ordre                : ");
        double tarif     = lireDoubleMin("Tarif consultation (DA) : ", 1);

        Medecin m = new Medecin(nom, prenom, dNais, tel, matricule,
                                dEmbauch, salaire, spec, numOrdre, tarif);
        clinique.ajouterMedecin(m);
        System.out.println("  ID attribué : " + m.getIdentifiant());
    }

    /** Formulaire d'ajout d'un infirmier avec toutes les validations */
    private static void ajouterInfirmier() {
        System.out.println("\n─ Nouvel Infirmier ─");
        String nom       = lireTexteNonVide("Nom             : ");
        String prenom    = lireTexteNonVide("Prénom          : ");
        // Âge minimum 18 ans pour tout le personnel médical
        String dNais     = lireDateAvecAgeMin("Date naissance  : ", 18);
        String tel       = lireTelephone("Téléphone       : ");
        String matricule = lireTexteNonVide("Matricule       : ");
        String dEmbauch  = lireDate("Date embauche   : ");
        double salaire   = lireDoubleMin("Salaire (DA)    : ", 0);
        String service   = lireTexteNonVide("Service         : ");
        String grade     = lireTexteNonVide("Grade           : ");

        Infirmier inf = new Infirmier(nom, prenom, dNais, tel, matricule,
                                     dEmbauch, salaire, service, grade);
        clinique.ajouterInfirmier(inf);
        System.out.println("  ID attribué : " + inf.getIdentifiant());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MENU CONSULTATIONS
    // ─────────────────────────────────────────────────────────────────────────
    private static void menuConsultations() {
        System.out.println("\n── GESTION DES CONSULTATIONS ──");
        System.out.println("  1. Créer une consultation");
        System.out.println("  2. Lister toutes les consultations");
        System.out.println("  3. Marquer une consultation comme terminée");
        System.out.println("  0. Retour");

        switch (lireEntier("Votre choix : ")) {
            case 1  -> creerConsultation();
            case 2  -> listerConsultations();
            case 3  -> terminerConsultation();
            case 0  -> {}
            default -> System.out.println("⚠ Choix invalide.");
        }
    }

    /** Formulaire de création d'une consultation */
    private static void creerConsultation() {
        System.out.println("\n─ Nouvelle Consultation ─");

        // Affiche les listes pour aider l'utilisateur à choisir les IDs
        clinique.afficherTousLesPatients();
        String idPatient = lireIdExistant("ID du patient  : ", "patient");

        clinique.afficherToutLePersonnel();
        String idMedecin  = lireIdExistant("ID du médecin  : ", "medecin");
        String date        = lireDateHeure("Date (YYYY-MM-DD HH:MM) : ");
        String diagnostic  = lireTexteNonVide("Diagnostic     : ");

        System.out.print("Notes cliniques (Entrée pour passer) : ");
        String notes = scanner.nextLine().trim();

        try {
            clinique.creerConsultation(idPatient, idMedecin, date, diagnostic, notes);
        } catch (MedecinIndisponibleException | PatientInexistantException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Affiche toutes les consultations */
    private static void listerConsultations() {
        List<Consultation> liste = clinique.getConsultations();
        if (liste.isEmpty()) {
            System.out.println("  Aucune consultation enregistrée.");
            return;
        }
        System.out.println("\n══ TOUTES LES CONSULTATIONS (" + liste.size() + ") ══");
        for (Consultation c : liste) {
            c.afficher();
        }
    }

    /** Marque une consultation comme terminée */
    private static void terminerConsultation() {
        listerConsultations();
        String id = lireTexteNonVide("ID de la consultation (ex: C001) : ");
        for (Consultation c : clinique.getConsultations()) {
            if (c.getIdConsultation().equalsIgnoreCase(id)) {
                c.setStatut(Consultation.STATUT_TERMINEE);
                System.out.println("✔ Consultation " + id + " marquée comme terminée.");
                return;
            }
        }
        System.out.println("⚠ Consultation non trouvée : " + id);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MENU ORDONNANCES
    // ─────────────────────────────────────────────────────────────────────────
    private static void menuOrdonnances() {
        System.out.println("\n── GESTION DES ORDONNANCES ──");
        System.out.println("  1. Créer une ordonnance");
        System.out.println("  2. Ajouter un médicament à une ordonnance");
        System.out.println("  3. Afficher une ordonnance");
        System.out.println("  0. Retour");

        switch (lireEntier("Votre choix : ")) {
            case 1  -> creerOrdonnance();
            case 2  -> ajouterMedicament();
            case 3  -> afficherOrdonnance();
            case 0  -> {}
            default -> System.out.println("⚠ Choix invalide.");
        }
    }

    /**
     * Crée une ordonnance ET ajoute les médicaments en une seule étape.
     */
    private static void creerOrdonnance() {
        // 1. Choisir la consultation
        listerConsultations();
        if (clinique.getConsultations().isEmpty()) return;

        String idCons = lireTexteNonVide("ID de la consultation : ");
        Consultation consultation = null;
        for (Consultation c : clinique.getConsultations()) {
            if (c.getIdConsultation().equalsIgnoreCase(idCons)) {
                consultation = c;
                break;
            }
        }
        if (consultation == null) {
            System.out.println("⚠ Consultation non trouvée : " + idCons);
            return;
        }

        String dateEmission = lireDate("Date d'émission : ");

        // 2. Créer l'ordonnance
        Ordonnance ordonnance;
        try {
            ordonnance = clinique.creerOrdonnance(consultation, dateEmission);
        } catch (OrdonnanceInvalideException e) {
            System.out.println(e.getMessage());
            return;
        }

        // 3. Ajouter les médicaments directement dans la foulée
        int nbMed = lireEntierMin("Nombre de médicaments à ajouter : ", 0);
        for (int i = 1; i <= nbMed; i++) {
            System.out.println("\n  Médicament " + i + " :");
            saisirEtAjouterMedicament(ordonnance);
        }

        // 4. Afficher le résultat final
        System.out.println();
        try {
            clinique.validerOrdonnance(ordonnance);
            ordonnance.afficher();
        } catch (OrdonnanceInvalideException e) {
            System.out.println(e.getMessage());
        }
    }

    /** Ajoute un médicament à une ordonnance déjà existante */
    private static void ajouterMedicament() {
        Ordonnance ord = choisirOrdonnance();
        if (ord == null) return;
        saisirEtAjouterMedicament(ord);
    }

    /** Affiche une ordonnance choisie par l'utilisateur */
    private static void afficherOrdonnance() {
        Ordonnance ord = choisirOrdonnance();
        if (ord == null) return;
        ord.afficher();
    }

    /**
     * Saisie d'un médicament et ajout direct à une ordonnance.
     * Factorisé pour éviter la duplication entre creerOrdonnance() et ajouterMedicament().
     */
    private static void saisirEtAjouterMedicament(Ordonnance ordonnance) {
        String nom    = lireTexteNonVide("  Nom du médicament       : ");
        String dosage = lireTexteNonVide("  Dosage (ex: 500mg)      : ");
        String duree  = lireTexteNonVide("  Durée du traitement     : ");
        System.out.print("  Contre-indications (Entrée pour passer) : ");
        String contre = scanner.nextLine().trim();

        Medicament med = contre.isBlank()
                ? new Medicament(nom, dosage, duree)
                : new Medicament(nom, dosage, duree, contre);

        ordonnance.ajouterMedicament(med);
    }

    /**
     * Affiche la liste des ordonnances et demande à l'utilisateur d'en choisir une.
     * @return l'ordonnance choisie, ou null si aucune n'existe ou ID introuvable
     */
    private static Ordonnance choisirOrdonnance() {
        List<Ordonnance> ordonnances = clinique.getOrdonnances();
        if (ordonnances.isEmpty()) {
            System.out.println("⚠ Aucune ordonnance disponible.");
            return null;
        }
        System.out.println("\n  Ordonnances disponibles :");
        for (Ordonnance o : ordonnances) {
            System.out.println("  → " + o.getIdOrdonnance()
                    + " | Patient : " + o.getConsultation().getPatient().getPrenom()
                    + " " + o.getConsultation().getPatient().getNom()
                    + " | Date : " + o.getDateEmission()
                    + " | Médicaments : " + o.getMedicaments().size());
        }
        String idOrd = lireTexteNonVide("ID de l'ordonnance : ");
        for (Ordonnance o : ordonnances) {
            if (o.getIdOrdonnance().equalsIgnoreCase(idOrd)) return o;
        }
        System.out.println("⚠ Ordonnance non trouvée : " + idOrd);
        return null;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MENU RECHERCHE
    // ─────────────────────────────────────────────────────────────────────────
    private static void menuRecherche() {
        System.out.println("\n── RECHERCHE ──");
        System.out.println("  1. Consultations par patient");
        System.out.println("  2. Consultations par médecin");
        System.out.println("  0. Retour");

        switch (lireEntier("Votre choix : ")) {
            case 1 -> {
                clinique.afficherTousLesPatients();
                String idP = lireTexteNonVide("ID du patient : ");
                List<Consultation> rP = clinique.rechercherConsultationsParPatient(idP);
                if (rP.isEmpty()) System.out.println("  Aucune consultation trouvée.");
                else rP.forEach(Consultation::afficher);
            }
            case 2 -> {
                clinique.afficherToutLePersonnel();
                String idM = lireTexteNonVide("ID du médecin : ");
                List<Consultation> rM = clinique.rechercherConsultationsParMedecin(idM);
                if (rM.isEmpty()) System.out.println("  Aucune consultation trouvée.");
                else rM.forEach(Consultation::afficher);
            }
            case 0  -> {}
            default -> System.out.println("⚠ Choix invalide.");
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // DONNEES DE DEMONSTRATION
    // ═══════════════════════════════════════════════════════════════════════

    private static void chargerDonneesDemonstration() {
        System.out.println("\nChargement des données de démonstration...\n");

        // Patients (IDs auto: P001, P002, P003)
        Patient p1 = new Patient("Benali", "Amira", "1995-03-14",
                "0555123456", "1950314059001", "A+");
        p1.ajouterAntecedent("Diabète type 2");
        p1.ajouterAntecedent("Hypertension artérielle");

        Patient p2 = new Patient("Khelifi", "Yacine", "1988-07-22",
                "0661987654", "1880722059002", "O-");
        p2.ajouterAntecedent("Asthme");

        Patient p3 = new Patient("Zidane", "Sara", "2001-11-05",
                "0770456789", "2011105059003", "B+");

        clinique.enregistrerPatient(p1);
        clinique.enregistrerPatient(p2);
        clinique.enregistrerPatient(p3);

        // Médecins (IDs auto: M001, M002)
        Medecin m1 = new Medecin("Bouzid", "Karim", "1975-05-10",
                "0555001001", "MAT-001", "2005-09-01", 180000,
                "Cardiologie", "ORD-1234", 2000);

        Medecin m2 = new Medecin("Ferhat", "Nadia", "1980-02-28",
                "0661002002", "MAT-002", "2010-03-15", 160000,
                "Pédiatrie", "ORD-5678", 1500);

        clinique.ajouterMedecin(m1);
        clinique.ajouterMedecin(m2);

        // Infirmier (ID auto: I001)
        Infirmier inf1 = new Infirmier("Hamdi", "Rachida", "1990-08-18",
                "0770003003", "INF-001", "2015-06-01", 90000,
                "Urgences", "Infirmière principale");
        clinique.ajouterInfirmier(inf1);

        // Consultations
        try {
            Consultation c1 = clinique.creerConsultation(
                    p1.getIdentifiant(), m1.getIdentifiant(),
                    "2026-05-03 09:00",
                    "Insuffisance cardiaque légère",
                    "Patient essoufflé à l'effort. ECG recommandé.");
            if (c1 != null) c1.setStatut(Consultation.STATUT_TERMINEE);

            Consultation c2 = clinique.creerConsultation(
                    p2.getIdentifiant(), m2.getIdentifiant(),
                    "2026-05-03 10:30",
                    "Rhinopharyngite aiguë", null);

            // Ordonnance pour c1
            if (c1 != null) {
                Ordonnance ord1 = clinique.creerOrdonnance(c1, "2026-05-03");
                ord1.ajouterMedicament(new Medicament(
                        "Ramipril", "5mg — 1 fois/jour", "3 mois",
                        "Insuffisance rénale sévère"));
                ord1.ajouterMedicament(new Medicament(
                        "Furosémide", "40mg — matin", "1 mois"));
            }
        } catch (MedecinIndisponibleException | PatientInexistantException
                 | OrdonnanceInvalideException e) {
            System.out.println("⚠ Erreur données démo : " + e.getMessage());
        }

        System.out.println("Données de démonstration chargées.\n");
    }

    // ═══════════════════════════════════════════════════════════════════════
    // UTILITAIRES DE SAISIE ET VALIDATION
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Lit une chaîne non vide. Redemande si l'utilisateur appuie juste sur Entrée.
     */
    private static String lireTexteNonVide(String invite) {
        while (true) {
            System.out.print(invite);
            String val = scanner.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.println("⚠ Ce champ ne peut pas être vide.");
        }
    }

    /**
     * Lit un entier. Redemande si la saisie n'est pas un nombre.
     */
    private static int lireEntier(String invite) {
        while (true) {
            try {
                System.out.print(invite);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠ Veuillez entrer un nombre entier.");
            }
        }
    }

    /**
     * Lit un entier >= min. Redemande si la valeur est trop petite.
     */
    private static int lireEntierMin(String invite, int min) {
        while (true) {
            int v = lireEntier(invite);
            if (v >= min) return v;
            System.out.println("⚠ La valeur doit être >= " + min + ".");
        }
    }

    /**
     * Lit un double >= min (utilisé pour salaire, tarif).
     */
    private static double lireDoubleMin(String invite, double min) {
        while (true) {
            try {
                System.out.print(invite);
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val >= min) return val;
                System.out.println("⚠ La valeur doit être >= " + min + ".");
            } catch (NumberFormatException e) {
                System.out.println("⚠ Veuillez entrer un nombre valide (ex: 1500.0).");
            }
        }
    }

    /**
     * Lit un numéro de téléphone : doit contenir exactement 10 caractères.
     */
    private static String lireTelephone(String invite) {
        while (true) {
            System.out.print(invite + "(10 chiffres) : ");
            String val = scanner.nextLine().trim();
            if (val.length() == 10) return val;
            System.out.println("⚠ Le téléphone doit contenir exactement 10 caractères. "
                    + "Vous avez saisi " + val.length() + " caractère(s).");
        }
    }

    /**
     * Lit une date au format YYYY-MM-DD.
     * Vérifie la longueur (10 chars) et que le format est bien respecté.
     */
    private static String lireDate(String invite) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(FORMAT_DATE);
        while (true) {
            System.out.print(invite + "(YYYY-MM-DD) : ");
            String val = scanner.nextLine().trim();
            if (val.length() != 10) {
                System.out.println("⚠ La date doit faire exactement 10 caractères (ex: 2001-04-15).");
                continue;
            }
            try {
                LocalDate.parse(val, fmt);
                return val;
            } catch (DateTimeParseException e) {
                System.out.println("⚠ Date invalide. Format attendu : YYYY-MM-DD (ex: 2001-04-15).");
            }
        }
    }

    /**
     * Lit une date + heure au format YYYY-MM-DD HH:MM.
     * Vérifie la longueur (16 chars) et le format.
     */
    private static String lireDateHeure(String invite) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(FORMAT_DATETIME);
        while (true) {
            System.out.print(invite);
            String val = scanner.nextLine().trim();
            if (val.length() != 16) {
                System.out.println("⚠ La date doit faire exactement 16 caractères (ex: 2026-05-10 09:00).");
                continue;
            }
            try {
                java.time.LocalDateTime.parse(val, fmt);
                return val;
            } catch (DateTimeParseException e) {
                System.out.println("⚠ Format invalide. Attendu : YYYY-MM-DD HH:MM (ex: 2026-05-10 09:00).");
            }
        }
    }

    /**
     * Lit une date de naissance et vérifie que l'âge calculé est >= ageMin.
     * Utilisé pour le personnel médical (âge minimum 18 ans).
     */
    private static String lireDateAvecAgeMin(String invite, int ageMin) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(FORMAT_DATE);
        while (true) {
            System.out.print(invite + "(YYYY-MM-DD) : ");
            String val = scanner.nextLine().trim();
            if (val.length() != 10) {
                System.out.println("⚠ La date doit faire exactement 10 caractères (ex: 1985-06-20).");
                continue;
            }
            try {
                LocalDate naissance = LocalDate.parse(val, fmt);
                int age = Period.between(naissance, LocalDate.now()).getYears();
                if (age < ageMin) {
                    System.out.println("⚠ Âge insuffisant : " + age + " ans. "
                            + "Le personnel médical doit avoir au moins " + ageMin + " ans.");
                    continue;
                }
                return val;
            } catch (DateTimeParseException e) {
                System.out.println("⚠ Date invalide. Format attendu : YYYY-MM-DD (ex: 1985-06-20).");
            }
        }
    }

    /**
     * Lit un groupe sanguin valide.
     * Boucle jusqu'à ce que l'utilisateur entre une valeur parmi les valeurs acceptées.
     */
    private static String lireGroupeSanguin(String invite) {
        while (true) {
            System.out.print(invite + Patient.GROUPES_VALIDES + " : ");
            String val = scanner.nextLine().trim().toUpperCase();
            if (Patient.GROUPES_VALIDES.contains(val)) return val;
            System.out.println("⚠ Groupe sanguin invalide. Valeurs acceptées : "
                    + Patient.GROUPES_VALIDES);
        }
    }

    /**
     * Lit un ID et vérifie qu'il correspond à un enregistrement existant.
     * @param type "patient" ou "medecin" — détermine dans quelle liste chercher
     */
    private static String lireIdExistant(String invite, String type) {
        while (true) {
            System.out.print(invite);
            String id = scanner.nextLine().trim();
            boolean trouve = false;
            if (type.equals("patient")) {
                trouve = clinique.getPatients().stream()
                        .anyMatch(p -> p.getIdentifiant().equalsIgnoreCase(id));
            } else if (type.equals("medecin")) {
                trouve = clinique.getMedecins().stream()
                        .anyMatch(m -> m.getIdentifiant().equalsIgnoreCase(id));
            }
            if (trouve) return id;
            System.out.println("⚠ Aucun " + type + " trouvé avec l'ID : " + id
                    + ". Veuillez choisir un ID dans la liste.");
        }
    }
}