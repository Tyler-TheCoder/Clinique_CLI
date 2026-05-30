package gestion;

import exception.*;
import modele.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe centrale de gestion de la clinique médicale.
 * Contient toutes les collections (patients, médecins, infirmiers,
 * consultations, ordonnances) et fournit toutes les opérations du système.
 */
public class CliniqueMedicale {

    // ─── Informations de la clinique ──────────────────────────────────────────
    private String nom;
    private String adresse;

    // ─── Collections de données ───────────────────────────────────────────────
    private List<Patient>      patients;       // Tous les patients enregistrés
    private List<Medecin>      medecins;       // Tous les médecins de la clinique
    private List<Infirmier>    infirmiers;     // Tout le personnel infirmier
    private List<Consultation> consultations;  // Toutes les consultations
    private List<Ordonnance>   ordonnances;    // Toutes les ordonnances émises

    // ─── Constructeur ────────────────────────────────────────────────────────
    /**
     * Initialise la clinique avec son nom et son adresse.
     * Crée les listes vides pour chaque entité.
     */
    public CliniqueMedicale(String nom, String adresse) {
        this.nom          = nom;
        this.adresse      = adresse;
        this.patients     = new ArrayList<>();
        this.medecins     = new ArrayList<>();
        this.infirmiers   = new ArrayList<>();
        this.consultations = new ArrayList<>();
        this.ordonnances  = new ArrayList<>();
    }

    // ═══════════════════════════════════════════════════════════════════════
    // GESTION DES PATIENTS
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Enregistre un nouveau patient dans la clinique.
     */
    public void enregistrerPatient(Patient patient) {
        patients.add(patient);
        System.out.println("✔ Patient enregistré : " + patient.getPrenom()
                + " " + patient.getNom() + " (ID: " + patient.getIdentifiant() + ")");
    }

    /**
     * Recherche un patient par son identifiant.
     * @throws PatientInexistantException si aucun patient ne correspond
     */
    public Patient rechercherPatient(String identifiant) throws PatientInexistantException {
        for (Patient p : patients) {
            if (p.getIdentifiant().equalsIgnoreCase(identifiant)) {
                return p;
            }
        }
        // Aucun patient trouvé : on lance l'exception métier
        throw new PatientInexistantException(identifiant);
    }

    /**
     * Affiche la liste de tous les patients avec un style arborescent uniforme.
     */
    public void afficherTousLesPatients() {
        if (patients.isEmpty()) {
            System.out.println("  Aucun patient enregistré.");
            return;
        }
        System.out.println("\n══ TOUS LES PATIENTS (" + patients.size() + ") ══");
        for (Patient p : patients) {
            System.out.println("\n  " + p.getIdentifiant() + " — " + p.getPrenom() + " " + p.getNom());
            System.out.println("  ├── Age            : " + p.calculerAge() + " ans");
            System.out.println("  ├── Date naissance : " + p.getDateNaissance());
            System.out.println("  ├── Téléphone      : " + p.getTelephone());
            System.out.println("  ├── Groupe sanguin : " + p.getGroupeSanguin());
            System.out.println("  └── Antécédents    : " + p.getAntecedentsMedicaux().size()
                    + " enregistré(s)");
        }
        System.out.println();
    }

    // ═══════════════════════════════════════════════════════════════════════
    // GESTION DU PERSONNEL MÉDICAL
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Ajoute un médecin à l'équipe de la clinique.
     */
    public void ajouterMedecin(Medecin medecin) {
        medecins.add(medecin);
        System.out.println("✔ Médecin ajouté : Dr. " + medecin.getPrenom()
                + " " + medecin.getNom() + " (" + medecin.getSpecialite() + ")");
    }

    /**
     * Ajoute un infirmier au personnel de la clinique.
     */
    public void ajouterInfirmier(Infirmier infirmier) {
        infirmiers.add(infirmier);
        System.out.println("✔ Infirmier ajouté : " + infirmier.getPrenom()
                + " " + infirmier.getNom() + " — Service: " + infirmier.getService());
    }

    /**
     * Recherche un médecin par son identifiant.
     * @return le médecin trouvé, ou null
     */
    public Medecin rechercherMedecin(String identifiant) {
        for (Medecin m : medecins) {
            if (m.getIdentifiant().equalsIgnoreCase(identifiant)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Affiche tout le personnel (polymorphisme).
     * Utilise List<Personne> pour démontrer l'appel polymorphe à afficherProfil().
     */
    public void afficherToutLePersonnel() {
        // ─── Démonstration du POLYMORPHISME ───────────────────────────────
        // On stocke médecins ET infirmiers dans une liste de type parent (Personne)
        List<Personne> toutLePersonnel = new ArrayList<>();
        toutLePersonnel.addAll(medecins);
        toutLePersonnel.addAll(infirmiers);

        if (toutLePersonnel.isEmpty()) {
            System.out.println("  Aucun personnel enregistré.");
            return;
        }

        System.out.println("\n══ TOUT LE PERSONNEL MÉDICAL ══");
        for (Personne p : toutLePersonnel) {
            // Java appelle automatiquement la bonne version de afficherProfil()
            // selon le type réel de l'objet (Medecin ou Infirmier)
            p.afficherProfil();
            System.out.println();
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // GESTION DES CONSULTATIONS
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Crée et enregistre une consultation après vérification de disponibilité.
     * Utilise try-catch-finally pour la gestion des erreurs.
     *
     * @throws MedecinIndisponibleException si le médecin est déjà occupé
     * @throws PatientInexistantException   si le patient n'existe pas
     */
    public Consultation creerConsultation(String idPatient, String idMedecin,
                                          String date, String diagnostic,
                                          String notesCliniques)
            throws MedecinIndisponibleException, PatientInexistantException {

        Consultation nouvelleConsultation = null;

        try {
            // 1. Vérifier que le patient existe
            Patient patient = rechercherPatient(idPatient);

            // 2. Vérifier que le médecin existe
            Medecin medecin = rechercherMedecin(idMedecin);
            if (medecin == null) {
                throw new IllegalArgumentException("Médecin introuvable : " + idMedecin);
            }

            // 3. Vérifier la disponibilité du médecin à cette date
            verifierDisponibiliteMedecin(medecin, date);

            // 4. Créer la consultation (avec ou sans notes cliniques)
            if (notesCliniques != null && !notesCliniques.isBlank()) {
                nouvelleConsultation = new Consultation(patient, medecin, date,
                        diagnostic, notesCliniques);
            } else {
                // Surcharge sans notes cliniques
                nouvelleConsultation = new Consultation(patient, medecin, date, diagnostic);
            }

            consultations.add(nouvelleConsultation);
            System.out.println("✔ Consultation créée : " + nouvelleConsultation.getIdConsultation());

        } catch (PatientInexistantException | MedecinIndisponibleException e) {
            // On propage ces exceptions métier vers le menu
            throw e;
        } catch (IllegalArgumentException e) {
            System.out.println("⚠ Erreur lors de la création : " + e.getMessage());
        } finally {
            // Ce bloc s'exécute TOUJOURS, qu'il y ait une exception ou non
            System.out.println("  [Fin de la tentative de création de consultation]");
        }

        return nouvelleConsultation;
    }

    /**
     * Vérifie qu'un médecin n'a pas déjà une consultation à la date donnée.
     * @throws MedecinIndisponibleException si le médecin est occupé
     */
    private void verifierDisponibiliteMedecin(Medecin medecin, String date)
            throws MedecinIndisponibleException {
        for (Consultation c : consultations) {
            if (c.getMedecin().getIdentifiant().equals(medecin.getIdentifiant())
                    && c.getDate().equals(date)
                    && !c.getStatut().equals(Consultation.STATUT_ANNULEE)) {
                throw new MedecinIndisponibleException(
                        medecin.getPrenom() + " " + medecin.getNom(), date);
            }
        }
    }

    /**
     * Recherche toutes les consultations d'un patient donné.
     */
    public List<Consultation> rechercherConsultationsParPatient(String idPatient) {
        List<Consultation> resultats = new ArrayList<>();
        for (Consultation c : consultations) {
            if (c.getPatient().getIdentifiant().equalsIgnoreCase(idPatient)) {
                resultats.add(c);
            }
        }
        return resultats;
    }

    /**
     * Recherche toutes les consultations d'un médecin donné.
     */
    public List<Consultation> rechercherConsultationsParMedecin(String idMedecin) {
        List<Consultation> resultats = new ArrayList<>();
        for (Consultation c : consultations) {
            if (c.getMedecin().getIdentifiant().equalsIgnoreCase(idMedecin)) {
                resultats.add(c);
            }
        }
        return resultats;
    }

    // ═══════════════════════════════════════════════════════════════════════
    // GESTION DES ORDONNANCES
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Crée une ordonnance pour une consultation existante.
     * Valide que la consultation est terminée et que le diagnostic est renseigné.
     * Utilise try-catch-finally.
     *
     * @throws OrdonnanceInvalideException si les conditions ne sont pas remplies
     */
    public Ordonnance creerOrdonnance(Consultation consultation, String dateEmission)
            throws OrdonnanceInvalideException {

        Ordonnance ordonnance = null;

        try {
            // Validation : une ordonnance nécessite un diagnostic
            if (consultation.getDiagnostic() == null
                    || consultation.getDiagnostic().isBlank()) {
                throw new OrdonnanceInvalideException(
                        "Le diagnostic est obligatoire pour créer une ordonnance.");
            }

            ordonnance = new Ordonnance(consultation, dateEmission);
            ordonnances.add(ordonnance);
            System.out.println("✔ Ordonnance créée : " + ordonnance.getIdOrdonnance());

        } catch (OrdonnanceInvalideException e) {
            throw e; // Propage l'exception vers le menu
        } finally {
            System.out.println("  [Fin de la tentative de création d'ordonnance]");
        }

        return ordonnance;
    }

    /**
     * Valide et finalise une ordonnance (vérifie qu'elle contient des médicaments).
     * @throws OrdonnanceInvalideException si l'ordonnance est vide
     */
    public void validerOrdonnance(Ordonnance ordonnance)
            throws OrdonnanceInvalideException {
        if (ordonnance.getMedicaments().isEmpty()) {
            throw new OrdonnanceInvalideException(
                    "L'ordonnance " + ordonnance.getIdOrdonnance()
                    + " ne contient aucun médicament.");
        }
        System.out.println("✔ Ordonnance " + ordonnance.getIdOrdonnance() + " validée.");
    }

    // ═══════════════════════════════════════════════════════════════════════
    // TABLEAU DE BORD
    // ═══════════════════════════════════════════════════════════════════════

    /**
     * Affiche le tableau de bord de la clinique :
     * nombre de patients, consultations, et revenus du jour.
     */
    public void afficherTableauDeBord() {
        double revenusTotal = 0;
        long   consultationsAujourdhui = 0;
        String aujourdhui = java.time.LocalDate.now().toString();

        for (Consultation c : consultations) {
            if (!c.getStatut().equals(Consultation.STATUT_ANNULEE)) {
                revenusTotal += c.getMedecin().getTarifConsultation();
            }
            if (c.getDate().startsWith(aujourdhui)) {
                consultationsAujourdhui++;
            }
        }

        // Largeur intérieure fixe : 60 caractères entre ║ et ║
        final int W = 60;
        String ligne = "═".repeat(W);

        // Helper : construit une ligne "║  contenu padding ║"
        // Nous avons trouvé cette fonction sur Internet pour obtenir un résultat satisfaisant (un cadre parfait pour le tableau de bord).
        // Le contenu est tronqué/padé pour tenir dans W-2 caractères (les 2 espaces de marge inclus)
        java.util.function.Function<String, String> row = content -> {
            // contenu total disponible = W caractères (les ║ ne comptent pas)
            String inner = "  " + content;
            if (inner.length() < W) inner = String.format("%-" + W + "s", inner);
            else if (inner.length() > W) inner = inner.substring(0, W);
            return "║" + inner + "║";
        };

        String consLine = String.format("%-20s : %d  (aujourd'hui: %d)",
                "Consultations", consultations.size(), consultationsAujourdhui);
        String revLine  = String.format("%-20s : %.2f DA",
                "Revenus totaux", revenusTotal);

        System.out.println("╔" + ligne + "╗");
        System.out.println(row.apply("TABLEAU DE BORD — " + nom));
        System.out.println("╠" + ligne + "╣");
        System.out.println(row.apply(String.format("%-20s : %s", "Clinique",   nom)));
        System.out.println(row.apply(String.format("%-20s : %s", "Adresse",    adresse)));
        System.out.println("╠" + ligne + "╣");
        System.out.println(row.apply(String.format("%-20s : %d", "Patients",   patients.size())));
        System.out.println(row.apply(String.format("%-20s : %d", "Medecins",   medecins.size())));
        System.out.println(row.apply(String.format("%-20s : %d", "Infirmiers", infirmiers.size())));
        System.out.println(row.apply(consLine));
        System.out.println(row.apply(String.format("%-20s : %d", "Ordonnances", ordonnances.size())));
        System.out.println(row.apply(revLine));
        System.out.println("╚" + ligne + "╝");
    }

    // ─── Getters ──────────────────────────────────────────────────────────────
    public String              getNom()           { return nom; }
    public List<Patient>       getPatients()      { return patients; }
    public List<Medecin>       getMedecins()      { return medecins; }
    public List<Infirmier>     getInfirmiers()    { return infirmiers; }
    public List<Consultation>  getConsultations() { return consultations; }
    public List<Ordonnance>    getOrdonnances()   { return ordonnances; }

    @Override
    public String toString() {
        return String.format("CliniqueMedicale{%s | Patients: %d | Médecins: %d | Consultations: %d}",
                nom, patients.size(), medecins.size(), consultations.size());
    }
}