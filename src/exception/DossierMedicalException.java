package exception;

/**
 * Exception levée lors d'un accès non autorisé ou incomplet à un dossier médical.
 */
public class DossierMedicalException extends Exception {
    public DossierMedicalException(String message) {
        super("❌ Erreur dossier médical : " + message);
    }
}
