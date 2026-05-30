package modele;

/**
 * Représente une consultation médicale entre un patient et un médecin.
 * Relie un Patient à un Medecin avec les informations de la consultation.
 */
public class Consultation {

    // ─── Statuts possibles pour une consultation ──────────────────────────────
    public static final String STATUT_PLANIFIEE  = "Planifiée";
    public static final String STATUT_EN_COURS   = "En cours";
    public static final String STATUT_TERMINEE   = "Terminée";
    public static final String STATUT_ANNULEE    = "Annulée";

    // ─── Compteur pour générer des IDs automatiques ───────────────────────────
    private static int compteur = 1;

    // ─── Attributs ───────────────────────────────────────────────────────────
    private String  idConsultation;
    private Patient patient;          // Le patient consulté
    private Medecin medecin;          // Le médecin qui consulte
    private String  date;             // Format : "YYYY-MM-DD HH:MM"
    private String  diagnostic;       // Diagnostic posé par le médecin
    private String  notesCliniques;   // Observations supplémentaires (optionnel)
    private String  statut;           // Statut actuel de la consultation

    // ─── Constructeur COMPLET (avec notes cliniques) ─────────────────────────
    /**
     * Crée une consultation avec notes cliniques.
     * Surcharge (overloading) : ce constructeur prend les notes en plus.
     */
    public Consultation(Patient patient, Medecin medecin, String date,
                        String diagnostic, String notesCliniques) {
        this.idConsultation = "C" + String.format("%03d", compteur++);
        this.patient        = patient;
        this.medecin        = medecin;
        this.date           = date;
        this.diagnostic     = diagnostic;
        this.notesCliniques = notesCliniques;
        this.statut         = STATUT_PLANIFIEE; // Statut initial
    }

    /**
     * Crée une consultation SANS notes cliniques (surcharge de constructeur).
     * Délègue au constructeur complet avec une note vide.
     */
    public Consultation(Patient patient, Medecin medecin,
                        String date, String diagnostic) {
        // Surcharge : appelle le constructeur principal avec une note par défaut
        this(patient, medecin, date, diagnostic, "Aucune note clinique");
    }

    // ─── Méthodes d'affichage ─────────────────────────────────────────────────
    /**
     * Affiche le résumé complet de la consultation.
     */
    public void afficher() {
        String ligne = "─".repeat(62);
        System.out.println("\n  " + idConsultation + " — " + date + " | Statut: " + statut);
        System.out.println("  " + ligne);
        System.out.println("   Patient    : " + patient.getPrenom() + " " + patient.getNom()
                + " (ID: " + patient.getIdentifiant() + ")");
        System.out.println("   Médecin    : Dr. " + medecin.getPrenom() + " " + medecin.getNom()
                + " — " + medecin.getSpecialite());
        System.out.println("   Diagnostic : " + diagnostic);
        System.out.println("   Notes      : " + notesCliniques);
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────
    public String  getIdConsultation()                   { return idConsultation; }

    public Patient getPatient()                          { return patient; }
    public void    setPatient(Patient patient)           { this.patient = patient; }

    public Medecin getMedecin()                          { return medecin; }
    public void    setMedecin(Medecin medecin)           { this.medecin = medecin; }

    public String  getDate()                             { return date; }
    public void    setDate(String date)                  { this.date = date; }

    public String  getDiagnostic()                       { return diagnostic; }
    public void    setDiagnostic(String diagnostic)      { this.diagnostic = diagnostic; }

    public String  getNotesCliniques()                   { return notesCliniques; }
    public void    setNotesCliniques(String notes)       { this.notesCliniques = notes; }

    public String  getStatut()                           { return statut; }
    public void    setStatut(String statut)              { this.statut = statut; }

    // ─── toString ─────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("Consultation{%s | Patient: %s %s | Médecin: Dr. %s | Date: %s | Statut: %s}",
                idConsultation,
                patient.getPrenom(), patient.getNom(),
                medecin.getNom(),
                date, statut);
    }
}