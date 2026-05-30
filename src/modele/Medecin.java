package modele;

/**
 * Représente un médecin de la clinique.
 * L'identifiant est généré automatiquement (M001, M002, ...).
 * Hiérarchie : Personne → PersonnelMedical → Medecin
 */
public class Medecin extends PersonnelMedical {

    // ─── Compteur statique pour la génération automatique des IDs ─────────────
    private static int compteur = 1;

    // ─── Attributs spécifiques au médecin ────────────────────────────────────
    private String specialite;
    private String numeroOrdre;
    private double tarifConsultation; // doit être > 0

    // ─── Constructeur ────────────────────────────────────────────────────────
    /**
     * Crée un nouveau médecin avec un identifiant généré automatiquement.
     * Format : M001, M002, M003, ...
     * Les validations (tarif, salaire, âge) sont faites dans Main avant création.
     */
    public Medecin(String nom, String prenom, String dateNaissance,
                   String telephone, String matricule, String dateEmbauche,
                   double salaire, String specialite, String numeroOrdre,
                   double tarifConsultation) {
        super("M" + String.format("%03d", compteur++), nom, prenom,
              dateNaissance, telephone, matricule, dateEmbauche, salaire);
        this.specialite         = specialite;
        this.numeroOrdre        = numeroOrdre;
        this.tarifConsultation  = tarifConsultation;
    }

    // ─── Polymorphisme : redéfinition de afficherProfil() ────────────────────
    @Override
    public void afficherProfil() {
        System.out.println("  MEDECIN    : Dr. " + getPrenom() + " " + getNom());
        System.out.println("  ID         : " + getIdentifiant());
        System.out.println("  Spécialité : " + specialite);
        System.out.println("  N° Ordre   : " + numeroOrdre);
        System.out.println("  Tarif      : " + tarifConsultation + " DA");
        System.out.println("  Matricule  : " + getMatricule());
        System.out.println("  Téléphone  : " + getTelephone());
        System.out.println("  Âge        : " + calculerAge() + " ans");
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────
    public String getSpecialite()                   { return specialite; }
    public void   setSpecialite(String s)           { this.specialite = s; }

    public String getNumeroOrdre()                  { return numeroOrdre; }
    public void   setNumeroOrdre(String n)          { this.numeroOrdre = n; }

    public double getTarifConsultation()            { return tarifConsultation; }
    public void   setTarifConsultation(double t)    { this.tarifConsultation = t; }

    @Override
    public String toString() {
        return String.format("Medecin{Dr. %s %s | Spécialité: %s | N°Ordre: %s | Tarif: %.2f DA}",
                getPrenom(), getNom(), specialite, numeroOrdre, tarifConsultation);
    }
}