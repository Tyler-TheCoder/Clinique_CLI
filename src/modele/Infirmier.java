package modele;

/**
 * Représente un infirmier de la clinique.
 * L'identifiant est généré automatiquement (I001, I002, ...).
 * Hiérarchie : Personne → PersonnelMedical → Infirmier
 */
public class Infirmier extends PersonnelMedical {

    // ─── Compteur statique pour la génération automatique des IDs ─────────────
    private static int compteur = 1;

    // ─── Attributs spécifiques à l'infirmier ─────────────────────────────────
    private String service; // ex: Urgences, Pédiatrie, Chirurgie
    private String grade;   // ex: Infirmier principal, Chef de service

    // ─── Constructeur ────────────────────────────────────────────────────────
    /**
     * Crée un nouvel infirmier avec un identifiant généré automatiquement.
     * Format : I001, I002, I003, ...
     */
    public Infirmier(String nom, String prenom, String dateNaissance,
                     String telephone, String matricule, String dateEmbauche,
                     double salaire, String service, String grade) {
        super("I" + String.format("%03d", compteur++), nom, prenom,
              dateNaissance, telephone, matricule, dateEmbauche, salaire);
        this.service = service;
        this.grade   = grade;
    }

    // ─── Polymorphisme : redéfinition de afficherProfil() ────────────────────
    @Override
    public void afficherProfil() {
        System.out.println("  INFIRMIER  : " + getPrenom() + " " + getNom());
        System.out.println("  ID         : " + getIdentifiant());
        System.out.println("  Service    : " + service);
        System.out.println("  Grade      : " + grade);
        System.out.println("  Matricule  : " + getMatricule());
        System.out.println("  Téléphone  : " + getTelephone());
        System.out.println("  Âge        : " + calculerAge() + " ans");
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────
    public String getService()               { return service; }
    public void   setService(String service) { this.service = service; }

    public String getGrade()                 { return grade; }
    public void   setGrade(String grade)     { this.grade = grade; }

    @Override
    public String toString() {
        return String.format("Infirmier{%s %s | Service: %s | Grade: %s}",
                getPrenom(), getNom(), service, grade);
    }
}