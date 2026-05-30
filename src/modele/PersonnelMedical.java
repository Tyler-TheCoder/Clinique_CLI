package modele;

/**
 * Classe intermédiaire représentant tout membre du personnel médical.
 * Hérite de Personne. Parent de Medecin et Infirmier.
 * Reste abstraite car on n'instancie jamais "un personnel médical" en général.
 */
public abstract class PersonnelMedical extends Personne {

    // ─── Attributs spécifiques au personnel médical ──────────────────────────
    private String matricule;
    private String dateEmbauche;  // Format : "YYYY-MM-DD"
    private double salaire;       // Doit être >= 0

    // ─── Constructeur ────────────────────────────────────────────────────────
    /**
     * Initialise les informations communes à tout le personnel médical.
     * Appelle super() pour remonter jusqu'à Personne.
     */
    public PersonnelMedical(String identifiant, String nom, String prenom,
                            String dateNaissance, String telephone,
                            String matricule, String dateEmbauche, double salaire) {
        // Appel du constructeur de Personne (obligatoire)
        super(identifiant, nom, prenom, dateNaissance, telephone);
        this.matricule    = matricule;
        this.dateEmbauche = dateEmbauche;
        // Validation du salaire dans le setter
        setSalaire(salaire);
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────
    public String getMatricule()                   { return matricule; }
    public void   setMatricule(String matricule)   { this.matricule = matricule; }

    public String getDateEmbauche()                { return dateEmbauche; }
    public void   setDateEmbauche(String date)     { this.dateEmbauche = date; }

    public double getSalaire()                     { return salaire; }

    /**
     * Setter avec validation : le salaire doit être positif ou nul.
     */
    public void setSalaire(double salaire) {
        if (salaire >= 0) {
            this.salaire = salaire;
        } else {
            System.out.println("⚠ Salaire invalide (" + salaire + "). Valeur 0 appliquée.");
            this.salaire = 0;
        }
    }

    // ─── toString ─────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("PersonnelMedical{%s | Matricule: %s | Embauché: %s | Salaire: %.2f DA}",
                super.toString(), matricule, dateEmbauche, salaire);
    }
}
