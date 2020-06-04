package pl.betweenthelines.szopp.rest.success;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {

    private int status;

    private LocalDateTime timestamp;

    private String message;

}
