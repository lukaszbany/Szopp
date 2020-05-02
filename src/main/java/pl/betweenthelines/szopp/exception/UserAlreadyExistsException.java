package pl.betweenthelines.szopp.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserAlreadyExistsException extends SzoppException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
