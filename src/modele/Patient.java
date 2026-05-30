package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un patient de la clinique.
 * L'identifiant est généré automatiquement (P001, P002, ...).
 * Hérite de Personne et ajoute les informations médicales propres au patient.
 */
public class Patient extends Personne {

    // ─── Groupes sanguins valides — public pour validation dans Main ──────────
    public static final List<String> GROUPES_VALIDES =
            List.of("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");

    // ─── Compteur statique pour la génération automatique des IDs ─────────────
    private static int compteur = 1;

    // ─── Attributs spécifiques au patient ────────────────────────────────────
    private String       numeroSecuriteSociale;
    private String       groupeSanguin;
    private List<String> antecedentsMedicaux;

    // ─── Constructeur ────────────────────────────────────────────────────────
    /**
     * Crée un nouveau patient avec un identifiant généré automatiquement.
     * Format : P001, P002, P003, ...
     * Le groupe sanguin est déjà validé dans Main avant la création.
     */
    public Patient(String nom, String prenom, String dateNaissance,
                   String telephone, String numeroSecuriteSociale, String groupeSanguin) {
        super("P" + String.format("%03d", compteur++), nom, prenom, dateNaissance, telephone);
        this.numeroSecuriteSociale = numeroSecuriteSociale;
        this.antecedentsMedicaux   = new ArrayList<>();
        this.groupeSanguin         = groupeSanguin;
    }

    // ─── Méthodes métier ─────────────────────────────────────────────────────
    /**
     * Ajoute un antécédent médical à l'historique du patient.
     */
    public void ajouterAntecedent(String antecedent) {
        if (antecedent != null && !antecedent.trim().isEmpty()) {
            antecedentsMedicaux.add(antecedent.trim());
            System.out.println("✔ Antécédent ajouté : " + antecedent);
        }
    }

    /**
     * Affiche le dossier médical complet du patient.
     */
    public void afficherDossier() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║         DOSSIER MÉDICAL DU PATIENT           ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        afficherProfil();
        System.out.println("  N° Sécurité Sociale : " + numeroSecuriteSociale);
        System.out.println("  Groupe Sanguin       : " + groupeSanguin);
        System.out.println("  Antécédents médicaux :");
        if (antecedentsMedicaux.isEmpty()) {
            System.out.println("    Aucun antécédent enregistré.");
        } else {
            for (int i = 0; i < antecedentsMedicaux.size(); i++) {
                System.out.printf("    %d. %s%n", i + 1, antecedentsMedicaux.get(i));
            }
        }
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    // ─── Polymorphisme : redéfinition de afficherProfil() ────────────────────
    @Override
    public void afficherProfil() {
        System.out.println("  PATIENT    : " + getPrenom() + " " + getNom());
        System.out.println("  ID         : " + getIdentifiant());
        System.out.println("  Âge        : " + calculerAge() + " ans");
        System.out.println("  Téléphone  : " + getTelephone());
        System.out.println("  Groupe     : " + groupeSanguin);
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────
    public String getNumeroSecuriteSociale()           { return numeroSecuriteSociale; }
    public void   setNumeroSecuriteSociale(String num) { this.numeroSecuriteSociale = num; }

    public String getGroupeSanguin()                   { return groupeSanguin; }
    public void   setGroupeSanguin(String g)           { this.groupeSanguin = g; }

    public List<String> getAntecedentsMedicaux()       { return antecedentsMedicaux; }

    @Override
    public String toString() {
        return String.format("Patient{%s | NSS: %s | Groupe: %s | Antécédents: %d}",
                super.toString(), numeroSecuriteSociale,
                groupeSanguin, antecedentsMedicaux.size());
    }
}