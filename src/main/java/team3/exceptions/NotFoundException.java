package team3.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("The query with id " + id + " has not been found in our database.");
    }
}


