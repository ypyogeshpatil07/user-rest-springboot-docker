package co.zip.candidate.userapi.exception;

public class DuplicateEmailIDException
        extends RuntimeException {

    public DuplicateEmailIDException(String message) {
        super(message);
    }
}
