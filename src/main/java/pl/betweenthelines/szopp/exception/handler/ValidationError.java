package pl.betweenthelines.szopp.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {

    private String field;

    private Object rejectedValue;

    private String message;

}
