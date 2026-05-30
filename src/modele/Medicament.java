package modele;

/**
 * Représente un médicament prescrit dans une ordonnance.
 * Classe simple sans héritage particulier.
 */
public class Medicament {

    // ─── Attributs ───────────────────────────────────────────────────────────
    private String nom;               // Nom commercial ou DCI du médicament
    private String dosage;            // Ex: "500mg", "1 comprimé"
    private String dureeTraitement;   // Ex: "7 jours", "1 mois"
    private String contreIndications; // Ex: "Allergie pénicilline", "Grossesse"

    // ─── Constructeur ────────────────────────────────────────────────────────
    /**
     * Crée un nouveau médicament avec toutes ses informations.
     */
    public Medicament(String nom, String dosage,
                      String dureeTraitement, String contreIndications) {
        this.nom               = nom;
        this.dosage            = dosage;
        this.dureeTraitement   = dureeTraitement;
        this.contreIndications = contreIndications;
    }

    /**
     * Constructeur simplifié sans contre-indications.
     */
    public Medicament(String nom, String dosage, String dureeTraitement) {
        this(nom, dosage, dureeTraitement, "Aucune contre-indication connue");
    }

    // ─── Méthode d'affichage ─────────────────────────────────────────────────
    /**
     * Affiche les détails du médicament dans la console.
     */
    public void afficher() {
        System.out.println("    💊 " + nom + " \n        Dosage: " + dosage +
                " \n        Durée: " + dureeTraitement +
                " \n        Contre-indications: " + contreIndications);
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────
    public String getNom()                              { return nom; }
    public void   setNom(String nom)                    { this.nom = nom; }

    public String getDosage()                           { return dosage; }
    public void   setDosage(String dosage)              { this.dosage = dosage; }

    public String getDureeTraitement()                  { return dureeTraitement; }
    public void   setDureeTraitement(String duree)      { this.dureeTraitement = duree; }

    public String getContreIndications()                { return contreIndications; }
    public void   setContreIndications(String contre)   { this.contreIndications = contre; }

    // ─── toString ─────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("Medicament{%s | %s | %s}", nom, dosage, dureeTraitement);
    }
}
