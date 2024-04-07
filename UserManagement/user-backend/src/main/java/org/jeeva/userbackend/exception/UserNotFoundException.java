package org.jeeva.userbackend.exception;

import org.apache.catalina.User;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
