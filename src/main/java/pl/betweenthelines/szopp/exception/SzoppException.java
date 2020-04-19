package pl.betweenthelines.szopp.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@NoArgsConstructor
@ResponseStatus(BAD_REQUEST)
public class SzoppException extends RuntimeException {

    public SzoppException(String message) {
        super(message);
    }

}
