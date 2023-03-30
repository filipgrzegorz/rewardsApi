package pl.filipgrzegorz.apps.rewards.rewards.api.exceptions;

public class TransactionServiceException extends RuntimeException {
    private static final long serialVersionUID = -7455543454L;

    public TransactionServiceException(String message) {
        super(message);
    }
}
