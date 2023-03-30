package pl.filipgrzegorz.apps.rewards.rewards.api.exceptions;

public class EntityNotFoundException extends RuntimeException {
    static final String ENTITY_NOT_FOUND = "Entity for %s Not found!";

    public EntityNotFoundException(String message) {

        super(String.format(ENTITY_NOT_FOUND, message));
    }
}
