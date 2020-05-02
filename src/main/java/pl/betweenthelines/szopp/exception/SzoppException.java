package pl.betweenthelines.szopp.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SzoppException extends RuntimeException {

    public SzoppException(String message) {
        super(message);
    }

}
