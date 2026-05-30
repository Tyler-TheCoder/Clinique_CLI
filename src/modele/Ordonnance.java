package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une ordonnance médicale liée à une consultation.
 * Contient la liste des médicaments prescrits avec leur posologie.
 */
public class Ordonnance {

    // ─── Compteur pour IDs automatiques ──────────────────────────────────────
    private static int compteur = 1;

    // ─── Attributs ───────────────────────────────────────────────────────────
    private String            idOrdonnance;
    private Consultation      consultation;      // La consultation associée
    private List<Medicament>  medicaments;       // Liste des médicaments prescrits
    private String            dateEmission;      // Date de création de l'ordonnance

    // ─── Constructeur ────────────────────────────────────────────────────────
    /**
     * Crée une ordonnance liée à une consultation existante.
     * @param consultation La consultation pour laquelle l'ordonnance est établie
     * @param dateEmission La date d'émission de l'ordonnance
     */
    public Ordonnance(Consultation consultation, String dateEmission) {
        this.idOrdonnance = "ORD" + String.format("%03d", compteur++);
        this.consultation = consultation;
        this.dateEmission = dateEmission;
        this.medicaments  = new ArrayList<>();
    }

    // ─── Méthodes métier ─────────────────────────────────────────────────────
    /**
     * Ajoute un médicament à l'ordonnance.
     * @param medicament Le médicament à prescrire
     */
    public void ajouterMedicament(Medicament medicament) {
        if (medicament != null) {
            medicaments.add(medicament);
            System.out.println("✔ Médicament ajouté : " + medicament.getNom());
        }
    }

    /**
     * Retire un médicament de l'ordonnance par son nom.
     * @param nomMedicament Le nom du médicament à retirer
     */
    public void retirerMedicament(String nomMedicament) {
        boolean supprime = medicaments.removeIf(
            m -> m.getNom().equalsIgnoreCase(nomMedicament)
        );
        if (supprime) {
            System.out.println("✔ Médicament retiré : " + nomMedicament);
        } else {
            System.out.println("⚠ Médicament non trouvé : " + nomMedicament);
        }
    }

    /**
     * Affiche l'ordonnance complète dans la console.
     */
    public void afficher() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║              ORDONNANCE MÉDICALE             ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("  N° Ordonnance : " + idOrdonnance);
        System.out.println("  Date émission : " + dateEmission);
        System.out.println("  Patient       : " + consultation.getPatient().getPrenom()
                + " " + consultation.getPatient().getNom());
        System.out.println("  Médecin       : Dr. " + consultation.getMedecin().getPrenom()
                + " " + consultation.getMedecin().getNom()
                + " (" + consultation.getMedecin().getSpecialite() + ")");
        System.out.println("  Diagnostic    : " + consultation.getDiagnostic());
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("  Médicaments prescrits (" + medicaments.size() + ") :");
        if (medicaments.isEmpty()) {
            System.out.println("    Aucun médicament prescrit.");
        } else {
            for (Medicament m : medicaments) {
                m.afficher();
            }
        }
        System.out.println("");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────
    public String           getIdOrdonnance()                    { return idOrdonnance; }

    public Consultation     getConsultation()                    { return consultation; }
    public void             setConsultation(Consultation c)      { this.consultation = c; }

    public List<Medicament> getMedicaments()                     { return medicaments; }

    public String           getDateEmission()                    { return dateEmission; }
    public void             setDateEmission(String date)         { this.dateEmission = date; }

    // ─── toString ─────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("Ordonnance{%s | Date: %s | Patient: %s | Médicaments: %d}",
                idOrdonnance, dateEmission,
                consultation.getPatient().getNom(),
                medicaments.size());
    }
}
