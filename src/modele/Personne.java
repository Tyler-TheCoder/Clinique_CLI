package modele;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Classe abstraite de base représentant toute personne dans la clinique.
 * Sert de parent commun à Patient et PersonnelMedical.
 * Ne peut pas être instanciée directement.
 */
public abstract class Personne {

    // ─── Attributs privés (Encapsulation) ───────────────────────────────────
    private String identifiant;
    private String nom;
    private String prenom;
    private String dateNaissance; // Format : "YYYY-MM-DD"
    private String telephone;

    // ─── Constructeur ────────────────────────────────────────────────────────
    /**
     * Constructeur principal de Personne.
     * Appelé via super() depuis les sous-classes.
     */
    public Personne(String identifiant, String nom, String prenom,
                    String dateNaissance, String telephone) {
        this.identifiant    = identifiant;
        this.nom            = nom;
        this.prenom         = prenom;
        this.dateNaissance  = dateNaissance;
        this.telephone      = telephone;
    }

    // ─── Méthode abstraite (Abstraction) ─────────────────────────────────────
    /**
     * Affiche le profil détaillé de la personne.
     * Chaque sous-classe DOIT fournir sa propre implémentation (@Override).
     */
    public abstract void afficherProfil();

    // ─── Méthode commune ─────────────────────────────────────────────────────
    /**
     * Calcule l'âge de la personne à partir de sa date de naissance.
     * @return l'âge en années, ou -1 si la date est invalide
     */
    public int calculerAge() {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate naissance   = LocalDate.parse(this.dateNaissance, fmt);
            return Period.between(naissance, LocalDate.now()).getYears();
        } catch (DateTimeParseException e) {
            System.out.println("⚠ Date de naissance invalide : " + this.dateNaissance);
            return -1;
        }
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────
    public String getIdentifiant()               { return identifiant; }
    public void   setIdentifiant(String id)      { this.identifiant = id; }

    public String getNom()                       { return nom; }
    public void   setNom(String nom)             { this.nom = nom; }

    public String getPrenom()                    { return prenom; }
    public void   setPrenom(String prenom)       { this.prenom = prenom; }

    public String getDateNaissance()             { return dateNaissance; }
    public void   setDateNaissance(String date)  { this.dateNaissance = date; }

    public String getTelephone()                 { return telephone; }
    public void   setTelephone(String tel)       { this.telephone = tel; }

    // ─── toString ─────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("[%s] %s %s | Naissance: %s | Tél: %s | Âge: %d ans",
                identifiant, prenom, nom, dateNaissance, telephone, calculerAge());
    }
}
