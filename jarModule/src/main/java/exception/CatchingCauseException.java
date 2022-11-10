package exception;


import jakarta.persistence.PersistenceException;
public class CatchingCauseException extends Exception {
    public CatchingCauseException(Throwable cause) {
        super(cause);
    }

    public static void findPathToCause(PersistenceException e) throws CatchingCauseException {
        Throwable cause = e.getCause();
        while (true){
            if (cause.getCause()!=null){
                cause = cause.getCause();
            }else {
                break;
            }
        }
        throw new CatchingCauseException(cause);
    }
}
