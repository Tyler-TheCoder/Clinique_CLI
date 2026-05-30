package exception;

/**
 * Exception levée lorsqu'aucun patient n'est trouvé pour un identifiant donné.
 */
public class PatientInexistantException extends Exception {
    public PatientInexistantException(String identifiant) {
        super("❌ Aucun patient trouvé pour l'identifiant : " + identifiant);
    }
}
