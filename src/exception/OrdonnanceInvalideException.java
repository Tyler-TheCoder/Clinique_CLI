package exception;

/**
 * Exception levée lorsqu'une ordonnance est invalide
 * (sans médicaments ou sans diagnostic associé).
 */
public class OrdonnanceInvalideException extends Exception {
    public OrdonnanceInvalideException(String raison) {
        super("❌ Ordonnance invalide : " + raison);
    }
}
