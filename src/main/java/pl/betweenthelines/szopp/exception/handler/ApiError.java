package pl.betweenthelines.szopp.exception.handler;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private int status;

    private LocalDateTime timestamp;

    private String message;

    @Setter
    private List<ValidationError> validationErrors;

}
