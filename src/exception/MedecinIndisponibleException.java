package exception;

/**
 * Exception levée lorsqu'un médecin a déjà un rendez-vous au créneau demandé.
 */
public class MedecinIndisponibleException extends Exception {
    public MedecinIndisponibleException(String nomMedecin, String date) {
        super("❌ Dr. " + nomMedecin + " est indisponible le " + date +
              " — un rendez-vous existe déjà à ce créneau.");
    }
}
